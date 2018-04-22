package com.caihan.scframe.framework.v1.support.mvp.lce.animator;

import android.view.View;

/**
 * LCE设计->动画策略接口
 *
 * @author caihan
 * @date 2018/1/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface ILceAnimator {

    /**
     * 显示加载页
     *
     * @param loadingLayout
     * @param contentLayout
     * @param errorLayout
     */
    void showLoadingLayout(View loadingLayout, View contentLayout, View errorLayout);

    /**
     * 显示内容页
     *
     * @param loadingLayout
     * @param contentLayout
     * @param errorLayout
     */
    void showContentLayout(View loadingLayout, View contentLayout, View errorLayout);

    /**
     * 显示错误页
     *
     * @param loadingLayout
     * @param contentLayout
     * @param errorLayout
     */
    void showErrorLayout(View loadingLayout, View contentLayout, View errorLayout);

}
