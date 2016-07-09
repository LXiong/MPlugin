package com.android.plugin;

import android.app.Activity;
import android.content.Intent;

import com.android.plugin.ui.IntegralListActivity;

/**
 * @author wangduo
 * @description: 跳转到积分墙预热
 * @email: cswangduo@163.com
 * @date: 16/7/5
 */
public class SdkPluginServer {

    private static SdkPluginServer instance;

    private SdkPluginServer() {
    }

    public synchronized static SdkPluginServer getInstance() {
        if (null == instance) {
            instance = new SdkPluginServer();
        }
        return instance;
    }

    public synchronized int startSdkServer(Activity mActivity) {
        Intent intent = new Intent();
        intent.setClass(mActivity, IntegralListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mActivity.startActivity(intent);
        return 0;
    }

}
