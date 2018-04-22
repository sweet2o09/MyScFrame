package com.caihan.scframe.framework.v1.support.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * BaseActivity与BaseFragment中的网络请求loading与吐司接口
 * @author caihan
 * @date 2018/2/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface BaseView {

    /**
     * 展示网络请求Loading
     */
    void showRequestLoading();

    /**
     * 关闭网络请求Loading
     */
    void dismissRequestLoading();

    /**
     * 网络请求异常
     *
     * @param error
     */
    void onRequestError(String error);

    /**
     * 弹出吐司
     *
     * @param msg
     */
    void showToast(@NonNull String msg);

    /**
     * 弹出吐司
     *
     * @param resId
     */
    void showToast(@StringRes final int resId);
}
