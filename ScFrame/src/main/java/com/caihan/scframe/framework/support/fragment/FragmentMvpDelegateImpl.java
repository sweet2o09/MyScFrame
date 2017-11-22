package com.caihan.scframe.framework.support.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.support.ProxyMvpCallback;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：第二重代理,目标对象->FragmentMvpDelegateImpl
 * 备注：
 * 特点：实现目标接口
 */
public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMvpDelegate<V, P> {

    // 具体的目标接口实现类，需要持有创建Mvp实例
    private FragmentMvpDelegateCallback<V, P> mvpCallback;
    private ProxyMvpCallback<V, P> proxy;

    public FragmentMvpDelegateImpl(FragmentMvpDelegateCallback<V, P> mvpCallback) {
        if (mvpCallback == null) {
            throw new NullPointerException("mvpCallback is not null!");
        }
        this.mvpCallback = mvpCallback;
    }

    private ProxyMvpCallback<V, P> getProxy() {
        if (this.proxy == null) {
            this.proxy = new ProxyMvpCallback<V, P>(mvpCallback);
        }
        return this.proxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getProxy().createPresenter();
        getProxy().attachView();
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
        getProxy().detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttach(Context context) {

    }

}
