package com.android.pluginapk.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pluginapk.center.ControlCenter;
import com.android.pluginapk.core.ResManager;
import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.util.TaskResult;
import com.android.pluginapk.util.TryCatchWrapRunner;
import com.android.pluginapk.util.UtilCom;
import com.android.pluginapk.util.UtilNetwork;
import com.android.pluginapk.util.UtilPhone;

import java.text.ParseException;

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

    private ControlCenter center;

    private AlertDialog loadingDlg;

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
        center = ControlCenter.getInstance(proxyActivity.getApplicationContext());
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

    public void getWorkkey() {
        showLoadingDialog("正在加载中...");
        AsyncTask<Object, Object, TaskResult<?>> task = new AsyncTask<Object, Object, TaskResult<?>>() {

            @Override
            protected TaskResult<?> doInBackground(Object... params) {
                // TODO Auto-generated method stub

                TryCatchWrapRunner<Object> runner = new TryCatchWrapRunner<Object>() {
                    @Override
                    public Object run() throws NetworkException, ParseException {
                        // TODO Auto-generated method stub
                        return center.getPhoneId();
                    }
                };
                return runner.excute();
            }

            protected void onPostExecute(TaskResult<?> mTaskResult) {
                hideLoadingDialog();
                if (UtilCom.checkTaskError(mTaskResult, true)) {
                    return;
                }

            }
        };
        task.execute(new Object());
    }

    protected void showLoadingDialog(String msg) {
        if (null == loadingDlg) {
            loadingDlg = new AlertDialog.Builder(proxyActivity).create();
        }
        if (loadingDlg.isShowing()) {
            return;
        }
        loadingDlg.setMessage(msg);
        loadingDlg.setContentView(null);
        loadingDlg.setCanceledOnTouchOutside(false);
        loadingDlg.show();
    }

    protected void hideLoadingDialog() {
        if (null != loadingDlg) {
            loadingDlg.dismiss();
            loadingDlg = null;
        }
    }

}
