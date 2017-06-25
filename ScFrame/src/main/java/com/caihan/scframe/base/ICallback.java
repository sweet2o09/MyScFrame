package com.caihan.scframe.base;

import android.os.Bundle;

/**
 * Created by caihan on 2017/4/17.
 */

public interface ICallback {
    //返回布局文件id
    int getLayoutResId();
    //初始化Toolbar
    void initToolbar();
    //初始化布局文件
    void initView();
    //初始化数据
    void initData(Bundle savedInstanceState);
    //绑定监听事件
    void setListener();
}
