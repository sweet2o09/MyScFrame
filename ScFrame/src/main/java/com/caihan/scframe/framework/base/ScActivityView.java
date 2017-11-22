package com.caihan.scframe.framework.base;

import android.support.annotation.LayoutRes;

/**
 * 作者：caihan
 * 创建时间：2017/10/29
 * 邮箱：93234929@qq.com
 * 说明：
 * Activity接口
 */
public interface ScActivityView {

    /**
     * 返回布局文件id
     *
     * @return
     */
    @LayoutRes
    int setLayoutResId();

    /**
     * 获取Intent中的数据
     */
    void getIntentData();

    /**
     * 初始化Actionbar
     */
    void initActionBar();

    /**
     * 初始化view跟Model
     */
    void initView();

    /**
     * 绑定监听事件
     */
    void setListener();

    /**
     * 初始化数据,在Fragment中只有当首次展示的时候才会调用到
     */
    void initData();

    /**
     * 网络请求
     */
    void request();
}
