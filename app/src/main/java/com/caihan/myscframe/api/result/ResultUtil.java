package com.caihan.myscframe.api.result;


import com.caihan.myscframe.api.ScApi;
import com.caihan.myscframe.api.retrofit2.databean.ResultData;
import com.caihan.myscframe.api.retrofit2.function.ErrorResFunc;
import com.caihan.myscframe.api.retrofit2.function.HandleFuc;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caihan on 2017/5/5.
 */
public class ResultUtil {

    public void getJoke(String url, Observer<ResultData> observer, HashMap<String, String> params) {
        Observable observable = ScApi.getInstance().getApiService().rxPost(url, params)
                .map(new HandleFuc<ResultData>())
                .onErrorResumeNext(new ErrorResFunc<ResultData>());
        toSubscribe(observable, observer);
    }

    /**
     * @param o   被观察者(网络请求)
     * @param s   观察者(数据接收)
     * @param <T> 观察目标(数据)
     */
    private <T> void toSubscribe(Observable<T> o, Observer<T> s) {
        /**
         * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
         * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
         * Schedulers.newThread() 代表一个常规的新线程
         * AndroidSchedulers.mainThread() 代表Android的主线程
         */
        o.subscribeOn(Schedulers.io())//设置事件触发在非主线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理返回数据
                .subscribe(s);
    }
}
