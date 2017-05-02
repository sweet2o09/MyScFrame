package com.caihan.scframe;

import android.content.Context;
import android.content.res.Resources;

import com.caihan.scframe.utilcode.util.CrashUtils;
import com.caihan.scframe.utilcode.util.ScreenUtils;
import com.caihan.scframe.utilcode.util.ToastUtils;
import com.caihan.scframe.utilcode.util.Utils;

/**
 * Created by caihan on 2017/4/17.
 */

public final class ScFrame {
    private static final String TAG = "ScFrame";
    /**
     * 获取Application的Content以及屏幕宽,高
     */
    private static Context sContext;
    public static int sScreenHeight;
    public static int sScreenWidth;

    // #log
    public static boolean isDebug = true;

    private ScFrame() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 在Application中初始化ScFrame框架工具类
     * @param context
     */
    public static void init(Context context) {
        ScFrame.sContext = context;
        initUtilCode(context);
        sScreenHeight = ScreenUtils.getScreenHeight();
        sScreenWidth = ScreenUtils.getScreenWidth();
    }

    /**
     * 初始化Blankj的AndroidUtilCode
     *
     * @param context
     * @url https://github.com/Blankj/AndroidUtilCode
     * @versionName 1.3.7
     */
    private static void initUtilCode(Context context) {
        Utils.init(context);
        ToastUtils.init(false);
        CrashUtils.getInstance().init();
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
