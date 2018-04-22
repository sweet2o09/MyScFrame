package com.caihan.scframe.framework.v1.support.mvp;


import android.support.annotation.NonNull;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;

/**
 * 代理模式－静态代理：目标接口
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface MvpCallback<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * 创建Presenter方法
     *
     * @return
     */
    @NonNull
    P createPresenter();

    /**
     * 得到Presenter实例
     *
     * @return
     */
    P getPresenter();

    /**
     * 设置一个新的Presenter
     *
     * @param presenter
     */
    void setPresenter(P presenter);

    /**
     * 得到具体的MvpView实例对象
     *
     * @return
     */
    V getMvpView();

    /**
     * 是否保存数据
     *
     * @param retaionInstance
     */
    void setRetainInstance(boolean retaionInstance);

    /**
     * 获取保存数据状态
     *
     * @return
     */
    boolean isRetainInstance();

    /**
     * 判断是否保存数据(该方法还会处理横竖屏切换)
     *
     * @return
     */
    boolean shouldInstanceBeRetained();
}
