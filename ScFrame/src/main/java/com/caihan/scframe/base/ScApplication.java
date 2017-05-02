package com.caihan.scframe.base;

import android.app.Activity;
import android.app.Application;

import com.caihan.scframe.ScFrame;
import com.caihan.scframe.utilcode.util.LogUtils;
import com.caihan.scframe.utilcode.util.SPUtils;
import com.caihan.scframe.utils.flash.FlashUtil;

/**
 * Created by caihan on 2017/4/17.
 * Application基类
 */
public abstract class ScApplication extends Application {
    private static final String TAG = "ScApplication";
    private static final String TEST_TAG = "XXX";

    private static ScApplication instance;
    protected static LogUtils.Builder lBuilder;
    protected static SPUtils sSPUtils;

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
     * 初始化SharedPreferences工具类
     *
     * @param SpTag
     */
    protected void initSpUtils(String SpTag) {
        if (sSPUtils == null) {
            sSPUtils = new SPUtils(SpTag);
        }
    }

    /**
     * 初始化Log工具类
     *
     * @param logSwitch
     * @param tag
     */
    protected void initLogUtils(boolean logSwitch, String tag) {
        lBuilder = new LogUtils.Builder()
                .setLogSwitch(logSwitch)// 设置log总开关，默认开
                .setGlobalTag(tag)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
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
