package com.caihan.scframe.api;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.caihan.scframe.api.interceptor.CustomSignInterceptor;
import com.caihan.scframe.api.interceptor.DynamicMapStrategy;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.IDiskConverter;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * @author caihan
 * @date 2019/3/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class ScHttp {
    //单例模式->双重检查模式
    private static final String TAG = "ScHttp";
    public static final long DEFAULT_MILLISECONDS = 60000;//默认的超时时间
    private static final int DEFAULT_RETRY_COUNT = 0;//默认重试次数
    private static final int DEFAULT_RETRY_INCREASEDELAY = 0;//默认重试叠加时间
    private static final int DEFAULT_RETRY_DELAY = 500;//默认重试延时
    private static final long DEFAULT_CACHE_MAX_SIZE = 50 * 1024 * 1024;//全局的缓存大小,默认50M


    private static Application sContext;
    private static Builder sBuilder;
    //volatile表示去掉虚拟机优化代码,但是会消耗少许性能,可忽略
    private volatile static ScHttp sInstance = null;

    public static ScHttp getInstance() {
        testInitialize();
        if (sInstance == null) {
            //同步代码块
            synchronized (ScHttp.class) {
                if (sInstance == null) {
                    sInstance = new ScHttp();
                }
            }
        }
        return sInstance;
    }

    private ScHttp() {
        EasyHttp.init(sContext);
        EasyHttp.getInstance()
                .debug(sBuilder.debugTag, sBuilder.openHttpLog)
                .setReadTimeOut(sBuilder.readTimeOut)
                .setWriteTimeOut(sBuilder.writeTimeout)
                .setConnectTimeout(sBuilder.connectTimeout)
                .setRetryCount(sBuilder.retryCount)//默认网络不好自动重试3次
                .setRetryDelay(sBuilder.retryDelay)//每次延时500ms重试
                .setRetryIncreaseDelay(sBuilder.retryIncreaseDelay)//每次延时叠加500ms
                .setBaseUrl(sBuilder.baseUrl)
                .setCacheDiskConverter(sBuilder.converter)//默认缓存使用序列化转化
                .setCacheMaxSize(sBuilder.cacheMaxSize)//设置缓存大小为50M
                .setCacheVersion(sBuilder.cacheersion)//缓存版本为1
                .setCertificates(sBuilder.certificates)//信任所有证书
                .addCommonHeaders(sBuilder.commonHeaders)//设置全局公共头
                .addCommonParams(sBuilder.commonParams);//设置全局公共参数

        if (sBuilder.hostnameVerifier != null) {
            EasyHttp.getInstance().setHostnameVerifier(sBuilder.hostnameVerifier);//全局访问规则
        }
        for (Interceptor interceptor : sBuilder.interceptors) {
            EasyHttp.getInstance().addInterceptor(interceptor);
        }
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application app, Builder builder) {
        sContext = app;
        if (builder == null) {
            return;
        }
        if (StringUtils.isEmpty(builder.baseUrl)) {
            throw new ExceptionInInitializerError("请设置全局URL ！");
        }
        sBuilder = builder;
        getInstance();
    }

    /**
     * 更改baseurl
     *
     * @param url
     */
    public static void changeBaseUrl(String url){
        EasyHttp.getInstance()
                .setBaseUrl(url);//设置全局公共参数
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 ScHttp.init() 初始化！");
        }
    }


    public static final class Builder {

        String baseUrl;
        String debugTag = TAG;
        boolean openHttpLog = false;
        long readTimeOut = DEFAULT_MILLISECONDS;//全局读取超时时间
        long writeTimeout = DEFAULT_MILLISECONDS;//全局写入超时时间
        long connectTimeout = DEFAULT_MILLISECONDS;//全局连接超时时间
        int retryCount = DEFAULT_RETRY_COUNT;//超时重试次数
        int retryDelay = DEFAULT_RETRY_DELAY;//延迟500ms重试
        int retryIncreaseDelay = DEFAULT_RETRY_INCREASEDELAY;//叠加延迟
        IDiskConverter converter = new SerializableDiskConverter();//默认缓存使用序列化转化
        long cacheMaxSize = 0;//全局的缓存大小
        int cacheersion = 1;//全局设置缓存的版本
        HostnameVerifier hostnameVerifier;//https的全局访问规则
        InputStream[] certificates;//https的全局自签名证书
        Converter.Factory factory;//全局设置Converter.Factory
        HttpHeaders commonHeaders;//添加全局公共请求参数
        HttpParams commonParams;//添加全局公共请求参数
        List<Interceptor> interceptors = new ArrayList<>();//添加全局拦截器

        public Builder() {
        }

        /**
         * 全局设置baseurl
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 调试模式,默认打开所有的异常调试
         */
        public Builder debug(String debugTag, boolean openHttpLog) {
            this.debugTag = debugTag;
            this.openHttpLog = openHttpLog;
            return this;
        }

        /**
         * 全局读取超时时间
         */
        public Builder setReadTimeOut(long readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        /**
         * 全局写入超时时间
         */
        public Builder setWriteTimeOut(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        /**
         * 全局连接超时时间
         */
        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 超时重试次数,超时重试延迟时间,超时重试延迟叠加时间
         */
        public Builder setRetryInfo(int retryCount, int retryDelay, int retryIncreaseDelay) {
            if (retryCount < 0) {
                throw new IllegalArgumentException("retryCount must > 0");
            }
            if (retryIncreaseDelay < 0) {
                throw new IllegalArgumentException("retryIncreaseDelay must > 0");
            }
            this.retryCount = retryCount;
            this.retryDelay = retryDelay;
            this.retryIncreaseDelay = retryIncreaseDelay;
            return this;
        }

        /**
         * 全局设置缓存的转换器,全局的缓存大小,全局设置缓存的版本，默认为1，缓存的版本号
         */
        public Builder setCacheInfo(IDiskConverter converter, long cacheMaxSize, int cacheersion) {
            if (cacheersion < 0) {
                throw new IllegalArgumentException("cacheersion must > 0");
            }
            this.converter = converter;
            this.cacheMaxSize = cacheMaxSize;
            this.cacheersion = cacheersion;
            return this;
        }

        /**
         * https的全局访问规则
         */
        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * https的全局自签名证书
         */
        public Builder setCertificates(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        /**
         * 全局设置Converter.Factory,默认GsonConverterFactory.create()
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.factory = factory;
            return this;
        }

        /**
         * 添加全局公共请求参数
         */
        public Builder addCommonHeaders(HttpHeaders commonHeaders) {
            if (this.commonHeaders == null) {
                this.commonHeaders = new HttpHeaders();
            }
            this.commonHeaders.put(commonHeaders);
            return this;
        }

        /**
         * 添加全局公共请求参数
         */
        public Builder addCommonParams(HttpParams commonParams) {
            if (this.commonParams == null) {
                this.commonParams = new HttpParams();
            }
            this.commonParams.put(commonParams);
            return this;
        }

        /**
         * 添加全局拦截器
         */
        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        /**
         * 添加参数签名拦截器{@link com.caihan.scframe.api.interceptor.CustomSignInterceptor}
         *
         * @param strategy
         */
        public Builder addSignInterceptor(final DynamicMapStrategy strategy){
            interceptors.add(new CustomSignInterceptor(strategy));
            return this;
        }

        /**
         * 添加参数签名拦截器{@link com.caihan.scframe.api.interceptor.TokenInterceptor}
         *
         * @param interceptor
         */
        public Builder addTokenInterceptor(final BaseExpiredInterceptor interceptor){
            interceptors.add(interceptor);
            return this;
        }

    }

}