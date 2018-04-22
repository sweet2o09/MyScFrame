package com.caihan.scframe.framework.v1.support.mvp.fragment.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;


/**
 * 第一重代理,目标接口->ActivityMvpDelegate
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
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
