package com.caihan.scframe.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者：caihan
 * 创建时间：2017/9/25
 * 邮箱：93234929@qq.com
 * 实现功能：实现选中状态自动切换背景与字体颜色功能
 * 备注：
 */

@SuppressLint("AppCompatCustomView")
public class ChooseTextView extends TextView {
    boolean isChoose = false;//未选中

    public ChooseTextView(Context context) {
        super(context);
        init();
    }

    public ChooseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChooseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChooseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
