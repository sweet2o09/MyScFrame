package com.caihan.myscframe.api.retrofit2.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caihan on 2017/4/4.
 * 拦截器Interceptors
 * 统一的请求参数
 */
public class CommonParamsInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request request;
        HttpUrl httpUrl = originRequest.url().newBuilder().
                addQueryParameter("paltform", "android").
                addQueryParameter("version", "1.0.0").build();
        request = originRequest.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
