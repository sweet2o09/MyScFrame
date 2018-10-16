package com.caihan.scframe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * meta-data标签工具类
 *
 * @author caihan
 * @date 2018/6/26
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MetaDataUtils {

    /**
     * 获取value
     * <meta-data android:name="meta_app" android:value="value from meta_app" />
     *
     * @param context       上下文
     * @param metaStringKey meta_app
     * @return value from meta_app
     */
    public static String getMetaDataFromApp(Context context, String metaStringKey) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(metaStringKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取resource id
     * <p>
     * <meta-data android:name="meta_act" android:resource="@string/app_name" />
     *
     * @param context       上下文
     * @param metaStringKey meta_act
     * @return
     */
    public static int getMetaDataIdFromAct(Context context, String metaStringKey) {
        int resId = 0;
        try {
            ActivityInfo activityInfo = context.getPackageManager()
                    .getActivityInfo(((Activity) context).getComponentName(),
                            PackageManager.GET_META_DATA);
            resId = activityInfo.metaData.getInt(metaStringKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resId;
    }
}
