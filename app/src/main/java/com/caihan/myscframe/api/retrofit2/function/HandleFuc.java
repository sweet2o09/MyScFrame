package com.caihan.myscframe.api.retrofit2.function;


import com.caihan.myscframe.api.retrofit2.resmodel.HttpResult;

import io.reactivex.functions.Function;

/**
 * Created by caihan on 2017/4/4.
 * 转换数据
 */

public class HandleFuc<T> implements Function<HttpResult<T>, T> {
    @Override
    public T apply(HttpResult<T> response) throws Exception {
        //response中code码不会0 出现错误
        if (!(response.getCode() == 1))
            throw new RuntimeException(response.getCode() + "");
        return response.getData();
    }
}
