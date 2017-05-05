package com.caihan.myscframe.api.retrofit2.exception;

import android.net.ParseException;

import com.caihan.scframe.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by caihan on 2017/4/4.
 * 错误解析
 */
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            /**
             * 网络异常，http 请求失败，
             * 即 http 状态码不在 [200, 300) 之间, such as: "server internal error".
             */
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "服务器请求超时";
        } else if (e instanceof SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "服务器响应超时";
        } else if (e instanceof ApiException) {
            /**
             * 网络正常，http 请求成功，服务器返回逻辑错误
             */
            ex = new ResponeThrowable(e, ERROR.API_ERROR);
            ex.message = "逻辑错误";
        } else if (e instanceof UnknownHostException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络中断，请检查您的网络状态";
        } else {
            /**
             * 其他未知错误
             */
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            ex.message = (e.getMessage() != null && !StringUtils.isEmpty(e.getMessage())) ?
                    e.getMessage() : "未知错误";
        }
        return ex;
    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 逻辑错误
         */
        public static final int API_ERROR = 1007;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}

