package com.android.plugin.app;

import android.app.Application;
import android.content.Context;

import com.android.plugin.data.Config;
import com.android.plugin.util.ApkManager;
import com.android.plugin.util.MyClassLoader;

import java.lang.reflect.Method;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/4
 */
public class PluginApplication {

    private static boolean initSuccess = false; // 第三方是否 配置了application
    private static boolean copySuccess = false; // apk包是否存在于用户手机中

    public void init(Context mContext) {
        initSuccess = true;
        copySuccess = ApkManager.getInstance().createApkFile(mContext);
        initApplication(mContext);
    }

    private void initApplication(Context mContext) {
        try {
            String className = Config.CLS_APPLICATION_NAME;
            Class<?> cl = MyClassLoader.loadClass(mContext, className);
            Object instance = cl.newInstance();
            if (null != cl) {
                Method mOncreate = cl.getDeclaredMethod("onCreate", Context.class, String.class);
                mOncreate.setAccessible(true);
                mOncreate.invoke(instance, mContext, ApkManager.getInstance().getApkPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInitSuccess() {
        return initSuccess;
    }

    public static boolean isCopySuccess() {
        return copySuccess;
    }
}
