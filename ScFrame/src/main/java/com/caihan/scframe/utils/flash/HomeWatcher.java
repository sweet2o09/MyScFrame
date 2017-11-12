package com.caihan.scframe.utils.flash;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：Home键监听
 * 备注：
 */
public class HomeWatcher {
    private static final String TAG = "FlashLock_HomeWatcher";

    private volatile static HomeWatcher sHomeWatcher = null;

    private IntentFilter mFilter = null;
    private OnHomePressedListener mListener = null;
    private InnerRecevier mRecevier = null;//Home键监听
    private boolean isRegisterReceiver = false;//是否已经注册过
    private MyCountDownTimer mCountDownTimer;//倒计时功能

    public static HomeWatcher getInstance() {
        if (sHomeWatcher == null) {
            synchronized (HomeWatcher.class) {
                if (sHomeWatcher == null) {
                    sHomeWatcher = new HomeWatcher();
                }
            }
        }
        return sHomeWatcher;
    }

    public HomeWatcher() {
        isRegisterReceiver = false;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        setOnHomePressedListener();
    }

    public void setTime(long millisInFuture, long countDownInterval) {
        if (mCountDownTimer == null) {
            mCountDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
        }
    }

    public boolean isTimeOut() {
        if (mCountDownTimer != null) {
            return mCountDownTimer.isTimeOut();
        }
        return false;
    }

    public void timerCancel(){
        if (mCountDownTimer != null) {
            mCountDownTimer.timerCancel();
        }
    }

    /**
     * 设置监听
     */
    public void setOnHomePressedListener() {
        mListener = new OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                mCountDownTimer.timerStart();
            }

            @Override
            public void onHomeLongPressed() {
            }

            @Override
            public void onLockPressed() {
            }
        };
        mRecevier = new InnerRecevier();
    }

    /**
     * 设置监听
     */
    public void setOnHomePressedListener(OnHomePressedListener listener) {
        mListener = listener;
        mRecevier = new InnerRecevier();
    }

    /**
     * 开始监听，注册广播
     */
    public void startWatch(Context context) {
        if (!isRegisterReceiver && mRecevier != null) {
            context.registerReceiver(mRecevier, mFilter);
            isRegisterReceiver = true;
        }
    }

    /**
     * 停止监听，注销广播
     */
    public void stopWatch(Context context) {
        if (mRecevier != null && isRegisterReceiver) {
            context.unregisterReceiver(mRecevier);
            isRegisterReceiver = false;
        }
    }

    /**
     * 广播接收者
     */
    public class InnerRecevier extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        final String SYSTEM_DIALOG_REASON_LOCK_KEY = "lock";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_LOCK_KEY)) {
                        mListener.onLockPressed();
                    }
                    if (mListener != null) {
                        if (!mCountDownTimer.isStart()) {
                            if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                                // 短按home键
                                mListener.onHomePressed();
                            }
                        } else if (reason
                                .equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            // 长按home键
                            mListener.onHomeLongPressed();
                        }
                    }
                }
            }
        }
    }

}
