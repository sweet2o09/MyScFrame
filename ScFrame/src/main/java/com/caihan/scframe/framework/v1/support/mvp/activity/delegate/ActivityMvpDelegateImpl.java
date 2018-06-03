package com.caihan.scframe.framework.v1.support.mvp.activity.delegate;

import android.os.Bundle;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.framework.v1.support.mvp.MvpCallback;
import com.caihan.scframe.framework.v1.support.mvp.ProxyMvpCallback;


/**
 * 第二重代理,目标对象->ActivityMvpDelegateImpl
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate<V, P> {

    private MvpCallback<V, P> mvpCallback;
    private ProxyMvpCallback<V, P> proxyMvpCallback;

    public ActivityMvpDelegateImpl(MvpCallback<V, P> mvpCallback) {
        this.mvpCallback = mvpCallback;
    }

    private ProxyMvpCallback<V, P> getProxyMvpCallback() {
        if (this.proxyMvpCallback == null) {
            this.proxyMvpCallback = new ProxyMvpCallback<V, P>(this.mvpCallback);
        }
        return proxyMvpCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Activity->onCreate回调中绑定
        getProxyMvpCallback().createPresenter();
        getProxyMvpCallback().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getProxyMvpCallback().detachView();
    }
}
