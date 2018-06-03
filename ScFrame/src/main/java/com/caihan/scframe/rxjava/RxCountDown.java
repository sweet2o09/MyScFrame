package com.caihan.scframe.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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

    public static Observable<Long> countdown(final long time) {

        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return time - aLong;
                    }
                }).take(time + 1);
    }

    public static Observable<Long> countdown2(final long time) {

        // 参数说明：
        // 参数1 = 起始位置；
        // 参数2 = 次数；
        // 参数3 = 等待多少时间开始；
        // 参数4 = 每隔多少时间发射一次
        // 参数5 = 时间单位
        // 参数6 = 线程调度器
        return Observable.intervalRange(0, time, 0,1,TimeUnit.SECONDS, Schedulers.io())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return time - aLong;
                    }
                }).take(time + 1);
    }
}
