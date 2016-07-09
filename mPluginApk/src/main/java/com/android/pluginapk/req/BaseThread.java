package com.android.pluginapk.req;

import com.android.pluginapk.net.NetService;

/**
 * Created by wangduo on 15/5/15.
 */
public abstract class BaseThread extends Thread {

    @Override
    public void run() {
        super.run();
        request();
    }

    protected abstract void request();

    protected String doHttpRequest(String opcode, byte[] req) {
        String ret = null;
        try {
            ret = NetService.getInstance().doHttpPostRequest(opcode, new String(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
