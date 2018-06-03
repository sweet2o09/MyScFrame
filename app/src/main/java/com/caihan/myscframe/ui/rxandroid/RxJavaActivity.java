package com.caihan.myscframe.ui.rxandroid;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;
import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.utils.log.ScLog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * RxJava
 *
 * @author caihan
 * @date 2018/6/3
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RxJavaActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.interval_range_btn)
    Button mIntervalRangeBtn;
    @BindView(R.id.from_iterable_btn)
    Button mFromIterableBtn;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("RxJava");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.interval_range_btn, R.id.from_iterable_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.interval_range_btn:
                intervalRange();
                break;
            case R.id.from_iterable_btn:
                fromIterable();
                break;
        }
    }

    private void intervalRange() {
        //从2开始没隔1秒发送一个数据,总共发送10个
        //2,3,4,5,6,7,8,9,10,11
        Observable.intervalRange(2, 10, 0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.<Long>request(this))
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        ScLog.debug("intervalRange doOnDispose ");
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ScLog.debug("intervalRange doOnSubscribe ");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        ScLog.debug("intervalRange doOnComplete ");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long longer) throws Exception {
                        ScLog.debug("intervalRange longer = " + longer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ScLog.debug("intervalRange throwable = " + throwable.getMessage().toString());
                    }
                });
    }

    private void fromIterable() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("第一");
        list.add("第二");
        list.add("第三");
        list.add("第四");
        list.add("第五");
        list.add("第六");
        list.add("第七");
        list.add("第八");
        list.add("第九");
        list.add("第十");
        ScLog.debug("fromIterable list.size() = " + list.size());
        Observable.intervalRange(0, list.size(), 0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        ScLog.debug("fromIterable map aLong = " + aLong);
                        int persion = aLong.intValue();
                        return list.get(persion);
                    }
                })
                .compose(RxSchedulers.<String>request(this))
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        ScLog.debug("fromIterable doOnDispose ");
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ScLog.debug("fromIterable doOnSubscribe ");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        ScLog.debug("fromIterable doOnComplete ");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String stringer) throws Exception {
                        ScLog.debug("fromIterable stringer = " + stringer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ScLog.debug("fromIterable throwable = " + throwable.getMessage().toString());
                    }
                });
    }
}
