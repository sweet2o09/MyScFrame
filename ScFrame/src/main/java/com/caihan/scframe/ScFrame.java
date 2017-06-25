package com.caihan.scframe;

import android.content.Context;
import android.content.res.Resources;


/**
 * Created by caihan on 2017/4/17.
 */

public final class ScFrame {
    private static final String TAG = "ScFrame";
    /**
     * 获取Application的Content以及屏幕宽,高
     */
    private static Context sContext;
    private static int sScreenHeight;
    private static int sScreenWidth;

    private ScFrame() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 在Application中初始化ScFrame框架工具类
     * @param context
     */
    public static void init(Context context) {
        ScFrame.sContext = context;
//        sScreenHeight = ScreenUtils.getScreenHeight();
//        sScreenWidth = ScreenUtils.getScreenWidth();
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
