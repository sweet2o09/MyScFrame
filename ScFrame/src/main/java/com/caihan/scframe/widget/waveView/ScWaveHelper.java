package com.caihan.scframe.widget.waveView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：水波纹效果帮助类
 * 备注：
 */
public class ScWaveHelper {

    private ScWaveView mWaveView;
    private AnimatorSet mAnimatorSet;
    private boolean isSettingFinish = false;
    private float mLevelRatioStart = 0.0f;
    private float mLevelRatioEnd = 0.5f;
    private long mLevelRatioTime = 10000;

    private float mLitudeStart = 0.0001f;
    private float mLitudeEnd = 0.05f;
    private long mLitudeTime = 5000;

    @ColorInt
    private int behindWaveColor = Color.parseColor("#28FFFFFF");
    @ColorInt
    private int frontWaveColor = Color.parseColor("#3CFFFFFF");

    public ScWaveHelper(ScWaveView waveView) {
        mWaveView = waveView;
    }

    public ScWaveHelper setLevelRatioStart(@FloatRange(from = 0f, to = 1f) float levelRatioStart) {
        mLevelRatioStart = levelRatioStart;
        return this;
    }

    public ScWaveHelper setLevelRatioEnd(@FloatRange(from = 0f, to = 1f) float levelRatioEnd) {
        mLevelRatioEnd = levelRatioEnd;
        return this;
    }

    public ScWaveHelper setLevelRatioTime(long levelRatioTime) {
        mLevelRatioTime = levelRatioTime;
        return this;
    }

    public ScWaveHelper setLitudeStart(@FloatRange(from = 0f, to = 0.05f) float litudeStart) {
        mLitudeStart = litudeStart;
        return this;
    }

    public ScWaveHelper setLitudeEnd(@FloatRange(from = 0f, to = 0.05f) float litudeEnd) {
        mLitudeEnd = litudeEnd;
        return this;
    }

    public ScWaveHelper setLitudeTime(long litudeTime) {
        mLitudeTime = litudeTime;
        return this;
    }

    public ScWaveHelper setBehindWaveColor(int behindWaveColor) {
        this.behindWaveColor = behindWaveColor;
        return this;
    }

    public ScWaveHelper setFrontWaveColor(int frontWaveColor) {
        this.frontWaveColor = frontWaveColor;
        return this;
    }

    /**
     * 初始化后要调用该方法才行
     */
    public void settingFinish() {
        initAnimation();
        setWaveColor(behindWaveColor, frontWaveColor);
        isSettingFinish = true;
    }

    public void start() {
        if (!isSettingFinish) return;
        mWaveView.setShowWave(true);
        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }
    }

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        //水平动画,0f-1f才能使得波纹看起来无限循环
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        //垂直动画,模仿加载进度,默认是从0f-0.5f,1f = 满
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", mLevelRatioStart, mLevelRatioEnd);
        waterLevelAnim.setDuration(mLevelRatioTime);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);


        //波浪振幅动画,振幅从小到大再到小,如果两个参数设置一致的话,视觉上比较死板
        //建议参数都小于0.05f不然振幅太大不美观
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", mLitudeStart, mLitudeEnd);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(mLitudeTime);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    public void stop() {
        if (!isSettingFinish) return;
        if (mAnimatorSet != null) {
//            mAnimatorSet.cancel();
            mAnimatorSet.end();
        }
    }

    /**
     * 设置水波纹布局的边框宽度与颜色
     *
     * @param width
     * @param color
     */
    public void setBorder(int width, @ColorInt int color) {
        mWaveView.setBorder(width, color);
    }

    /**
     * 设置水波纹的颜色
     *
     * @param behindWaveColor 后置水波纹的颜色
     * @param frontWaveColor  前置水波纹的颜色
     */
    public void setWaveColor(@ColorInt int behindWaveColor, @ColorInt int frontWaveColor) {
        mWaveView.setWaveColor(behindWaveColor, frontWaveColor);
    }
}
