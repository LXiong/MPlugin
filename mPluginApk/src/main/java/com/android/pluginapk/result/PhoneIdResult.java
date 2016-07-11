package com.android.pluginapk.result;

import org.json.JSONException;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/10
 */
public class PhoneIdResult extends BaseResult {

    private String phoneid;

    @Override
    protected void parseDetail() throws JSONException {
        phoneid = jobj.getString("phoneid");
    }

    public String getPhoneid() {
        return phoneid;
    }

}
