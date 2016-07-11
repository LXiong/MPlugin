package com.android.pluginapk.business;

import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.result.PhoneIdResult;

import java.text.ParseException;

/**
 * @author wd cswangduo_163_com
 * @ClassName: IControl
 * @Description: interface of business
 * @date 2013-7-17 上午10:53:51
 */
public interface IControl {

    PhoneIdResult getPhoneId() throws NetworkException, ParseException;

}
