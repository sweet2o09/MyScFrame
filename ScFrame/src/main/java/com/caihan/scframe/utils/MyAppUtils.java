package com.caihan.scframe.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.blankj.utilcode.util.IntentUtils;


/**
 * Created by caihan on 2017/4/17.
 */

public class MyAppUtils {

    /**
     * 获取application中指定的meta-data
     *
     * @param ctx
     * @param key
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        //key = "UMENG_CHANNEL";
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager pm = ctx.getPackageManager();
            if (pm != null) {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(
                        ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 通过任务管理器杀死进程
     * 需添加权限 {@code <uses-permission android:name="android.permission.RESTART_PACKAGES"/>}</p>
     *
     * @param context
     */
    public static void restart(Context context) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
            System.exit(0);
        } else {// android2.1
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            am.restartPackage(context.getPackageName());
        }
    }

    /**
     * 获取App具体设置
     *
     * @param context     上下文
     * @param requestCode 返回码
     */
    public static void getAppDetailsSettings(Context context, int requestCode) {
        getAppDetailsSettings(context, context.getPackageName(), requestCode);
    }

    /**
     * 获取App具体设置
     *
     * @param context     上下文
     * @param packageName 包名
     * @param requestCode 返回码
     */
    public static void getAppDetailsSettings(Context context, String packageName, int requestCode) {
        if (isSpace(packageName)) return;
        ((AppCompatActivity) context).startActivityForResult(
                IntentUtils.getAppDetailsSettingsIntent(packageName), requestCode);
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
