package com.android.pluginapk.util;

import android.content.Context;

/**
 * @author wangduo
 * @description: 全局异常处理
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        sendExToServer(throwable.getMessage());
    }

    /**
     * 将全局异常发送给服务器
     */
    private void sendExToServer(String exMsg) {
//        new ReportCrashThread(mContext, exMsg).start();
    }

}

