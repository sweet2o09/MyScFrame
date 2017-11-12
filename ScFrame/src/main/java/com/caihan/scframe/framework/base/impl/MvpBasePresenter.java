package com.caihan.scframe.framework.base.impl;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;


/**
 * 作者：caihan
 * 创建时间：2017/10/24
 * 邮箱：93234929@qq.com
 * 实现功能：
 * 备注：MVP->P层,是个类,需要实例化
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    public V getView() {
        return view;
    }

    //绑定
    public void attachView(V view){
        this.view = view;
    }

    //解绑
    public void detachView(){
        this.view = null;
    }

}
