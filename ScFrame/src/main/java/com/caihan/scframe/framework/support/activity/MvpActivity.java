package com.caihan.scframe.framework.support.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.base.ScActivity;




/**
 * 作者：caihan
 * 创建时间：2017/11/22
 * 邮箱：93234929@qq.com
 * 说明：
 * MVP设计模式,Activity基类
 * MvpActivity是ActivityMvpDelegateCallback具体的实现类
 * 一个界面对应一个MvpView,一个MvpPresenter,可以有多个Mode
 */
@SuppressLint("NewApi")
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends ScActivity
        implements ActivityMvpDelegateCallback<V, P>, MvpView {

    private P presenter;
    private ActivityMvpDelegate<V, P> activityMvpDelegate;
    //是否保存数据
    private boolean retainInstance;

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

    @Override
    public boolean isRetainInstance() {
        return retainInstance;
    }

    @Override
    public void setRetainInstance(boolean retaionInstance) {
        this.retainInstance = retaionInstance;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        //说明Activity出现了异常情况才缓存数据
        return this.retainInstance && isChangingConfigurations();
    }
}
