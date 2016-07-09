package com.android.pluginapk.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/6
 */
public class UtilNetwork {

    /**
     * 获取网络类型
     *
     * @param mContext
     * @return
     */
    public static String getNetworkType(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null == info) {
            return "";
        }
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return "100";
        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return info.getSubtype() + "";
        }
        return "";
    }

    /**
     * 是否漫游
     *
     * @param mContext
     * @return
     */
    public static boolean isRoaming(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null == info || !info.isConnected()) {
            return false;
        }
        return info.isRoaming();
    }

    private static String btMac;

    /**
     * 获取蓝牙mac地址
     *
     * @param mContext
     * @return
     */
    public static String getBtMac(Context mContext) {
        if (null != btMac) {
            return btMac;
        }
        PackageManager pm = mContext.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.BLUETOOTH", mContext.getPackageName()));
        if (permission) {
            BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null != bAdapter && bAdapter.isEnabled()
                    && (btMac = bAdapter.getAddress()) != null) {
                return btMac;
            }
        }
        btMac = "";
        return btMac;
    }

    /**
     * 获取wifi mac地址
     *
     * @param mContext
     * @return
     */
    public static String getWifiMac(Context mContext) {
        WifiManager mWifi = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
        if (!mWifi.isWifiEnabled()) {
//            mWifi.setWifiEnabled(true);
            return "";
        }
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        String wifiMac = wifiInfo.getMacAddress();
        return null == wifiMac ? "" : wifiMac;
    }


}
