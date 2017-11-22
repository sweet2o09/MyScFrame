package com.caihan.scframe.framework.support.fragment;

import android.content.Context;
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

    void onCreate(Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onAttach(Context context);

    void onDetach();

}
