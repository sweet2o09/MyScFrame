package com.caihan.scframe.framework.v1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter基类
 *
 * {@link ViewPager#setOffscreenPageLimit(int)} 被调用的时候会直接缓存若干个界面,建议大家不要使用
 *
 * @author ""
 * @date 2018/11/10
 *
 */
public abstract class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    protected Context mContext;
    protected FragmentManager mFragmentManager;

    private List<T> mFragments = new ArrayList<>(); // Fragment集合
    private T mCurrentFragment; // 当前显示的Fragment
    private boolean mDestroyItem = false;//是否移出viewpager两边之外的page布局,默认不移除

    public BaseFragmentPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragmentManager = fm;
        init(mFragments);
    }

    protected abstract void init(List<T> list);

    @Override
    public T getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            // 记录当前的Fragment对象
            mCurrentFragment = (T) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mDestroyItem) {
            // 移出viewpager两边之外的page布局
            super.destroyItem(container, position, object);
        }
    }

    /**
     * 获取Fragment集合
     */
    public List<T> getAllFragment() {
        return mFragments;
    }

    /**
     * 获取当前的Fragment
     */
    public T getCurrentFragment() {
        return mCurrentFragment;
    }
}
