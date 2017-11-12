package com.caihan.scframe.framework.support.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.base.ScActivity;
import com.caihan.scframe.framework.support.MvpCallback;



/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 说明：集成MVP的Activity
 * 第一重代理->MvpActivity
 * 特点一：实现目标接口(可有可无)
 * 特点二：持有目标对象
 * <p>
 * 第二个代理->关联MVP,目标对象->MvpActivity
 * 特点:实现目标接口
 *
 * 一个界面对应一个MvpView,一个MvpPresenter,可以有多个Mode
 */
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends ScActivity
        implements MvpCallback<V, P>, MvpView {

    private P presenter;
    private ActivityMvpDelegate<V, P> activityMvpDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getActivityMvpDelegate().onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityMvpDelegate().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityMvpDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getActivityMvpDelegate().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getActivityMvpDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityMvpDelegate().onDestroy();
    }

    private ActivityMvpDelegate getActivityMvpDelegate() {
        if (this.activityMvpDelegate == null) {
            this.activityMvpDelegate = new ActivityMvpDelegateImpl<V, P>(this);
        }
        return this.activityMvpDelegate;
    }

    @Override
    public P createPresenter() {
        return presenter;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

}
