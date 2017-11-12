package com.caihan.scframe.framework.support;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;

/**
 * 作者：caihan
 * 创建时间：2017/10/24
 * 邮箱：93234929@qq.com
 * 实现功能：第二重代理->关联MVP,目标接口->MvpCallback(定义：MVP绑定和解绑操作)
 * 备注：
 */
public interface MvpCallback<V extends MvpView, P extends MvpPresenter<V>> {

    //创建P层
    P createPresenter();

    //得到P层
    P getPresenter();

    //设置P层
    void setPresenter(P presenter);

    //得到V层
    V getMvpView();

}
