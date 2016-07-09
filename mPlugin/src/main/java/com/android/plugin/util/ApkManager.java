package com.android.plugin.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangduo
 * @description: apk包管理类
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class ApkManager {

    private static ApkManager instance;

    private String apkPackageName; // apk包名
    private String apkVersionName; // apk版本号
    private String apkPath; // apk包保存的完整路径;
    private final String MD5 = "ca5e2fc2bde560acccc05be347309ff4"; // assets下apk的md5值

    // **************** 用户机器中存放的apk包的名字 ****************
    private final String srcApkName = "pluginapk-1.0.0.apk"; // assets下apk的名字
    private final String desApkName = "pluginapk.apk"; // 存储在本地时apk的名字
    private final String newApkName = "pluginapk-new.apk"; // 更新包的名字
    // **************** 用户机器中存放的apk包的名字 ****************

    private ApkManager() {
    }

    public synchronized static ApkManager getInstance() {
        if (null == instance) {
            instance = new ApkManager();
        }
        return instance;
    }

    /**
     * 将apk包拷贝到设备中（sd卡或手机内存）
     *
     * @param mContext
     * @return
     */
    public boolean createApkFile(Context mContext) {
        LogHelper.e("ApkManager", "createApkFile start.....");

        copyUpdateApkIfExist(mContext);
        if (null != apkPath) { // 存在apk更新包 并且替换成功
            getApkInfo(apkPath, mContext);
            return true;
        }

        initApkFilePathIfExist(mContext);
        if (null != apkPath) { // 手机中存在apk
            getApkInfo(apkPath, mContext);
            return true;
        }

        if (copyFromAssets(mContext)) {
            getApkInfo(apkPath, mContext);
            return true;
        }
        return false;
    }

    /**
     * 获取手机中已存在的apk的路径,如不存在apk则:apkPath = null
     *
     * @param mContext
     * @return
     */
    private void initApkFilePathIfExist(Context mContext) {
        FileManager fileManager = FileManager.getInstance();
        apkPath = fileManager.getFilePathInPhone(mContext, desApkName);
        File apkFile = new File(apkPath);
        if (apkFile.exists()
                && apkFile.isFile()
                && apkFile.length() > 0) {
            LogHelper.e("ApkManager", "apkFile exist in phone...");
            return;
        }
        if (fileManager.isSDExist()) {
            apkPath = fileManager.getFilePathInSD(desApkName);
            apkFile = new File(apkPath);
            if (apkFile.exists()
                    && apkFile.isFile()
                    && apkFile.length() > 0) {
                LogHelper.e("ApkManager", "apkFile exist in SD...");
                return;
            }
            LogHelper.e("ApkManager", "apkFile don't exist...");
            apkPath = null;
        }
    }

    /**
     * 是否有更新包 有的话替换(更新都是下次使用生效) 并返回替换后apk的路径 失败则:apkPath = null
     *
     * @param mContext
     * @return
     */
    private void copyUpdateApkIfExist(Context mContext) {
        File updateApkFile;
        if ((updateApkFile = getUpdateApkInPhone(mContext)) != null
                && updateApkFile.exists()
                && updateApkFile.isFile()) {
            LogHelper.e("ApkManager", "updateApkFile exist in phone...");
            apkPath = FileManager.getInstance().getFilePathInPhone(mContext, desApkName);
        } else if ((updateApkFile = getUpdateApkInSD()) != null
                && updateApkFile.exists()
                && updateApkFile.isFile()) {
            LogHelper.e("ApkManager", "updateApkFile exist in SD...");
            apkPath = FileManager.getInstance().getFilePathInSD(desApkName);
        }
        if (null == apkPath) {
            LogHelper.e("ApkManager", "updateApkFile don't exist...");
            return;
        }
        File desFile = new File(apkPath);
        desFile.delete();
        if (!rename(updateApkFile, desFile)) {
            apkPath = null;
        }
    }

    /**
     * 获取SD卡中指向更新包的索引
     *
     * @return
     */
    private File getUpdateApkInSD() {
        String updateApkPath; // 更新包保存的完整路径
        if (FileManager.getInstance().isSDExist()) { // sd卡存在 存储在sd卡中
            updateApkPath = FileManager.getInstance().getFilePathInSD(newApkName);
            File updateApkFile = new File(updateApkPath);
            if (null != updateApkFile && updateApkFile.exists() && updateApkFile.isFile()) {
                return updateApkFile;
            }
        }
        return null;
    }

    /**
     * 获取手机内存中指向更新包的索引
     *
     * @param mContext
     * @return
     */
    private File getUpdateApkInPhone(Context mContext) {
        String updateApkPath; // 更新包保存的完整路径
        updateApkPath = FileManager.getInstance().getFilePathInPhone(mContext, newApkName);
        File updateApkFile = new File(updateApkPath);
        if (null != updateApkFile && updateApkFile.exists() && updateApkFile.isFile()) {
            return updateApkFile;
        }
        return null;
    }

    /**
     * 给apk改名
     *
     * @param srcFile
     * @param desFile
     * @throws Exception
     */
    private boolean rename(File srcFile, File desFile) {
        boolean isSucceed = false;
        if (srcFile.exists() && srcFile.isFile()) {
            final int COUNT = 5; // 最大 重命名 重试次数
            int count = -1; // 重试次数
            do {
                if (count >= COUNT) {
                    break;
                }
                isSucceed = srcFile.renameTo(desFile);
                count++;
            } while (!isSucceed);
        }
        LogHelper.e("ApkManager", "rename result : " + isSucceed);
        return isSucceed;
    }

    /**
     * 从assets中拷贝apk到手机
     *
     * @param mContext
     * @return
     */
    private boolean copyFromAssets(Context mContext) {
        InputStream is = null;
        FileOutputStream fos = null;
        boolean temp;
        File apkFile = null;
        try {
            FileManager fileManager = FileManager.getInstance();
            if (fileManager.hasEnoughInternalMemory()) { // 手机内存空间足够大
                LogHelper.e("ApkManager", "phone has enough storage");
                apkPath = FileManager.getInstance().getFilePathInPhone(mContext, desApkName);
                fos = mContext.openFileOutput(desApkName, Context.MODE_PRIVATE);
            } else {
                if (fileManager.isSDExist()) {
                    if (fileManager.hasEnoughExternalMemory()) {
                        // sd存在且剩余空间足够大
                        LogHelper.e("ApkManager", "sd has enough storage");
                        apkPath = fileManager.getFilePathInSD(desApkName);
                        fos = new FileOutputStream(apkFile);
                    } else {
                        LogHelper.e("ApkManager", "sd has no enough storage");
                    }
                } else {
                    LogHelper.e("ApkManager", "sd unAvailable");
                }
            }
            if (null == apkPath) { // 手机内存不足
                return false;
            }
            apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                temp = apkFile.createNewFile();
                LogHelper.e("ApkManager", "file don't exist, create :" + temp);
            }
            is = mContext.getAssets().open(srcApkName);
            if (!read(is, fos)) {
                temp = apkFile.delete();
                LogHelper.e("ApkManager", "copy failed，delete:" + temp);
                return false;
            } else {
                // 校验MD5值
                String md5 = UtilMD5.getMD5String(apkFile);
                LogHelper.e("ApkManager", "copy success,md5值:" + md5);
                if (!MD5.equals(md5)) {
                    temp = apkFile.delete();
                    LogHelper.e("ApkManager", "md5 error,delete:" + temp);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LogHelper.e("ApkManager", "error occurs... detail:" + e.toString());
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 文件读写
     *
     * @param is
     * @param fos
     * @throws Exception
     */
    private boolean read(InputStream is, FileOutputStream fos) {
        try {
            byte[] buffer = new byte[1024];
            int byteCount;
            //循环从输入流读取 buffer字节
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount); // 将读取的输入流写入输出流
            }
            fos.flush(); // 刷新缓冲区
            return true;
        } catch (Exception e) {
            LogHelper.e("ApkManager", "read error:" + e.toString());
        } finally {
            try {
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取apk包的包名 版本名
     *
     * @param mContext
     */
    private void getApkInfo(String apkPath, Context mContext) {
        LogHelper.e("ApkManager", "getApkInfo...");
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (null != packageInfo) {
            this.apkPackageName = packageInfo.packageName;
            this.apkVersionName = packageInfo.versionName;
        }
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    public String getApkVersionName() {
        return apkVersionName;
    }

    public String getApkPath() {
        return apkPath;
    }

}
