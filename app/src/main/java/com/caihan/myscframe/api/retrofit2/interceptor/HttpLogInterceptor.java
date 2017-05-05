package com.caihan.myscframe.api.retrofit2.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by caihan on 2017/4/4.
 */

public class HttpLogInterceptor {
    HttpLoggingInterceptor mLoggingInterceptor;

    public HttpLoggingInterceptor get() {
        mLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {

                    @Override
                    public void log(String message) {
                        Log.e("OkHttp", "log = " + message);
                    }

                });
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return mLoggingInterceptor;
    }

}
