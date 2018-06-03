package com.caihan.scframe.framework.v1.support.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.caihan.scframe.framework.v1.BaseFragment;
import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.framework.v1.support.mvp.fragment.delegate.FragmentMvpDelegate;
import com.caihan.scframe.framework.v1.support.mvp.fragment.delegate.FragmentMvpDelegateCallback;
import com.caihan.scframe.framework.v1.support.mvp.fragment.delegate.FragmentMvpDelegateImpl;

/**
 * MVP设计模式
 * <p>
 * 采用多重代理的形式管理V,P与Fragment生命周期进行绑定,只是为了装B,可以不要
 *
 * @author caihan
 * @date 2018/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class ScMvpFragment
        <V extends MvpView, P extends MvpPresenter<V>>
        extends BaseFragment
        implements MvpView, FragmentMvpDelegateCallback<V, P> {

    protected FragmentMvpDelegate<V, P> mvpDelegate;

    /**
     * {@link #createPresenter()}
     */
    protected P presenter;

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
        onViewCreatedMvp();
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

    @Override
    @Deprecated
    protected void onViewCreated() {

    }

    /**
     * 委托类
     *
     * @return
     */
    private FragmentMvpDelegate<V, P> getFragmentMvpDelegate() {
        if (this.mvpDelegate == null) {
            this.mvpDelegate = new FragmentMvpDelegateImpl(this);
        }
        return this.mvpDelegate;
    }


    /**
     * 初始化,如果不是直接继承的话请不要使用该方法
     */
    protected abstract void onViewCreatedMvp();


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
