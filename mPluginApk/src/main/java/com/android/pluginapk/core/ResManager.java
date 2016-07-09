package com.android.pluginapk.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.android.pluginapk.BuildConfig;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/6
 */
public class ResManager {

    private static ResManager instance;

    private Resources mResources;
    // key : apk包全路径, value : apk包中的资源resource
    private HashMap<String, Resources> map = new HashMap<>();

    private ResManager() {
    }

    public synchronized static ResManager getInstance() {
        if (null == instance) {
            instance = new ResManager();
        }
        return instance;
    }

    /**
     * @param mContext
     * @param apkPath    apk包存储的全路径
     * @param layoutName layout布局文件名字
     * @return
     */
    public View getLayoutView(Context mContext, String apkPath, String layoutName) {
        LayoutInflater mInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        Resources r = getResources(mContext, apkPath);
        int layoutId = r.getIdentifier(layoutName, "layout", BuildConfig.APPLICATION_ID);
        XmlResourceParser localXmlResourceParser = r.getLayout(layoutId);
        return mInflater.inflate(localXmlResourceParser, null, false);
    }

    /**
     * @param parentView 父view
     * @param idName     控件的id name
     * @return
     */
    public View getWidget(View parentView, String idName) {
        if (null != mResources) {
            int resId = mResources.getIdentifier(idName, "id", BuildConfig.APPLICATION_ID);
            return parentView.findViewById(resId);
        }
        return null;
    }

    /**
     * @param mContext
     * @param apkPath      apk包存储的全路径
     * @param drawableName drawable图片名字
     * @return
     */
    public Drawable getDrawable(Context mContext, String apkPath, String drawableName) {
        Resources r = getResources(mContext, apkPath);
        int drawableId = r.getIdentifier(drawableName, "drawable", BuildConfig.APPLICATION_ID);
        return r.getDrawable(drawableId);
    }

    /**
     * 获取 指定全路径下指定apk中 的资源Resource
     *
     * @param mContext
     * @param apkPath  apk全路径
     * @return
     */
    private Resources getResources(Context mContext, String apkPath) {
        if (map.containsKey(apkPath) && null != mResources) {
            return mResources;
        }
        try {
            Class cl = Class.forName("android.content.res.AssetManager");
            AssetManager assetManager = (AssetManager) cl.newInstance();

            Method mAddAssetPath = cl.getDeclaredMethod("addAssetPath", String.class);
            mAddAssetPath.setAccessible(true);
            mAddAssetPath.invoke(assetManager, apkPath);

            Resources tempResources = mContext.getResources();
            DisplayMetrics metrics = tempResources.getDisplayMetrics();
            Configuration configuration = tempResources.getConfiguration();
            Resources resources = new Resources(assetManager, metrics, configuration);
            if (resources != null) {
                map.put(apkPath, resources);
                mResources = resources;
            }
            return resources;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private Resources getResources(Context paramContext, String paramString) {
//        try {
//            Class localClass = Class.forName("android.content.res.AssetManager");
//            Object localObject = localClass.newInstance();
//            localClass.getDeclaredMethod("addAssetPath", new Class[]{String.class}).invoke(localObject, new Object[]{paramString});
//            Resources localResources1 = paramContext.getResources();
//            Class[] arrayOfClass = new Class[3];
//            arrayOfClass[0] = localClass;
//            arrayOfClass[1] = localResources1.getDisplayMetrics().getClass();
//            arrayOfClass[2] = localResources1.getConfiguration().getClass();
//            Constructor localConstructor = Resources.class.getConstructor(arrayOfClass);
//            Object[] arrayOfObject = new Object[3];
//            arrayOfObject[0] = localObject;
//            arrayOfObject[1] = localResources1.getDisplayMetrics();
//            arrayOfObject[2] = localResources1.getConfiguration();
//            Resources localResources2 = (Resources) localConstructor.newInstance(arrayOfObject);
//            return localResources2;
//        } catch (Exception localException) {
//            localException.printStackTrace();
//        }
//        return null;
//    }


}
