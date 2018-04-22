package com.caihan.scframe.framework.v1.support.mvp.activity.delegate;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.framework.v1.support.mvp.MvpCallback;

/**
 * 扩展目标接口 针对不同的模块，目标接口有差异
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpCallback<V, P> {

}
