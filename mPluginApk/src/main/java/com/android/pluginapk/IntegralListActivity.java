package com.android.pluginapk;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pluginapk.core.ResManager;
import com.android.pluginapk.util.UtilCom;
import com.android.pluginapk.util.UtilNetwork;
import com.android.pluginapk.util.UtilPhone;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/6
 */
public class IntegralListActivity {

    private Activity proxyActivity;
    private ResManager resManager;
    private View rootView;

    private String apkPath;

    private ListView listview;

    /**
     * 初始化
     *
     * @param proxyActivity
     * @return
     */
    public int initSdkPlugin(Activity proxyActivity) {
        // 网络未连接
        if (TextUtils.isEmpty(UtilNetwork.getNetworkType(proxyActivity))) {
            return 1;
        }
        // 无sim卡
        if (UtilCom.getImsi(proxyActivity).equals("0")) {
            return 1;
        }
        return 0;
    }

    private int startSdkPlugin(Activity proxyActivity, String apkPath) {
        this.proxyActivity = proxyActivity;
        this.apkPath = apkPath;
        setContentView();
        findView();
        initView();
        return 0;
    }


    private void setContentView() {
        resManager = ResManager.getInstance();
        rootView = resManager.getLayoutView(proxyActivity, apkPath, "activity_main");
        proxyActivity.setContentView(rootView);
    }

    private void findView() {
        if (null != rootView) {
            listview = (ListView) resManager.getWidget(rootView, "listView");
        }
    }

    private void initView() {
//        Drawable d1 = resManager.getDrawable(proxyActivity, apkPath, "dialog_bg");
//        llyt_parent.setBackgroundDrawable(d1);
//        ViewGroup.LayoutParams params1 = llyt_parent.getLayoutParams();
//        params1.width = 3 * UtilPhone.getScreenWidth(proxyActivity) / 4;
//        llyt_parent.setLayoutParams(params1);
//
//        Drawable d2 = resManager.getDrawable(proxyActivity, apkPath, "back");
//        btn_back.setCompoundDrawablesWithIntrinsicBounds(d2, null, null, null);
//
//        Drawable d3 = resManager.getDrawable(proxyActivity, apkPath, "content_bg");
//        llyt_content.setBackgroundDrawable(d3);
//
//        Drawable d4 = resManager.getDrawable(proxyActivity, apkPath, "btn_bg");
//        btn_confirm.setBackgroundDrawable(d4);
//        btn_confirm.setPadding(30, 10, 30, 10);
    }


}
