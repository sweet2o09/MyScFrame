package com.caihan.scframe;

import android.content.Context;
import android.content.res.Resources;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;


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

    // #log
    private static boolean isDebug = true;

    private static LogUtils.Builder lBuilder;
    private static SPUtils sSPUtils;

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
        initSpUtils(TAG);
        sScreenHeight = ScreenUtils.getScreenHeight();
        sScreenWidth = ScreenUtils.getScreenWidth();
    }

    /**
     * 初始化Blankj的AndroidUtilCode
     *
     * @param context
     * @url https://github.com/Blankj/AndroidUtilCode
     * @versionName 1.5.0
     */
    private static void initUtilCode(Context context) {
        Utils.init(context);
        CrashUtils.getInstance().init();
    }

    /**
     * 初始化SharedPreferences工具类
     *
     * @param SpTag
     */
    private static void initSpUtils(String SpTag) {
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
    private static void initLogUtils(boolean logSwitch, String tag) {
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
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (sContext != null) return sContext;
        throw new NullPointerException("u should init first");
    }

    public static SPUtils getSPUtils() {
        return sSPUtils;
    }

    public static Resources getResources() {
        return ScFrame.getContext().getResources();
    }
}
