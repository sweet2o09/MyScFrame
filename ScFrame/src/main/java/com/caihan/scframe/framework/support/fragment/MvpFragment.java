package com.caihan.scframe.framework.support.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.support.MvpCallback;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：MVP->Fragment
 * 备注：
 * 第一重代理->MvpFragment
 * 特点一：实现目标接口(可有可无)
 * 特点二：持有目标对象
 * <p>
 * 第二个代理->关联MVP,目标对象->MvpFragment
 * 特点:实现目标接口
 */
public class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends Fragment implements MvpCallback<V, P>, MvpView {

    private FragmentMvpDelegate<V, P> activityMvpDelegate;

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

    private P presenter;

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
