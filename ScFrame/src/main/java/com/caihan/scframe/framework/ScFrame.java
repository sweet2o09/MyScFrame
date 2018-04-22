package com.caihan.scframe.framework;

import android.content.Context;
import android.content.res.Resources;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：ScFrame框架启动类
 * 备注：
 */
public final class ScFrame {
    private static final String TAG = "ScFrame";
    /**
     * 获取Application的Content以及屏幕宽,高
     */
    private static Context sContext;

    private ScFrame() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 在Application中初始化ScFrame框架工具类
     * @param context
     */
    public static void init(Context context) {
        ScFrame.sContext = context;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (sContext != null) return sContext;
        throw new NullPointerException("u should init first");
    }

    public static Resources getResources() {
        return ScFrame.getContext().getResources();
    }
}
