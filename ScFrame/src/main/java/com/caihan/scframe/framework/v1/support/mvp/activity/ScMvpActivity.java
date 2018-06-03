package com.caihan.scframe.framework.v1.support.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.caihan.scframe.framework.v1.BaseActivity;
import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.framework.v1.support.mvp.activity.delegate.ActivityMvpDelegate;
import com.caihan.scframe.framework.v1.support.mvp.activity.delegate.ActivityMvpDelegateCallback;
import com.caihan.scframe.framework.v1.support.mvp.activity.delegate.ActivityMvpDelegateImpl;
import com.caihan.scframe.framework.v1.support.mvp.lce.activity.ScMvpLceActivity;

/**
 * MVP设计模式
 * <p>
 * 采用多重代理的形式管理V,P与Activity生命周期进行绑定,只是为了自我锻炼(zb),可以不要
 *
 * @author caihan
 * @date 2018/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class ScMvpActivity
        <V extends MvpView, P extends MvpBasePresenter<V>>
        extends BaseActivity
        implements MvpView, ActivityMvpDelegateCallback<V, P> {

    protected ActivityMvpDelegate mvpDelegate;
    /**
     * {@link #createPresenter()}
     */
    protected P presenter;
    //是否保存数据
    private boolean retainInstance;

    @Override
    @Deprecated
    protected void onCreate() {

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

    /**
     * 委托类
     *
     * @return
     */
    private ActivityMvpDelegate<V, P> getActivityMvpDelegate() {
        if (this.mvpDelegate == null) {
            this.mvpDelegate = new ActivityMvpDelegateImpl(this);
        }
        return this.mvpDelegate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityMvpDelegate().onCreate(savedInstanceState);
        onCreateMvp();
    }

    /**
     * 如果不是直接继承的话,请勿使用该方法
     * {@link ScMvpLceActivity#onContentChanged()}会在{@link BaseActivity#onCreate(Bundle)}后调用
     * 因此在ScMvpLceActivity中可以继续使用onCreateMvp()进行初始化
     */
    protected abstract void onCreateMvp();

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
