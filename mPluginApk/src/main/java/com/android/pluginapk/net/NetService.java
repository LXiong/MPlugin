package com.android.pluginapk.net;

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

    private final String ADDRES = "http://pay.ghwx.com.cn:8081/GameSdkApi/";

    public String doHttpPostRequest(String opcode, String reqData) throws Exception {
        StringBuffer sb = new StringBuffer(ADDRES);
        sb.append(opcode);
        String ret = NetUtils.doHttpPostRequest(sb.toString(), reqData);
        return ret;
    }

}
