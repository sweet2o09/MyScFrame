package com.caihan.scframe.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by caihan on 2017/4/17.
 * Activity管理类：用于Activity管理和应用程序退出
 */
public class ScActStack {
    private static final String TAG = "ScActStack";
    private static Stack<Activity> sActStack;
    private volatile static ScActStack sInstance = new ScActStack();

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
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return sActStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (sActStack == null) {
            sActStack = new Stack<>();
        }
        sActStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (sActStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend XActivity");
        }
        if (sActStack.isEmpty()) {
            return null;
        }
        Activity activity = sActStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : sActStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = sActStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            sActStack.remove(activity);
            // activity.finish();//此处不用finish
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : sActStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : sActStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = sActStack.size(); i < size; i++) {
            if (null != sActStack.get(i)) {
                (sActStack.get(i)).finish();
            }
        }
        sActStack.clear();
    }


    /**
     * 应用程序退出
     */
    public void appExit() {
        try {
            finishAllActivity();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}