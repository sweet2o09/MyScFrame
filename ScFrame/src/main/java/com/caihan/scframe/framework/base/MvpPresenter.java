package com.caihan.scframe.framework.base;

/**
 * 作者：caihan
 * 创建时间：2017/10/24
 * 邮箱：93234929@qq.com
 * 实现功能：MVP->P接口,高度抽象P层
 * 备注：
 */

public interface MvpPresenter<V extends MvpView> {


    /**
     * 绑定UI层
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑UI层
     */
    void detachView();

    /**
     * 销毁Model层
     */
    void detachModel();
}
