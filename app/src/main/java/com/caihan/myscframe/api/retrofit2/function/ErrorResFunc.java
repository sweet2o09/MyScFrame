package com.caihan.myscframe.api.retrofit2.function;


import com.caihan.myscframe.api.retrofit2.exception.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by caihan on 2017/4/4.
 */

public class ErrorResFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
