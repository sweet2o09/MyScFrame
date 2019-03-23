package com.caihan.scframe.api;

import com.caihan.scframe.api.callback.RequestDownloadCallBack;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.request.DownloadRequest;
import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author caihan
 * @date 2019/3/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class ScHttpManager {

    private ScHttpManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * get请求,返回所有请求数据
     *
     * @param url
     * @return
     */
    public static Observable<String> get(String url) {
        return get(url,String.class);
    }

    /**
     * get请求
     *
     * @param url   链接
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Class<T> clazz) {
        return new GetRequest(url)
                .execute(clazz);
    }

    /**
     * get请求
     *
     * @param <T>
     * @param url   链接
     * @param proxy
     * @return
     */
    public static <T> Observable<T> get(String url, CallClazzProxy<? extends ApiResult<T>, T> proxy) {
        return new GetRequest(url)
                .execute(proxy);
    }

    /**
     * get请求
     *
     * @param url       链接
     * @param keyValues 参数
     * @param clazz     实体类
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Map<String, String> keyValues, Class<T> clazz) {
        return new GetRequest(url)
                .params(keyValues)
                .execute(clazz);
    }

    /**
     * post请求
     *
     * @param url   链接
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Class<T> clazz) {
        return new PostRequest(url)
                .execute(clazz);
    }

    /**
     * post请求
     *
     * @param url       链接
     * @param keyValues 参数
     * @param clazz     实体类
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Map<String, String> keyValues, Class<T> clazz) {
        return new PostRequest(url)
                .params(keyValues)
                .execute(clazz);
    }


    /**
     * 下载
     *
     * @param downLoadUrl 下载地址(需要包含域名)
     * @param savePath    保存的路径
     * @param saveName    保存的名字
     * @param callBack    Rx回调
     * @return
     */
    public static Disposable downLoad(String downLoadUrl, String savePath, String saveName,
                                      final RequestDownloadCallBack callBack) {
        return new DownloadRequest(downLoadUrl)
                .savePath(savePath)
                .saveName(saveName)
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void onStart() {
                        if (callBack != null) {
                            callBack.onStart();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        if (callBack != null) {
                            callBack.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        if (callBack != null) {
                            long progress = bytesRead * 100 / contentLength;
                            callBack.update(progress, done);
                        }
                    }

                    @Override
                    public void onComplete(String path) {
                        if (callBack != null) {
                            callBack.onComplete(path);
                        }
                    }
                });
    }



    /**
     * 取消订阅
     */
    public static void cancelSubscription(Disposable disposable) {
        EasyHttp.cancelSubscription(disposable);
    }

}
