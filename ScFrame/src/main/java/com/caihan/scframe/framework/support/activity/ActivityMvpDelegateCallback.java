package com.caihan.scframe.framework.support.activity;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.support.MvpCallback;

/**
 * 作者：caihan
 * 创建时间：2017/11/22
 * 邮箱：93234929@qq.com
 * 说明：
 * 扩展目标接口 针对不同的模块，目标接口有差异
 */
public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpCallback<V, P> {

}
