package com.android.pluginapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.pluginapk.core.ResManager;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public class IntegralListAdapter extends BaseAdapter {

    private Context mContext;
    private String apkPath;
    private LayoutInflater mInflater;
    private ResManager resManager;

    public IntegralListAdapter(Context mContext, String apkPath) {
        this.mInflater = LayoutInflater.from(mContext);
        this.resManager = ResManager.getInstance();
        this.mContext = mContext;
        this.apkPath = apkPath;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = resManager.getLayoutView(mContext, apkPath, "activity_main");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder {

    }

}
