package com.android.pluginapk.dao;

import java.text.ParseException;

import android.content.Context;

import com.android.pluginapk.business.IControl;
import com.android.pluginapk.exception.NetworkException;
import com.android.pluginapk.result.PhoneIdResult;
import com.android.pluginapk.util.JsonParser;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public class ControlDebugDao extends Dao implements IControl {

    public ControlDebugDao(Context mContext) {
        super(mContext);
        String s = "{\"message\":\"success\",\"result\":0,\"list\":["
                + "{\"detailurl\":\"111\",\"imgid\":2,\"imgurl\":\"22222222222\",\"seqno\":1,\"title\":\"ad\"},"
                + "{\"detailurl\":\"http://\",\"imgid\":1,\"imgurl\":\"221211111111111\",\"seqno\":2,\"title\":\"advert\"}]}";

        String ret = "{   \"result\":\"0\",\"message\":\"\",\"data\":["
                + "{\"imgid\":\"1\",\"seqno\":1,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"掌上社保\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"},"
                + "{\"imgid\":\"2\",\"seqno\":2,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"拾味馆\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"},"
                + "{\"imgid\":\"3\",\"seqno\":3,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"无锡市民卡\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"}]}";
    }

    @Override
    public PhoneIdResult getPhoneId() throws NetworkException,
            ParseException {
        // TODO Auto-generated method stub
        String ret = "{\"cmd\":\"getid\",\"phoneid\":\"123456\"}";

        PhoneIdResult result = new PhoneIdResult();
        result = (PhoneIdResult) JsonParser.toResult(result, ret);
        return result;
    }


}
