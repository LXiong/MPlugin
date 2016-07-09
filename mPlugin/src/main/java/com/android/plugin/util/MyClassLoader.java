package com.android.plugin.util;

import android.content.Context;

import dalvik.system.DexClassLoader;

/**
 * @author wangduo
 * @description: 类加载器
 * @email: cswangduo@163.com
 * @date: 16/7/5
 */
public class MyClassLoader extends DexClassLoader {

    private static MyClassLoader cjLoader;

    protected MyClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    /**
     * 返回dexPath对应的加载器
     */
    private final static MyClassLoader getClassLoader(Context ctx) {
        if (cjLoader == null) {
            // 获取到app的启动路径
            String dexPath = ApkManager.getInstance().getApkPath();
            String dexOutputPath = ctx.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
            cjLoader = new MyClassLoader(dexPath, dexOutputPath, null, ctx.getClassLoader());
        }
        return cjLoader;
    }

    /**
     * 根据className动态加载类并返回
     *
     * @param ctx
     * @param className
     * @return
     * @throws Exception
     */
    public final static Class<?> loadClass(Context ctx, String className) throws Exception {
        className = ApkManager.getInstance().getApkPackageName() + className;
        return getClassLoader(ctx).loadClass(className);
    }


}
