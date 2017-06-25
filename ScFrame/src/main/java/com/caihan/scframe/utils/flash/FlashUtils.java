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
public class FlashUtils {
    private static final String TAG = "FlashUtil";

    private volatile static FlashUtils sInstance = null;

    private final static int COUNT_DOWN_INTERVAL = 1000;//读秒
    private static int sRETIME = 10 * 1000;//后台运行30秒后启动闪屏
    private Class mFlashAct = null;
    private ArrayList<String> mNoFlashActClassNames = null;
    private IFlashCallback mCallback = null;

    public static FlashUtils getInstance() {
        if (sInstance == null) {
            synchronized (FlashUtils.class) {
                if (sInstance == null) {
                    sInstance = new FlashUtils();
                }
            }
        }
        return sInstance;
    }

    private FlashUtils() {
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
    public FlashUtils addNoFlashActClassName(String... className) {
        if (mNoFlashActClassNames == null) {
            mNoFlashActClassNames = new ArrayList<>();
        }
        if (className.length > 0) {
            for (String s : Arrays.asList(className)) {
                if (!mNoFlashActClassNames.contains(s)) {
                    mNoFlashActClassNames.add(s);
                }
            }
        }
        return this;
    }

    /**
     * 添加闪屏界面,用于对当前最上层Act进行判断
     *
     * @param flashAct
     */
    public FlashUtils setFlashAct(Class flashAct) {
        mFlashAct = flashAct;
        return this;
    }

    /**
     * 在App里面设置后台运行事件,只设置一次就行,不能多次
     *
     * @param time
     */
    public void setFlashTime(int time) {
        sRETIME = time;
    }

    /**
     * 设置回调,界面自行处理事件
     *
     * @param callback
     */
    public void setFlashCallback(IFlashCallback callback) {
        mCallback = callback;
    }

    /**
     * 当界面onResume的时候调用,开启Home键监听
     */
    public void onResume(Activity activity) {
        if (mFlashAct != null) {
            if (isTimeOut()) {
                startFlash(activity);
            } else {
                timerCancel();
            }
            startWatch(activity);
        }
    }

    /**
     * 当界面onPause的时候调用,取消Home键监听
     */
    public void onPause(Activity activity) {
        if (mFlashAct != null) {
            stopWatch(activity);
        }
    }

    private void startWatch(Context context) {
        HomeWatcher.getInstance().startWatch(context);
        HomeWatcher.getInstance().setTime(sRETIME, COUNT_DOWN_INTERVAL);
    }

    private void stopWatch(Context context) {
        HomeWatcher.getInstance().stopWatch(context);
    }

    /**
     * 启动闪屏
     * 先判断当前页是否就是闪屏也,如果不是的话就启动闪屏页
     * 当mCallback != null 的时候,启动闪屏交由回调界面处理,处理后记得调用timerCancel()方法
     * @param activity
     */
    private void startFlash(Activity activity) {
        if (mFlashAct == null) {
            return;
        }
        if (mCallback != null) {
            mCallback.startFlash();
        } else {
            ActivityManager am = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            if (!mNoFlashActClassNames.contains(cn.getClassName()) &&
                    !cn.getClassName().contains(mFlashAct.getName())) {
                activity.startActivity(new Intent(activity, mFlashAct.getClass()));
                timerCancel();
            }
        }
    }

    private boolean isTimeOut() {
        return HomeWatcher.getInstance().isTimeOut();
    }

    public void timerCancel() {
        HomeWatcher.getInstance().timerCancel();
    }
}