package com.caihan.myscframe.api;


import com.caihan.myscframe.api.retrofit2.ApiService;
import com.caihan.myscframe.api.retrofit2.RetrofitBuilder;
import com.caihan.myscframe.api.retrofit2.RetrofitFactory;

/**
 * Created by caihan on 2017/1/11.
 * Function  网络通用接口
 */
public class ScApi {

    private static final String TAG = "MyRetrofit2";

    private volatile static ScApi sInstance = new ScApi();

    private RetrofitFactory mRetrofitFactory;

    public static ScApi getInstance() {
        if (sInstance == null) {
            synchronized (ScApi.class) {
                if (sInstance == null) {
                    sInstance = new ScApi();
                }
            }
        }
        return sInstance;
    }

    private ScApi() {
        mRetrofitFactory = new RetrofitBuilder().build();
    }

    public RetrofitFactory getRetrofitFactory() {
        return mRetrofitFactory;
    }

    public ApiService getApiService() {
        return mRetrofitFactory.getApiService();
    }
}