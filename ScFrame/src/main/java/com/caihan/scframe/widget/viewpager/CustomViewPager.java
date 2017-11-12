package com.caihan.scframe.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：自定义ViewPager,控制左右滑动切屏
 * 备注：
 * 第一种:修改onInterceptTouchEvent,onTouchEvent
 * 第二种:修改scrollTo(幽默...)
 */
public class CustomViewPager extends ViewPager {
    private static final String TAG = "CustomViewPager";
    private boolean isCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets scroll. true 可以滑动  false 不可以滑动
     *
     * @param isCanScroll the is scroll
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    /**
     * 在使用ViewPager+Fragment 进行跳转的时候出现，闪屏的一个效果！
     * 我们的目的是点击某个Item的时候跳转到指定的Fragment，
     * 为此在我们使用viewPager.setCurrentItem(position) 就会出现过度动效的问题
     * 解决方案是调用setCurrentItem(item,false)方法,false表示禁止平滑滚动效果
     *
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return this.isCanScroll && super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return this.isCanScroll && super.onInterceptTouchEvent(arg0);
    }

}
