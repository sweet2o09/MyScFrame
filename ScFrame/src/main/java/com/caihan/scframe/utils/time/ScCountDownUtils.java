package com.caihan.scframe.utils.time;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：按钮倒计时效果控制类
 * 备注：
 */
public class ScCountDownUtils extends CountDownTimer {
    public static final int TIME_COUNT_FUTURE = 60000;
    public static final int TIME_COUNT_INTERVAL = 1000;

    private Context mContext;
    private TextView mButton;
    private String mOriginalText;
    private String mOnTickString = "秒后重发";

    // private Drawable mOriginalBackground;
    // private Drawable mTickBackground;
    // private int mOriginalTextColor;

    public ScCountDownUtils() {
        super(TIME_COUNT_FUTURE, TIME_COUNT_INTERVAL);
    }

    public ScCountDownUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void init(Context context, TextView button) {
        this.mContext = context;
        this.mButton = button;
        this.mOriginalText = mButton.getText().toString();
        // this.mOriginalBackground =
        // context.getResources().getDrawable(R.color.unChecked);
        // this.mTickBackground = this.mOriginalBackground;
        // this.mOriginalTextColor = mButton.getCurrentTextColor();
    }

    public void init(Context context, TextView button, String onTickString) {
        this.mContext = context;
        this.mButton = button;
        this.mOriginalText = mButton.getText().toString();
        this.mOnTickString = onTickString;
    }

    public void setTickDrawable(Drawable tickDrawable) {
        // this.mTickBackground = tickDrawable;
    }

    @Override
    public void onFinish() {
        if (mContext != null && mButton != null) {
            mButton.setText(mOriginalText);
            // mButton.setTextColor(mOriginalTextColor);
            // mButton.setBackgroundDrawable(mOriginalBackground);
            mButton.setClickable(true);
            mButton.setEnabled(true);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mContext != null && mButton != null) {
            mButton.setClickable(false);
            mButton.setEnabled(false);
            // mButton.setBackgroundDrawable(mTickBackground);
            // mButton.setTextColor(mContext.getResources().getColor(
            // android.R.color.white));
            mButton.setText(millisUntilFinished / 1000 + mOnTickString);
        }
    }

}