package com.caihan.scframe.immersion.base;


import com.caihan.scframe.immersion.ScImmersionBar;

/**
 * 沉浸式效果UI接口
 *
 * @author caihan
 * @date 2018/1/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface OnImmersionListener {

    boolean openImmersion();

    /**
     * 初始化沉浸式效果
     */
    void initImmersion();

    /**
     * 获取沉浸式效果实例
     *
     * @return
     */
    ScImmersionBar getImmersion();

    /**
     * 设置沉浸式效果
     */
    void setImmersion();

    /**
     * 销毁沉浸式效果
     */
    void onDestroyImmersion();
}
