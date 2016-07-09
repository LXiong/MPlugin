package com.android.plugin.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.plugin.data.Config;
import com.android.plugin.util.ApkManager;
import com.android.plugin.util.LogHelper;
import com.android.plugin.util.MyClassLoader;

import java.lang.reflect.Method;

/**
 * @author wangduo
 * @description: 积分墙页面
 * @email: cswangduo@163.com
 * @date: 16/7/5
 */
public class IntegralListActivity extends Activity {

    private Class<?> cl;
    private Object instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        init();
    }

    private void setOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void init() {
        try {
            String className = Config.CLS_INTEGRALLIST_NAME;
            this.cl = MyClassLoader.loadClass(this, className);
            this.instance = cl.newInstance();
            if (null != cl) {
                int resultCode = initSdkPlugin();
                if (0 == resultCode) {
                    startSdkPlugin();
                }
            } else {
                LogHelper.e("SdkPayActivity", "cl null");
//                handleInitError(ResultCons.RESULT_100, 0);
            }
        } catch (Exception e) {
            LogHelper.e("SdkPayActivity", "exception occur");
//            handleInitError(ResultCons.RESULT_100, 0);
        }
    }

    private int initSdkPlugin() throws Exception {
        Method mInitSdkPluginPay = cl.getDeclaredMethod("initSdkPlugin", Activity.class);
        mInitSdkPluginPay.setAccessible(true);
        return (Integer) mInitSdkPluginPay.invoke(instance, this);
    }

    private int startSdkPlugin() throws Exception {
        Method mStartSdkPluginPay = cl.getDeclaredMethod("startSdkPlugin", Activity.class, String.class);
        mStartSdkPluginPay.setAccessible(true);
        return (Integer) mStartSdkPluginPay.invoke(instance, this, ApkManager.getInstance().getApkPath());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onRestoreInstanceState", Bundle.class);
                localMethod.setAccessible(true);
                localMethod.invoke(instance, savedInstanceState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onStart");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onStart();
    }

    @Override
    protected void onRestart() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onRestart");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onResume");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onSaveInstanceState", Bundle.class);
                localMethod.setAccessible(true);
                localMethod.invoke(instance, outState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onPause");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onStop");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onDestroy");
                localMethod.setAccessible(true);
                localMethod.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onActivityResult", Integer.TYPE, Integer.TYPE, Intent.class);
                localMethod.setAccessible(true);
                localMethod.invoke(instance, requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (null != cl) {
            try {
                Method localMethod = cl.getDeclaredMethod("onConfigurationChanged", Configuration.class);
                localMethod.setAccessible(true);
                localMethod.invoke(instance, newConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.cl) {
            try {
                Method monKeyDown = cl.getDeclaredMethod("onTouchEvent", MotionEvent.class);
                monKeyDown.setAccessible(true);
                Boolean temp = (Boolean) monKeyDown.invoke(instance, event);
                return temp.booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null != this.cl) {
            try {
                Method monKeyDown = cl.getDeclaredMethod("onKeyDown", Integer.TYPE, KeyEvent.class);
                monKeyDown.setAccessible(true);
                Boolean temp = (Boolean) monKeyDown.invoke(instance, new Integer(keyCode), event);
                return temp.booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (KeyEvent.KEYCODE_BACK == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
