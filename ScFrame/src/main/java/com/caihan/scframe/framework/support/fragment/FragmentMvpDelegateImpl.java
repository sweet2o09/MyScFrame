package com.caihan.scframe.framework.support.fragment;

import android.os.Bundle;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.support.MvpCallback;
import com.caihan.scframe.framework.support.ProxyMvpCallback;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：第二重代理,目标对象->FragmentMvpDelegateImpl
 * 备注：
 * 特点：实现目标接口
 */
public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements FragmentMvpDelegate<V, P> {

    private MvpCallback<V, P> mvpCallback;
    private ProxyMvpCallback<V, P> proxyMvpCallback;

    public FragmentMvpDelegateImpl(MvpCallback<V, P> mvpCallback) {
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
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        getProxyMvpCallback().detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }
}
