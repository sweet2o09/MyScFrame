package com.caihan.myscframe.api.retrofit2.resmodel;

import java.io.Serializable;

/**
 * Created by caihan on 2017/1/11.
 * Function  数据响应模型
 */
public class HttpResult<T> implements Serializable {
    private static final String TAG = "RfRequst";
    private static final long serialVersionUID = -7337330725596281110L;


    /**
     * code : 1
     * data : {
     * "versionName":"2.0.4",
     * "versionCode":14,
     * "isQiangzhi":0,
     * "url":"\thttp://upload.guaishoubobo.com/app-bobo-release.apk",
     * "updateContent":"问题修复! 如升级意外失败,请从官网www.guaishoubobo.com重新下载!",
     * "size":25650067
     * }
     */

    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
