package com.android.plugin.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @author wangduo
 * @description: 文件存储目录管理
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class FileManager {

    public static FileManager instance;

    private FileManager() {
    }

    public synchronized static FileManager getInstance() {
        if (null == instance) {
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * sd卡是否存在
     */
    public boolean isSDExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡下文件路径
     *
     * @param fileName
     * @return
     */
    public String getFilePathInSD(String fileName) {
        String dirPath = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/jfq/data/pluginApk/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath + fileName;
    }

    /**
     * 获取手机目录下文件路径
     *
     * @param fileName
     * @return
     */
    public String getFilePathInPhone(Context mContext, String fileName) {
        return mContext.getFilesDir() + "/" + fileName;
    }

    /**
     * SD卡中剩余空间是否足够？大于1M
     *
     * @return
     */
    public boolean hasEnoughExternalMemory() {
        return getAvailableExternalMemorySize() > 1;
    }

    /**
     * 手机内存剩余空间是否足够？大于1M
     *
     * @return
     */
    public boolean hasEnoughInternalMemory() {
        return getAvailableInternalMemorySize() > 1;
    }

    /**
     * 获取手机外部可用空间大小
     *
     * @return
     */
    public long getAvailableExternalMemorySize() {
        if (isSDExist()) {
            File path = Environment.getExternalStorageDirectory();//获取SDCard根目录
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            LogHelper.e("FileManager", "External:" + availableBlocks * blockSize);
            return availableBlocks * blockSize / 1000 / 1000;
        } else {
            LogHelper.e("FileManager", "External: 0 ");
            return 0;
        }
    }

    /**
     * 获取手机内部可用空间大小
     *
     * @return
     */
    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        LogHelper.e("FileManager", "Internal:" + availableBlocks * blockSize);
        return availableBlocks * blockSize / 1000 / 1000;
    }

}
