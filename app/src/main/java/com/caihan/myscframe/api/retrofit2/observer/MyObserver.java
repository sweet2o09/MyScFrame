package com.caihan.myscframe.api.retrofit2.observer;


import com.caihan.scframe.api.retrofit2.resmodel.HttpResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by caihan on 2017/4/1.
 * Function  观察者
 */

public class MyObserver<T> implements Observer<HttpResult<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResult<T> t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
