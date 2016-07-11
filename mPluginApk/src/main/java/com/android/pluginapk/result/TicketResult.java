package com.android.pluginapk.result;

import org.json.JSONException;

/**
 * @author wangduo
 * @description: 积分
 * @email: cswangduo@163.com
 * @date: 16/7/10
 */
public class TicketResult extends BaseResult {

    private int ticket;

    @Override
    protected void parseDetail() throws JSONException {
        ticket = jobj.getInt("ticket");
    }

    public int getTicket() {
        return ticket;
    }

}
