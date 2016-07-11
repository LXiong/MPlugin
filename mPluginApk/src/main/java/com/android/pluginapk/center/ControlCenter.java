package com.android.pluginapk.center;

import android.content.Context;

import com.android.pluginapk.app.MyApplication;
import com.android.pluginapk.business.IControl;
import com.android.pluginapk.dao.ControlDao;
import com.android.pluginapk.dao.ControlDebugDao;
import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.result.PhoneIdResult;

import java.text.ParseException;

/**
 * @author wd cswangduo_163_com
 * @ClassName: ControlCenter
 * @Description: TODO
 * @date 2013-12-15 下午2:12:13
 */
public class ControlCenter {

    private static ControlCenter instance;
    private IControl dao;

    private ControlCenter(Context mContext) {
        if (!MyApplication.isDebug()) {
            dao = new ControlDao(mContext);
        } else {
            dao = new ControlDebugDao(mContext);
        }
    }

    public static synchronized ControlCenter getInstance(Context mContext) {
        if (null == instance) {
            instance = new ControlCenter(mContext);
        }
        return instance;
    }

    public PhoneIdResult getPhoneId() throws NetworkException, ParseException {
        return dao.getPhoneId();
    }

}
