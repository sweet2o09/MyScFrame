package com.caihan.scframe.widget.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * 仿京东解锁view
 */
public class JDLockView extends View implements ILockView {

    private Paint mPaint;
    private int mCurrentState = NO_FINGER;
    private float mOuterRadius;
    private float mInnerRadius;
    @ColorInt
    private int mNoFingerColor = Color.parseColor("#B9D4EF");
    @ColorInt
    private int mFingerColor = Color.parseColor("#5987FF");
    @ColorInt
    private int mFingerUpColor = Color.parseColor("#FF560A");
    @ColorInt
    private int mOuterRadiusFingerColor = Color.parseColor("#EEF3FF");
    @ColorInt
    private int mOuterRadiusFingerUpColor = Color.parseColor("#FFEEE6");

    public JDLockView(Context context) {
        this(context, null);
    }

    public JDLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        width = width > height ? height : width;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float space = 10;
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        canvas.translate(x, y);
        mOuterRadius = x - space;
        mInnerRadius = (x - space) / 3;
        switch (mCurrentState) {
            case NO_FINGER:
                drawNoFinger(canvas);
                break;
            case FINGER_TOUCH:
                drawFingerTouch(canvas);
                break;
            case FINGER_UP_MATCHED:
                drawFingerUpMatched(canvas);
                break;
            case FINGER_UP_UN_MATCHED:
                drawFingerUpUnmatched(canvas);
                break;
        }
    }

    /**
     * 画无手指触摸状态
     *
     * @param canvas
     */
    private void drawNoFinger(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mNoFingerColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }

    /**
     * 画手指触摸状态
     *
     * @param canvas
     */
    private void drawFingerTouch(Canvas canvas) {
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mOuterRadiusFingerColor);
        canvas.drawCircle(0, 0, mOuterRadius, mPaint);
        mPaint.setColor(mFingerColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }

    /**
     * 画手指抬起，匹配状态
     *
     * @param canvas
     */
    private void drawFingerUpMatched(Canvas canvas) {
        drawFingerTouch(canvas);
    }

    /**
     * 画手指抬起，不匹配状态
     *
     * @param canvas
     */
    private void drawFingerUpUnmatched(Canvas canvas) {
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mOuterRadiusFingerUpColor);
        canvas.drawCircle(0, 0, mOuterRadius, mPaint);
        mPaint.setColor(mFingerUpColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public View newInstance(Context context) {
        return new JDLockView(context);
    }

    @Override
    public void onNoFinger() {
        mCurrentState = NO_FINGER;
        postInvalidate();
    }

    @Override
    public void onFingerTouch() {
        mCurrentState = FINGER_TOUCH;
        postInvalidate();
    }

    @Override
    public void onFingerUpMatched() {
        mCurrentState = FINGER_UP_MATCHED;
        postInvalidate();
    }

    @Override
    public void onFingerUpUnmatched() {
        mCurrentState = FINGER_UP_UN_MATCHED;
        postInvalidate();
    }
}
