package com.caihan.scframe.utils.statusbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;

import java.lang.reflect.Method;

/**
 * Created by caihan on 2017/1/1.
 * 状态栏,导航栏工具
 * initSystemBar,setColor,setTransparent方法不可以一起使用
 */
public class MyStatusBarUtil {
    private static final String TAG = "MyStatusBarUtil";

    private MyStatusBarUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param activity
     * @param mToolbar
     * @param initToolBar 如果布局文件里面没添加android:fitsSystemWindows="true"的话,传递true
     */
    public static void initSystemBar(Activity activity, View mToolbar, boolean initToolBar) {
        compat6(activity);
        if (initToolBar && mToolbar != null) {
            initToolBar(activity, mToolbar);
        }
    }

    /**
     * 如果布局文件里面没添加android:fitsSystemWindows="true"的话,就需要调用该方法
     * 该方法是让toolbar加上statubar高度,然后顶上去
     * 5.0以后的机器如果调用该方法,根布局不需要添加背景色,以toolbar的背景色为准
     * 4.4机器验证OK
     *
     * @param activity
     * @param toolbar
     */
    private static void initToolBar(Activity activity, View toolbar) {
        int mStatuBarHeight = BarUtils.getStatusBarHeight(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams param4 = toolbar.getLayoutParams();
            param4.height = param4.height + mStatuBarHeight;
            toolbar.setLayoutParams(param4);
            toolbar.setPadding(0, mStatuBarHeight, 0, 0);
        }
    }

    private static void compat6(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getNavigationBarHeight(activity) == 0) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 设置状态栏颜色,默认40%透明
     * 当背景是白色的时候,状态栏上的文字会看不清楚,用该方法在合适不过了
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, @ColorRes int color) {
        BarUtils.setColor(activity, activity.getResources().getColor(color));
    }

    /**
     * 设置状态栏全透明,底部导航栏navigationBar也会变成全透明
     * 用于启动页等全屏都是图片的界面
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        BarUtils.setTransparent(activity);
    }

    /**
     * 获取虚拟按键栏高度
     *
     * @param context
     * @return
     */
    private static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }
}
