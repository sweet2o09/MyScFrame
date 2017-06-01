package com.caihan.scframe.update;

import android.content.Context;

import com.caihan.scframe.update.builder.ScUpdateBuilder;

/**
 * Created by caihan on 2017/5/22.
 * 版本更新管理器
 */
public class ScUpdateManager {

    //检测版本地址
    private static String sCheckUrl;
    //渠道号
    private static String sChannel;
    // 非wifi网络不检查更新
    private static boolean sIsWifiOnly = true;

    public static void setWifiOnly(boolean wifiOnly) {
        sIsWifiOnly = wifiOnly;
    }

    public static void setCheckUrl(String checkUrl, String channel) {
        sCheckUrl = checkUrl;
        sChannel = channel;
    }

    public static ScUpdateBuilder create(Context context) {
        ScUpdateUtil.ensureExternalCacheDir(context);
        return new ScUpdateBuilder(context).setWifiOnly(sIsWifiOnly);
    }
}
