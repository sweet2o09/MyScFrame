package com.caihan.myscframe.api.retrofit2;

import android.util.Log;

import com.caihan.myscframe.api.retrofit2.interceptor.CommonParamsInterceptor;
import com.caihan.myscframe.api.retrofit2.interceptor.HeaderInterceptor;
import com.caihan.myscframe.api.retrofit2.interceptor.HttpLogInterceptor;
import com.caihan.scframe.ScFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.caihan.myscframe.api.retrofit2.ApiConfig.BASE_URL;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_CACHE_SIZE;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_CONNECT_TIMEOUT;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_READ_TIMEOUT;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_WRITE_TIMEOUT;


/**
 * Created by caihan on 2017/3/31.
 * Function  网络客户端
 */
public class RetrofitFactory {
    private static final String TAG = "RetrofitFactory";

    private volatile static RetrofitFactory sInstance = null;
    private ApiService mApiService;

    public static RetrofitFactory getInstance(RetrofitBuilder retrofitBuilder) {
        if (sInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitFactory(retrofitBuilder);
                }
            }
        }
        return sInstance;
    }

    /**
     * addNetworkInterceptor添加的是网络拦截器Network Interfacetor它会在request和response时分别被调用一次；
     * addInterceptor添加的是应用拦截器Application Interceptor他只会在response被调用一次。
     */
    private RetrofitFactory(RetrofitBuilder retrofitBuilder) {
        if (retrofitBuilder == null) {
            defaultRetrofitFactory();
        } else {
            newRetrofitFactory(retrofitBuilder);
        }
    }

    private void defaultRetrofitFactory() {
        // 创建OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 支持HTTPS, 明文Http与比较新的Https
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS));
        //添加缓存策略
//        setCache(builder);
        //自定义CookieJar
        cookieJar(builder);
        // 添加各种插入器
        addInterceptor(builder);

        mApiService = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService.class);
    }

    private void newRetrofitFactory(RetrofitBuilder retrofitBuilder) {
        if (retrofitBuilder.setCacheStatus) {
            //添加缓存策略
            setCache(retrofitBuilder.okHttpBuilder);
        }
        if (retrofitBuilder.cookieJarStatus) {
            //自定义CookieJar
            cookieJar(retrofitBuilder.okHttpBuilder);
        }
        // 添加各种插入器
        addInterceptor(retrofitBuilder.okHttpBuilder);
        mApiService = new Retrofit.Builder()
                .client(retrofitBuilder.okHttpBuilder.build())
                .addCallAdapterFactory(retrofitBuilder.callAdapterF)
                .addConverterFactory(retrofitBuilder.converterF)
//                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(retrofitBuilder.baseUrl)
                .build()
                .create(ApiService.class);
    }

    /**
     * 自定义CookieJar
     *
     * @param builder
     */
    private void cookieJar(OkHttpClient.Builder builder) {

        builder.cookieJar(new CookieJar() {
            final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);//保存cookie
                //也可以使用SP保存
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);//取出cookie
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
    }

    private void addInterceptor(OkHttpClient.Builder builder) {
        // 添加Header
        builder.addInterceptor(new HeaderInterceptor())
                //添加统一请求参数
                .addInterceptor(new CommonParamsInterceptor())
                // 添加http log日志拦截器
                .addInterceptor(new HttpLogInterceptor().get());
//        .addInterceptor(new CaheInterceptor())
//        .addNetworkInterceptor(new CaheInterceptor())

        // 添加调试工具
//        builder.networkInterceptors().add(new StethoInterceptor());
    }

    private void setCache(OkHttpClient.Builder builder) {
        // 添加缓存控制策略
        try {
            File cacheDir = new File(ScFrame.getContext().getCacheDir(), "OkHttpCache");
            Cache cache = new Cache(cacheDir, DEFAULT_CACHE_SIZE);
            builder.cache(cache);
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
    }

    public ApiService getApiService() {
        return mApiService;
    }

}
