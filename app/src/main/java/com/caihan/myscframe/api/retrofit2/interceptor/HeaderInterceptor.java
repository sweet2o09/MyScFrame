package com.caihan.myscframe.api.retrofit2.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caihan on 2017/4/4.
 * 拦截器Interceptors
 * 使用addHeader()不会覆盖之前设置的header,若使用header()则会覆盖之前的header
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    /**
     * 使用默认的请求头
     */
    public HeaderInterceptor(){
    }

    /**
     * 自行添加请求头
     * @param headers
     */
    public HeaderInterceptor(Map<String, String> headers){
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }else {
            builder.addHeader("version", "1");
            builder.addHeader("time", System.currentTimeMillis() + "");
        }

        Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
