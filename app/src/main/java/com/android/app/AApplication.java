package com.android.app;

import android.app.Application;

import com.android.plugin.app.PluginApplication;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public class AApplication extends Application {

    private PluginApplication app = new PluginApplication();

    @Override
    public void onCreate() {
        super.onCreate();
        app.init(getApplicationContext());
    }

}
