package com.caihan.scframe.framework.support.lce.activity;


import com.caihan.scframe.framework.base.MvpView;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：LCE设计->定义回调接口
 * 备注：
 */
public interface MvpLceView<D> extends MvpView {

    /**
     * 显示Loading页面
     * 一个页面分为两种类型
     * 一种：下拉刷新组件类型->自带加载页面
     * 一种：需要添加加载页面
     *
     * @param isPullRefresh 是否是下拉刷新组件
     */
    void showLoading(boolean isPullRefresh);

    /**
     * 显示Content页面
     * 一个页面分为两种类型
     * 一种：下拉刷新组件类型->自带加载页面
     * 一种：需要添加加载页面
     *
     * @param isPullRefresh 是否是下拉刷新组件
     */
    void showContent(boolean isPullRefresh);

    /**
     * 显示Error页面
     * 一个页面分为两种类型
     * 一种：下拉刷新组件类型->自带加载页面
     * 一种：需要添加加载页面
     *
     * @param isPullRefresh 是否是下拉刷新组件
     */
    void showError(boolean isPullRefresh);

    /**
     * 绑定数据
     *
     * @param data
     * @param isPullRefresh
     */
    void bindData(D data, boolean isPullRefresh);

    /**
     * 加载数据
     *
     * @param isPullRefresh
     */
    void loadData(boolean isPullRefresh);

}
