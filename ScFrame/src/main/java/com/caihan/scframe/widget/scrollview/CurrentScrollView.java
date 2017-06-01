package com.caihan.scframe.widget.scrollview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.blankj.utilcode.util.SizeUtils;
import com.caihan.scframe.utils.motionevent.GestureUtils;


/**
 * Created by caihan on 2017/2/7.
 */
public class CurrentScrollView extends ScrollView {
    private static final String TAG = "CurrentScrollView";
    private static int mDistance = 0;
    private float firstTouchY = 0;
    private float lastTouchY = 0;
    //ScrollView的子View， 也是ScrollView的唯一一个子View
    private View contentView;
    private boolean isBottom = false;

    private GestureUtils mGestureUtils;

    public CurrentScrollView(Context context) {
        super(context);
        init(context);
    }

    public CurrentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CurrentScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mDistance = SizeUtils.dp2px(1);
        mGestureUtils = new GestureUtils();
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (contentView == null) {
            return super.dispatchTouchEvent(event);
        }
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                firstTouchY = event.getRawY();
                mGestureUtils.actionDown(event);
                isBottom = isBottom();
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mGestureUtils.actionMove(event);
                if (isBottom && mGestureUtils.getGesture(GestureUtils.Gesture.PullUp) &&
                        mGestureUtils.getyDistance() > mDistance) {
                    /**
                     * 这句话是告诉父view，我的事件是否自己处理
                     * 如果需要父类处理的话传递false
                     */
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    //dispatchTouchEvent —— true不分发，false 是分发（默认）
                    return true;
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isBottom() {
        return contentView.getHeight() <= getHeight() + getScrollY();
    }

}
