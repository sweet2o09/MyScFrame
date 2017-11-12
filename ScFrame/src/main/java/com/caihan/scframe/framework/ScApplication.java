package com.caihan.scframe.framework;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.caihan.scframe.ScFrame;
import com.caihan.scframe.utils.flash.FlashLockSingleton;

import java.util.ArrayList;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：Application基类
 * 备注：
 */
public abstract class ScApplication extends Application {
    private static final String TAG = "ScApplication";
    private static final String TEST_TAG = "XXX";

    private static ScApplication instance;
    private static Context sContext;
    // #log
    public static boolean isDebug = false;

    private static SPUtils sSPUtils;

    /**
     * 默认初始化ScFrame框架工具类
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sContext = this;
        ScFrame.init(this);
        initUtilCode(instance);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ScActStack.getInstance().appExit(this);
    }

    /**
     * 初始化Blankj的AndroidUtilCode
     *
     * @param context
     * @url https://github.com/Blankj/AndroidUtilCode
     * @versionName 1.9.3
     */
    private void initUtilCode(Application context) {
        Utils.init(context);
        CrashUtils.init();
    }


    /**
     * 初始化SharedPreferences工具类
     *
     * @param SpTag
     */
    protected void initSpUtils(String SpTag) {
        if (sSPUtils == null) {
            sSPUtils = SPUtils.getInstance(SpTag);
        }
    }


    /**
     * 初始化闪屏工具类
     *
     * @param context
     * @param jumpClass     闪屏Activity
     * @param noJumpClasses 不需要闪屏的ActivityClassName
     * @param jumpTime      多长时间跳转
     */
    protected void initFlashUtil(Context context, Class jumpClass,
                                 ArrayList<String> noJumpClasses, int jumpTime) {
        FlashLockSingleton.getInstance().init(context, jumpClass, noJumpClasses, jumpTime);
    }

    /**
     * 初始化Log工具类
     *
     * @param logSwitch
     * @param tag
     */
    protected void initLogUtils(boolean logSwitch, String tag) {
        deBug(logSwitch);
        LogUtils.getConfig()
                .setLogSwitch(logSwitch)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(true)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(tag)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix(tag)// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1);// log栈深度，默认为1
    }

    protected void deBug(boolean isDebug) {
        this.isDebug = isDebug;
    }
}
