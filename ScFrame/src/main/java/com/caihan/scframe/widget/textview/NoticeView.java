package com.caihan.scframe.widget.textview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.caihan.scframe.R;

import java.util.List;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：公告轮播View
 * 备注：
 * 使用方式
 * noticeView.addNotice(notices);
 * noticeView.startFlipping();
 */
public class NoticeView extends ViewFlipper implements View.OnClickListener {

    private Context mContext;
    private List<String> mNotices;
    private OnNoticeClickListener mOnNoticeClickListener;
    private int mTextColor = Color.parseColor("#666666");

    /**
     * 通知点击监听接口
     */
    public interface OnNoticeClickListener {
        void onNotieClick(int position, String notice);
    }


    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void onClick(View v) {

    }

    private void init(Context context) {
        mContext = context;
        // 轮播间隔时间为3s
        setFlipInterval(3000);
        // 内边距5dp
        setPadding(dp2px(5f), dp2px(5f), dp2px(5f), dp2px(5f));
        // 设置enter和leave动画
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_view_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_view_out));
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                mContext.getResources().getDisplayMetrics());
    }

    /**
     * 设置通知点击监听器
     *
     * @param onNoticeClickListener 通知点击监听器
     */
    public NoticeView setOnNoticeClickListener(OnNoticeClickListener onNoticeClickListener) {
        mOnNoticeClickListener = onNoticeClickListener;
        return this;
    }

    public NoticeView setTextColor(@ColorInt int textColor) {
        mTextColor = textColor;
        return this;
    }

    /**
     * 添加需要轮播展示的公告
     *
     * @param notices
     */
    public void addNotice(List<String> notices) {
        mNotices = notices;
        removeAllViews();
        for (int i = 0; i < mNotices.size(); i++) {
            // 根据公告内容构建一个TextView
            String notice = notices.get(i);
            TextView textView = new TextView(mContext);
            textView.setSingleLine();
            textView.setText(notice);
            textView.setTextSize(13f);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(mTextColor);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            // 将公告的位置设置为textView的tag方便点击是回调给用户
            textView.setTag(i);
            textView.setOnClickListener(this);
            // 添加到ViewFlipper
            NoticeView.this.addView(textView,
                    new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }
}
