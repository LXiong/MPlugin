package com.android.pluginapk.net;

import com.android.pluginapk.BuildConfig;
import com.android.pluginapk.exception.NetworkException;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/9
 */
public class NetService {

    private static NetService instance;

    private NetService() {
    }

    public static synchronized NetService getInstance() {
        if (null == instance) {
            instance = new NetService();
        }
        return instance;
    }

    public String doHttpPostRequest(String opcode, String reqData) throws NetworkException {
        StringBuffer sb = new StringBuffer(BuildConfig.ADDRESS);
        sb.append(opcode);
        String ret = NetUtils.doHttpPostRequest(sb.toString(), reqData);
        return ret;
    }

    public String doHttpPostRequest(String reqData) throws NetworkException {
        StringBuffer sb = new StringBuffer(BuildConfig.ADDRESS);
        String ret = NetUtils.doHttpPostRequest(sb.toString(), reqData);
        return ret;
    }

}
