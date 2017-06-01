package com.caihan.scframe.base;

import android.app.Activity;
import android.app.Application;

import com.caihan.scframe.ScFrame;
import com.caihan.scframe.utils.flash.FlashUtil;

/**
 * Created by caihan on 2017/4/17.
 * Application基类
 */
public abstract class ScApplication extends Application {
    private static final String TAG = "ScApplication";
    private static final String TEST_TAG = "XXX";

    private static ScApplication instance;

    /**
     * 默认初始化ScFrame框架工具类
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ScFrame.init(this);
    }

    /**
     * 初始化闪屏工具类
     *
     * @param flashAct         闪屏Activity
     * @param noFlashClassName 不需要闪屏的ActivityClassName
     */
    protected void initFlashUtil(Activity flashAct, String... noFlashClassName) {
        FlashUtil.getInstance()
                .addNoFlashActClassName(noFlashClassName)
                .setFlashAct(flashAct);
    }
}
