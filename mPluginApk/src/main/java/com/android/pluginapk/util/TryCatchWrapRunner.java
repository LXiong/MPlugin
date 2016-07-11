package com.android.pluginapk.util;

import com.android.pluginapk.exception.NetworkException;

import java.text.ParseException;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public abstract class TryCatchWrapRunner<T> {

    public abstract T run() throws ParseException, NetworkException;

    public TaskResult<T> excute() {
        TaskResult<T> result = new TaskResult<T>();
        try {
            T runRet = run();
            result.setValue(runRet);
            result.setRetCode(TaskResultCode.OK);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            result.setRetCode(TaskResultCode.PARSE_ERROR);
        } catch (NetworkException e) {
            // TODO Auto-generated catch block
            result.setRetCode(e.getErrorCode());
        }

        return result;
    }
}
