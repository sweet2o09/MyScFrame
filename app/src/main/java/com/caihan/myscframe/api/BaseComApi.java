package com.caihan.myscframe.api;

import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by caihan on 2017/4/6.
 */

public class BaseComApi {
    private static final String TAG = "BaseComApi";

    /**
     * 自己封装的方法，来减少代码亮
     *
     * @param call retrofit的call
     * @param <T>  泛型
     * @return Flowable
     */
    public static <T> Flowable<T> create(@NonNull final Call<T> call) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(final FlowableEmitter<T> e) throws Exception {
                //设置取消监听
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.e(TAG, "cancel: ");
                        if (!call.isCanceled()) {
                            call.cancel();
                        }
                    }
                });

                //同步执行请求，把线程管理交给Rx
                try {
                    Response<T> response = call.execute();
                    Log.e(TAG, "onResponse: ");
                    if (!e.isCancelled()) {
                        e.onNext(response.body());
                        e.onComplete();
                    }
                } catch (Exception exception) {
                    Log.e(TAG, "exception with: exception = [" + exception.getMessage() + "]");
                    if (!e.isCancelled()) {
                        Log.e(TAG, "onResponse: no cancel");
                        e.onError(exception);
                        e.onComplete();
                    }
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 后台线程执行同步，主线程执行异步操作
     * 并且拦截所有错误，不让app崩溃
     *
     * @param <T> 数据类型
     * @return Transformer
     */
    public static <T> FlowableTransformer<T, T> background() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .onErrorResumeNext(Flowable.<T>empty())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
