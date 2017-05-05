package com.caihan.myscframe.api.retrofit2.observer;

import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by caihan on 2017/4/1.
 * 和Observable，Flowable一样会发送数据，不同的是订阅后只能接受到一次:
 */
public class MySingleObserver<T> implements SingleObserver<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }
}
