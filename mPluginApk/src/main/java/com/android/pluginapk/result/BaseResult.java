package com.android.pluginapk.result;

import com.android.pluginapk.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/10
 */
public abstract class BaseResult {

    protected String cmd = null;

    protected JSONObject jobj = null;

    public void fromJSONString(String jsonStr) throws JSONException {
        LogHelper.d("JSON Result", jsonStr);
        jobj = new JSONObject(jsonStr);
        this.cmd = jobj.getString("cmd");
        parseDetail();
    }

    public String getCmd() {
        return cmd;
    }

    protected abstract void parseDetail() throws JSONException;

}
