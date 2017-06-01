package com.caihan.scframe.utils.flash;

import android.os.CountDownTimer;

import com.blankj.utilcode.util.LogUtils;


/**
 * Created by caihan on 2017/2/23.
 * 倒计时
 */
public class MyCountDownTimer extends CountDownTimer {
    private static final String TAG = "MyCountDownTimer";

    private int mFlashTime = 0;
    private boolean mIsStart = false;//是否已经开始倒计时

    /**
     * @param millisInFuture    表示以毫秒为单位 倒计时的总数
     *                          <p>
     *                          例如 millisInFuture=1000 表示1秒
     * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
     *                          <p>
     *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mFlashTime = (int) millisUntilFinished;
        LogUtils.e(TAG, "onTick flash_time = " + (mFlashTime / 1000) + "秒");
    }

    @Override
    public void onFinish() {
        mFlashTime = 0;
        LogUtils.e(TAG, "MyCountDownTimer onFinish");
    }

    public void timerStart(){
        mIsStart = true;
        this.start();
    }

    public void timerCancel(){
        mIsStart = false;
        this.cancel();
    }

    public boolean isStart() {
        return mIsStart;
    }

    public boolean isTimeOut(){
        return mFlashTime == 0 && mIsStart;
    }
}