package com.caihan.scframe.framework.v1.support;


import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

/**
 * MVP->P接口,高度抽象P层
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * 绑定UI层
     * @param view
     */
    @UiThread
    void attachView(@NonNull V view);

    /**
     * 解绑UI层
     */
    @UiThread
    void detachView();

    /**
     * 销毁Model层
     */
    @UiThread
    void destroy();

}
