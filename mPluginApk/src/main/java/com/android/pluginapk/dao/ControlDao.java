package com.android.pluginapk.dao;

import java.text.ParseException;

import android.content.Context;

import com.android.pluginapk.business.IControl;
import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.net.NetService;
import com.android.pluginapk.result.PhoneIdResult;
import com.android.pluginapk.util.JsonParser;

/**
 * @author wd cswangduo_163_com
 * @ClassName:
 * @Description: TODO
 * @date 2013-6-8 下午6:29:06
 */
public class ControlDao extends Dao implements IControl {

    public final String TYPE_SOCIAL = "social";
    public final String TYPE_FUND = "fund";
    public final String TYPE_BANK = "bank";
    public final String TYPE_LFSERVICE = "lfsevice";
    public final String TYPE_MORE = "more";

    public ControlDao(Context mContext) {
        super(mContext);
    }

    @Override
    public PhoneIdResult getPhoneId() throws NetworkException,
            ParseException {
        // TODO Auto-generated method stub


        NetService netService = NetService.getInstance();
        String ret = netService.doHttpPostRequest("");

        PhoneIdResult result = new PhoneIdResult();
        result = (PhoneIdResult) JsonParser.toResult(result, ret);
        return result;
    }

}
