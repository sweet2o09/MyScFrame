package com.caihan.scframe.framework.support.fragment;

import android.os.Bundle;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：第一重代理,目标接口->ActivityMvpDelegate
 * 备注：
 */
public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    public void onCreate(Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState);

    public void onViewCreated(View view, Bundle savedInstanceState);

    public void onStart();

    public void onPause();

    public void onResume();

    public void onStop();

    public void onDestroyView();

    public void onDestroy();

    public void onDetach();

}
