package com.caihan.myscframe.ui.welcome;

import android.support.constraint.ConstraintLayout;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;
import com.caihan.myscframe.utils.StartActivityUtils;
import com.caihan.scframe.rxjava.RxCountDown;
import com.caihan.scframe.utils.log.U1CityLog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WelcomeActivity extends BaseScActivity {

    @BindView(R.id.content_view)
    ConstraintLayout mContentView;


    private Disposable mDisposable;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionTransparent();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        U1CityLog.debug("onCreate");
        RxCountDown.countdown(3)
                .compose(this.<Boolean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        U1CityLog.debug("onSubscribe");
                    }

                    @Override
                    public void onNext(Boolean b) {
                        U1CityLog.debug("onNext");
                        startHomeActivity(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        U1CityLog.debug("onError");
                    }

                    @Override
                    public void onComplete() {
                        U1CityLog.debug("onComplete");
                    }
                });

    }

    @OnClick(R.id.content_view)
    public void onViewClicked() {
        U1CityLog.debug("onViewClicked");
        //切断下游,不再接收,调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
        mDisposable.dispose();
        startHomeActivity(false);
    }

    /**
     * 进入首页
     *
     * @param isFirstTime 是否显示引导页
     */
    private void startHomeActivity(boolean isFirstTime) {
        if (!isFirstTime) {
            StartActivityUtils.toMainActivity(mContext);
            overridePendingTransition(android.R.anim.fade_in, R.anim.activity_exit_to_larger_scale);
        } else {
            StartActivityUtils.toMainActivity(mContext);
        }
        finish();
    }
}
