package com.caihan.scframe.rxjava;

import com.caihan.scframe.utils.log.U1CityLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx倒计时功能
 *
 * @author caihan
 * @date 2018/4/22
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RxCountDown {
    public static Observable<Boolean> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())//在异步线程里执行subscribe操作,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//在主线程中处理结果,可以设置多次
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(Long aLong) throws Exception {
                        U1CityLog.debug("map Function apply = " + aLong.intValue());
                        return aLong.intValue() >= countTime;
                    }
                })
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(Boolean b) throws Exception {
                        U1CityLog.debug("filter Predicate test = " + b);
                        return b;
                    }
                });
    }
}
