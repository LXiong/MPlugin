package com.android.pluginapk.app;

import android.content.Context;

import com.android.pluginapk.util.CrashHandler;
import com.android.pluginapk.util.LogHelper;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class MyApplication {

    private String TAG = "MyApplication";

    public void onCreate(Context mContext, String apkPath) {
        // 设置捕获全局异常
        CrashHandler.getInstance().init(mContext);
        init(mContext, apkPath);
    }

    private void init(Context mContext, String apkPath) {
        LogHelper.e(TAG, "init");
//        new InitReqThread(mContext).start();
    }

}
