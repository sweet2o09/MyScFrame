package com.caihan.scframe.utils.flash;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：闪屏/解锁模块
 * 备注：
 * <p>
 * 在Application中初始化并传入需要跳转的Activity.class与不需要跳转的Activity.class.getName()
 * 当需要跳转的时候通过IFlashCallback返回给相应Activity进行处理
 * IFlashCallback会在Activity.onResume()的时候进行注册onPause()的时候进行销毁
 * <p>
 * HomeWatcher是Home键监听单例,在该类实例化的时候一并实例化,
 * 并且是在Activity.onResume()的时候注册监听,onPause()的时候进行销毁
 */
public class FlashLockSingleton {
    private static final String TAG = "FlashLock";

    private volatile static FlashLockSingleton sInstance = null;

    private final static int COUNT_DOWN_INTERVAL = 1000;//读秒
    private int mJumpTime = 10 * 1000;//后台运行10秒后启动闪屏

    private Context mAppContext;
    private Class mJumpToAct = null;//需要跳转到的指定界面class
    private ArrayList<String> mNoJumpActClassNames = null;//不需要跳转的界面list
    private IFlashCallback mFlashCallback;

    public static FlashLockSingleton getInstance() {
        if (sInstance == null) {
            synchronized (FlashLockSingleton.class) {
                if (sInstance == null) {
                    sInstance = new FlashLockSingleton();
                }
            }
        }
        return sInstance;
    }

    private FlashLockSingleton() {
    }

    /**
     * 在Application中初始化
     *
     * @param context
     * @param jumpClass
     * @param noJumpClasses
     */
    public void init(Context context, Class jumpClass, ArrayList<String> noJumpClasses) {
        mAppContext = context;
        mJumpToAct = jumpClass;
        mNoJumpActClassNames = noJumpClasses;
        initHomeWatcher();
    }

    /**
     * 在Application中初始化
     *
     * @param context
     * @param jumpClass
     * @param noJumpClasses
     * @param jumpTime
     */
    public void init(Context context, Class jumpClass,
                     ArrayList<String> noJumpClasses, int jumpTime) {
        mAppContext = context;
        mJumpToAct = jumpClass;
        mNoJumpActClassNames = noJumpClasses;
        mJumpTime = jumpTime;
        initHomeWatcher();
    }

    /**
     * 初始化Home键监听
     */
    private void initHomeWatcher() {
        if (mJumpToAct == null) return;
        HomeWatcher.getInstance().setTime(mJumpTime, COUNT_DOWN_INTERVAL);
    }

    /**
     * 当界面onResume的时候调用,开启Home键监听
     */
    public void onResume(IFlashCallback callback) {
        if (mJumpToAct == null) return;
        mFlashCallback = callback;
        if (mFlashCallback != null) {
            if (isTimeOut()) {
                startFlash();
            } else {
                timerCancel();
            }
            startWatch();
        }
    }

    /**
     * 当界面onPause的时候调用,取消Home键监听
     */
    public void onPause() {
        if (mJumpToAct == null) return;
        mFlashCallback = null;
        stopWatch();
    }

    public void onDestroy() {
    }

    /**
     * 手动检查是否需要跳转闪屏/解锁界面
     */
    public void manualStartActivity(IFlashCallback callback) {
        if (mJumpToAct == null) {
            return;
        }
        mFlashCallback = callback;
        startActivity();
    }

    /**
     * 启动闪屏
     * 先判断当前页是否就是闪屏也,如果不是的话就启动闪屏页
     * 当mCallback != null 的时候,启动闪屏交由回调界面处理,处理后记得调用timerCancel()方法
     */
    private void startFlash() {
        if (mJumpToAct == null) {
            return;
        }
        if (mFlashCallback == null) {
            LogUtils.d(TAG, "startFlash error mFlashCallback == null");
            return;
        }
        startActivity();
    }

    private void startActivity() {
        ActivityManager am = (ActivityManager) mAppContext.getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        //检查当前最顶端的页面是否在不需要跳转闪屏/解锁列表
        if (mNoJumpActClassNames.size() > 0) {
            for (String actString : mNoJumpActClassNames) {
                if (cn.getClassName().contains(actString)) {
                    return;
                }
            }
        }
        //如果最顶端的界面不是闪屏/解锁界面的话就跳转到闪屏/解锁界面
        if (!cn.getClassName().contains(mJumpToAct.getName())) {
            if (mFlashCallback != null) {
                mFlashCallback.startFlash(mJumpToAct);
            }
        }
        timerCancel();
    }

    /**
     * 开始监听Home键
     */
    private void startWatch() {
        HomeWatcher.getInstance().startWatch(mAppContext);
    }

    /**
     * 取消Home键监听
     */
    private void stopWatch() {
        HomeWatcher.getInstance().stopWatch(mAppContext);
    }

    /**
     * 检查是否已经可以启动
     *
     * @return
     */
    private boolean isTimeOut() {
        return HomeWatcher.getInstance().isTimeOut();
    }

    /**
     * 重置计时
     */
    public void timerCancel() {
        HomeWatcher.getInstance().timerCancel();
    }

}