package com.android.pluginapk.app;

import android.content.Context;

import com.android.pluginapk.center.ControlCenter;
import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.result.PhoneIdResult;
import com.android.pluginapk.util.CrashHandler;
import com.android.pluginapk.util.LogHelper;

import java.text.ParseException;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class MyApplication {

    private String TAG = "MyApplication";
    private static String phoneId = "-1";

    private final static boolean isDebug = true;

    public void onCreate(Context mContext, String apkPath) {
        // 设置捕获全局异常
        CrashHandler.getInstance().init(mContext);
        init(mContext, apkPath);
    }

    private void init(final Context mContext, String apkPath) {
        LogHelper.e(TAG, "init");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PhoneIdResult result = ControlCenter.getInstance(mContext).getPhoneId();
                    phoneId = result.getPhoneid();
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static String getPhoneId() {
        return phoneId;
    }

}
