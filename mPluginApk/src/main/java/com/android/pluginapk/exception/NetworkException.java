package com.android.pluginapk.exception;

import com.android.pluginapk.util.TaskResultCode;

import java.io.Serializable;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public class NetworkException extends Exception implements Serializable {

    private static final long serialVersionUID = -519638538214334215L;

    private TaskResultCode errorCode = null;

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(TaskResultCode code, String message) {
        super(message);
        errorCode = code;
    }

    public NetworkException(TaskResultCode code, String message, Throwable cause) {
        super(message, cause);
        errorCode = code;
    }

    public TaskResultCode getErrorCode() {
        return errorCode;
    }

}
