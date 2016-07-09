package com.android.pluginapk.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/6
 */
public class UtilPhone {

    public static int getScreenWidth(Context mContext) {
        return getD(mContext).getWidth();
    }

    public static int getScreenHeight(Context mContext) {
        return getD(mContext).getHeight();
    }

    private static Display getD(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay();
    }

}
