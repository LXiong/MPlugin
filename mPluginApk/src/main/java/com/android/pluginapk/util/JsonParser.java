package com.android.pluginapk.util;

import android.text.TextUtils;

import com.android.pluginapk.result.BaseResult;

import org.json.JSONException;

import java.text.ParseException;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/10
 */
public class JsonParser {

    /**
     * @param result
     * @param str
     * @return
     * @throws ParseException
     */
    public static BaseResult toResult(BaseResult result, final String str) throws ParseException {
        if (TextUtils.isEmpty(str)) {
            throw new ParseException("JSON parse error! empty string", 0);
        }
        try {
            result.fromJSONString(str);
        } catch (JSONException e) {
            throw new ParseException("JSON parse error!; " + str, 0);
        }
        return result;
    }

}
