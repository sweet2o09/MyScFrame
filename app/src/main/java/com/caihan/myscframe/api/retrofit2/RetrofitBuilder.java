package com.caihan.myscframe.api.retrofit2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.caihan.myscframe.api.retrofit2.ApiConfig.BASE_URL;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_CONNECT_TIMEOUT;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_READ_TIMEOUT;
import static com.caihan.myscframe.api.retrofit2.ApiConfig.DEFAULT_WRITE_TIMEOUT;


/**
 * Created by caihan on 2017/5/5.
 */
public final class RetrofitBuilder {

    boolean retryOnConnectionFailure;
    boolean setCacheStatus;
    boolean cookieJarStatus;
    int connectTimeout;
    int readTimeout;
    int writeTimeout;
    List<ConnectionSpec> connectionSpecs;
    CallAdapter.Factory callAdapterF;
    Converter.Factory converterF;
    String baseUrl;

    OkHttpClient.Builder okHttpBuilder;

    public RetrofitBuilder() {
        retryOnConnectionFailure = true;
        setCacheStatus = false;
        cookieJarStatus = false;
        connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        readTimeout = DEFAULT_READ_TIMEOUT;
        writeTimeout = DEFAULT_WRITE_TIMEOUT;
        connectionSpecs = Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS);
        callAdapterF = RxJava2CallAdapterFactory.create();
        converterF = GsonConverterFactory.create();
        baseUrl = BASE_URL;
        // 创建OkHttpClient
        okHttpBuilder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(retryOnConnectionFailure)
                // 支持HTTPS, 明文Http与比较新的Https
                .connectionSpecs(connectionSpecs);
    }

    public RetrofitBuilder setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        return this;
    }

    public RetrofitBuilder setSetCacheStatus(boolean setCacheStatus) {
        this.setCacheStatus = setCacheStatus;
        return this;
    }

    public RetrofitBuilder setCookieJarStatus(boolean cookieJarStatus) {
        this.cookieJarStatus = cookieJarStatus;
        return this;
    }

    public RetrofitBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RetrofitBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RetrofitBuilder setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitBuilder setConnectionSpecs(List<ConnectionSpec> connectionSpecs) {
        this.connectionSpecs = connectionSpecs;
        return this;
    }

    public RetrofitBuilder setCallAdapterF(CallAdapter.Factory callAdapterF) {
        this.callAdapterF = callAdapterF;
        return this;
    }

    public RetrofitBuilder setConverterF(Converter.Factory converterF) {
        this.converterF = converterF;
        return this;
    }

    public RetrofitBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitFactory build() {
        return RetrofitFactory.getInstance(this);
    }
}
