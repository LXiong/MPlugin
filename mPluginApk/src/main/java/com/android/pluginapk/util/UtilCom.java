package com.android.pluginapk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/6
 */
public class UtilCom {

    public static String getFileMd5(String fullPath) {
        File file = new File(fullPath);
        if (file.exists() && file.isFile()) {
            return UtilMD5.getMD5String(file);
        }
        return null;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getBrand() {
        String brand = Build.BRAND;
        return brand == null ? "" : brand;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneType() {
        String model = Build.MODEL;
        return model == null ? "" : model;
    }

    public static String getOsVersion() {
        String osVersion = Build.VERSION.RELEASE;
        return osVersion == null ? "" : "android " + osVersion;
    }

    public static String getImei(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei == null ? "000000000000000" : imei;
    }

    public static String getImsi(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        return imsi == null ? "0" : imsi;
    }

    public static String getIccid(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = tm.getSimSerialNumber();
        return iccid == null ? "" : iccid;
    }

    public static String getPhoneNum(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNum = tm.getLine1Number();
        return phoneNum == null ? "" : phoneNum;
    }

    public static String getAndroidId(Context mContext) {
        String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (null != androidId) {
            return androidId;
        }
        return "";
    }

    public static String getUUID(Context mContext) {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = tm.getDeviceId();
            tmSerial = tm.getSimSerialNumber();
            androidId = getAndroidId(mContext);
            UUID uuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String uniqueId = uuid.toString();
            return uniqueId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCellId() {
        GsmCellLocation gcl = new GsmCellLocation();
        int cellId = gcl.getCid();
        String temp = cellId == -1 ? "" : cellId + "";
        return temp;
    }

    private static String ua;

    public static String getUA(Context mContext) {
        if (null != ua) {
            return ua;
        }
        try {
            WebView webview;
            webview = new WebView(mContext);
            webview.layout(0, 0, 0, 0);
            WebSettings settings = webview.getSettings();
            ua = settings.getUserAgentString();
            if (ua != null) {
                return ua;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static String macAddress;

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getLocalMacAddressFromIp() {
        if (null != macAddress) {
            return macAddress;
        }
        try {
            byte[] mac;
            String ipAddress = getLocalIpAddress();
            if (null != ipAddress) {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                NetworkInterface ne = NetworkInterface.getByInetAddress(inetAddress);
                mac = ne.getHardwareAddress();
                macAddress = byte2hex(mac);
                if (null != macAddress) {
                    return macAddress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs.append("0").append(stmp);
            } else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    /**
     * 获取手机内部总空间大小
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory(); //Gets the Android data directory
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();      //每个block 占字节数
        long totalBlocks = stat.getBlockCount();   //block总数
        return totalBlocks * blockSize / 1000 / 1000;
    }

    /**
     * 获取手机内部可用空间大小
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / 1000 / 1000;
    }

    private static boolean isSDExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机外部可用空间大小
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (isSDExist()) {
            File path = Environment.getExternalStorageDirectory();//获取SDCard根目录
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize / 1000 / 1000;
        } else {
            return 0;
        }
    }

    /**
     * 获取手机外部总空间大小
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (isSDExist()) {
            File path = Environment.getExternalStorageDirectory(); //获取SDCard根目录
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize / 1000 / 1000;
        } else {
            return 0;
        }
    }

    private static String appName;

    /**
     * 获取应用程序名字
     *
     * @param mContext
     * @return
     */
    public static String getAppName(Context mContext) {
        if (null != appName) {
            return appName;
        }
        try {
            PackageManager packageManager = mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(mContext), 0);
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
            if (appName != null) {
                return appName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String packageName;

    /**
     * 获取包名
     *
     * @param mContext
     * @return
     */
    public final static String getPackageName(Context mContext) {
        if (null != packageName) {
            return packageName;
        }
        packageName = mContext.getPackageName();
        if (null != packageName) {
            return packageName;
        }
        return "";
    }

    private static String appVersion;

    /**
     * 获取程序版本号
     *
     * @param mContext
     * @return
     */
    public final static String getAppVersion(Context mContext) {
        if (null != appVersion) {
            return appVersion;
        }
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getPackageName(mContext), 0);
            appVersion = info.versionName;
            if (null != appVersion) {
                return appVersion;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取当前正在运行app
     *
     * @param mContext
     * @return
     */
//    public static ArrayList<TlvRunningApp> getRunningApps(Context mContext) {
//        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
//
//        PackageManager pm = mContext.getPackageManager();
//        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//
//        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
//            for (ApplicationInfo appInfo : applicationInfos) {
//                // 如果是系统应用则重新轮询
//                if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
//                    continue;
//                }
//                if (runningAppProcessInfo.processName.equals(appInfo.processName)) {
//                    try {
//                        String temp_packName = appInfo.packageName;
//                        PackageInfo info = pm.getPackageInfo(temp_packName, 0);
//                        String temp_appVersion = info.versionName;
//                        String temp_appName = (String) pm.getApplicationLabel(appInfo);
//                        TlvRunningApp app = new TlvRunningApp();
//                        app.setAppName(temp_appName);
//                        app.setAppVersion(temp_appVersion);
//                        app.setAppPackage(temp_packName);
//                        runningApps.add(app);
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return runningApps;
//    }

}
