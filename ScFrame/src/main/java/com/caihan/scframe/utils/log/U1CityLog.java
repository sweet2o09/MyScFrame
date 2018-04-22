package com.caihan.scframe.utils.log;

import android.os.Build;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.caihan.scframe.framework.U1CityApplication;

/**
 * Log工具管理类<br/>
 * xTag(String,Object...)方法是带自定义TAG的log,Object里面可以是各种类型,会自动判断
 * @author caihan
 * @date 2018/1/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class U1CityLog {

    private static final String DEBUG_TAG = "DebugLog";

    private U1CityLog() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static void v(final Object... contents) {
        LogUtils.v(contents);
    }

    public static void vTag(final String tag, final Object... contents) {
        LogUtils.v(tag, contents);
    }

    public static void d(final Object... contents) {
        LogUtils.d(contents);
    }

    public static void dTag(final String tag, final Object... contents) {
        LogUtils.dTag(tag, contents);
    }

    public static void i(final Object... contents) {
        LogUtils.i(contents);
    }

    public static void iTag(final String tag, final Object... contents) {
        LogUtils.i(tag, contents);
    }

    public static void w(final Object... contents) {
        LogUtils.w(contents);
    }

    public static void wTag(final String tag, final Object... contents) {
        LogUtils.w(tag, contents);
    }

    public static void e(final Object... contents) {
        LogUtils.e(contents);
    }

    public static void eTag(final String tag, final Object... contents) {
        LogUtils.e(tag, contents);
    }

    public static void a(final Object... contents) {
        LogUtils.a(contents);
    }

    public static void aTag(final String tag, final Object... contents) {
        LogUtils.a(tag, contents);
    }

    /**
     * 简单log,默认TAG = DebugLog
     * @param msg
     */
    public static void debug(Object msg) {
        debug(DEBUG_TAG, msg.toString());
    }

    /**
     * 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
     * 所以这里使用自己分节的方式来输出足够长度的message
     *
     * @param tag
     * @param msg
     */
    public static void debug(String tag, Object msg) {
        String str = msg.toString().trim();
        final int maxLen = 4000;
        for (int i = 0, len = str.length(); i * maxLen < len; ++i) {
            String subMsg = str.substring(i * maxLen, (i + 1) * maxLen < len ? (i + 1) * maxLen : len);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.d(tag, subMsg);
            } else {
                if (U1CityApplication.sDebug) {
                    Log.i(tag, subMsg);
                } else {
                    Log.d(tag, subMsg);
                }
            }
        }
    }
}
