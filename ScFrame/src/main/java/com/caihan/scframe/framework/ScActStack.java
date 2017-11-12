package com.caihan.scframe.framework;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by caihan on 2017/4/17.
 * Activity管理类：用于Activity管理和应用程序退出
 */
public class ScActStack {
    private static final String TAG = "ScActStack";
    private Stack<Activity> mActStack = null;
    private volatile static ScActStack sInstance = null;

    public static ScActStack getInstance() {
        if (sInstance == null) {
            synchronized (ScActStack.class) {
                if (sInstance == null) {
                    sInstance = new ScActStack();
                }
            }
        }
        return sInstance;
    }

    private ScActStack() {
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActStack == null) {
            mActStack = new Stack<>();
        }
        mActStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mActStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActStack.size(); i < size; i++) {
            if (null != mActStack.get(i)) {
                if (!mActStack.get(i).isFinishing()) {
                    mActStack.get(i).finish();
                }
            }
        }
        mActStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(
                    Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stack<Activity> getActStack() {
        return mActStack;
    }
}