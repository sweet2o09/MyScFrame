package com.caihan.myscframe.api.retrofit2.exception;

import static com.caihan.scframe.api.retrofit2.ApiConfig.WRONG_1;
import static com.caihan.scframe.api.retrofit2.ApiConfig.WRONG_2;

/**
 * Created by caihan on 2017/1/14.
 * 对服务器返回的逻辑错误值进行封装
 */
public class ApiException extends RuntimeException {

    private int errorCode;

    public ApiException(int resultCode) {
        this(resultCode, getApiExceptionMessage(resultCode));
    }

    public ApiException(int resultCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = resultCode;
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * 映射服务器返回的自定义错误码，
     * （此时的http状态码在[200, 300) 之间）
     *
     * @param resultCode
     * @return
     */
    private static String getApiExceptionMessage(int resultCode) {
        String message = "";
        switch (resultCode) {
            case WRONG_1:
                message = "错误1";
                break;
            case WRONG_2:
                message = "错误2";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}

