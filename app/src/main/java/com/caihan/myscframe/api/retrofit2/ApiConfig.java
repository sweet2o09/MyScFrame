package com.caihan.myscframe.api.retrofit2;

/**
 * Created by caihan on 2017/4/1.
 */

public interface ApiConfig {

    String BASE_URL = "http://bobo.yimwing.com";
    String NEWS_URI = "/api/version/android_new";

    int DEFAULT_CONNECT_TIMEOUT = 10;
    int DEFAULT_READ_TIMEOUT = 20;
    int DEFAULT_WRITE_TIMEOUT = 20;
    int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;//10MB

    String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    String CONNECTEXCEPTION = "网络连接异常，请检查您的网络状态";
    String UNKNOWNHOSTEXCEPTION = "网络异常，请检查您的网络状态";
    int WRONG_1 = 100;
    int WRONG_2 = 101;
}
