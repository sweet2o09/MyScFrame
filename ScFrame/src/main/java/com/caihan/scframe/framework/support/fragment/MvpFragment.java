package com.caihan.scframe.framework.support.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.base.ScFragment;


/**
 * 作者：caihan
 * 创建时间：2017/11/22
 * 邮箱：93234929@qq.com
 * 说明：
 * MVP设计模式,Fragment基类
 * 实现V层,P层的绑定以及解绑
 */
public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends ScFragment
        implements FragmentMvpDelegateCallback<V, P>, MvpView {

    private P presenter;
    private FragmentMvpDelegate<V, P> activityMvpDelegate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentMvpDelegate().onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFragmentMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFragmentMvpDelegate().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentMvpDelegate().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentMvpDelegate().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getFragmentMvpDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentMvpDelegate().onDestroy();
    }

    private FragmentMvpDelegate getFragmentMvpDelegate() {
        if (this.activityMvpDelegate == null) {
            this.activityMvpDelegate = new FragmentMvpDelegateImpl<V, P>(this);
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

    @SuppressWarnings("unchecked")
    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    public boolean isRetainInstance() {
        return false;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return false;
    }

}
