package com.caihan.scframe.framework.support.lce.animator;

import android.view.View;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：LCE设计->动画策略接口
 * 备注：
 */
public interface ILceAnimator {

    /**
     * 显示加载页
     * @param loadingView
     * @param contentView
     * @param errorView
     */
    void showLoading(View loadingView, View contentView, View errorView);

    /**
     * 显示内容页
     * @param loadingView
     * @param contentView
     * @param errorView
     */
    void showContent(View loadingView, View contentView, View errorView);

    /**
     * 显示错误页
     * @param loadingView
     * @param contentView
     * @param errorView
     */
    void showErrorView(View loadingView, View contentView, View errorView);

}
