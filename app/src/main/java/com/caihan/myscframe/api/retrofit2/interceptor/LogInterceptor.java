package com.caihan.myscframe.api.retrofit2.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caihan on 2017/4/4.
 * 创建日志拦截器
 */

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("OkHttp", "HttpHelper1" + String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        Log.d("OkHttp", "HttpHelper2" + String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}
