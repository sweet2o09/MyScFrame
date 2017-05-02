package com.caihan.scframe.utils.flash;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by caihan on 2017/4/17.
 * 闪屏工具类
 */
public class FlashUtil {
    private static final String TAG = "FlashUtil";

    private volatile static FlashUtil sInstance = new FlashUtil();

    private final static int COUNT_DOWN_INTERVAL = 1000;//读秒
    private final static int RE_TIME = 10 * 1000;//后台运行30秒后启动闪屏
    private int mFlashTime = RE_TIME;
    private Activity mFlashAct;
    private ArrayList<String> mNoFlashActClassNames;

    public static FlashUtil getInstance() {
        if (sInstance == null) {
            synchronized (FlashUtil.class) {
                if (sInstance == null) {
                    sInstance = new FlashUtil();
                }
            }
        }
        return sInstance;
    }

    private FlashUtil() {
        if (mNoFlashActClassNames == null) {
            mNoFlashActClassNames = new ArrayList<>();
        }
        mNoFlashActClassNames.clear();
    }

    /**
     * 添加不需要闪屏的界面,用于对当前最上层Act进行判断
     *
     * @param className
     */
    public FlashUtil addNoFlashActClassName(String... className) {
        if (mNoFlashActClassNames == null) {
            mNoFlashActClassNames = new ArrayList<>();
        }
        if (className.length > 0) {
            mNoFlashActClassNames.addAll(Arrays.asList(className));
        }
        return this;
    }

    /**
     * 添加闪屏界面,用于对当前最上层Act进行判断
     *
     * @param flashAct
     */
    public FlashUtil setFlashAct(Activity flashAct) {
        mFlashAct = flashAct;
        return this;
    }

    /**
     * 当界面onResume的时候调用,开启Home键监听
     */
    public void onResume(Activity activity) {
        if (isTimeOut()) {
            startFlash(activity);
        } else {
            timerCancel();
        }
        startWatch(activity);
    }

    /**
     * 当界面onPause的时候调用,取消Home键监听
     */
    public void onPause(Activity activity) {
        stopWatch(activity);
    }

    private void startWatch(Context context) {
        HomeWatcher.getInstance().startWatch(context);
        HomeWatcher.getInstance().setTime(RE_TIME, COUNT_DOWN_INTERVAL);
    }

    private void stopWatch(Context context) {
        HomeWatcher.getInstance().stopWatch(context);
    }

    /**
     * 启动闪屏
     * 先判断当前页是否就是闪屏也,如果不是的话就启动闪屏页
     *
     * @param activity
     */
    private void startFlash(Activity activity) {
        if (mFlashAct == null) {
            return;
        }
        ActivityManager am = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        if (!mNoFlashActClassNames.contains(cn.getClassName()) &&
                !cn.getClassName().contains(mFlashAct.getLocalClassName())) {
            activity.startActivity(new Intent(activity, mFlashAct.getClass()));
            timerCancel();
        }
    }

    private boolean isTimeOut() {
        return HomeWatcher.getInstance().isTimeOut();
    }

    private void timerCancel() {
        HomeWatcher.getInstance().timerCancel();
    }
}