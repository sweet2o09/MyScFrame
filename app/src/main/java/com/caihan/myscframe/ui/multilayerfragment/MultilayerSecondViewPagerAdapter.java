package com.caihan.myscframe.ui.multilayerfragment;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.framework.v1.BaseFragment;
import com.caihan.scframe.utils.log.ScLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2018/9/28
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultilayerSecondViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mList = new ArrayList<>();
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;

    public MultilayerSecondViewPagerAdapter(ArrayList<String> list,FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mList = list;
        mFragmentList.clear();
        for (int i = 0; i < mList.size(); i++) {
            mFragmentList.add(MultilayerSecondMvpFragment.newInstance(i));
        }
    }

    /**
     * 完美解决强制刷新问题
     *
     * @param data
     */
    public void setData(@NonNull List<String> data) {
        mList = data;
        FragmentTransaction ft = mFragmentManager.beginTransaction();//获得FragmentTransaction 事务
        for (BaseFragment fragment : mFragmentList) {
            ft.remove(fragment);
        }
        ft.commit();
        ft = null;
        mFragmentManager.executePendingTransactions();//提交事务
        mFragmentList.clear();
        for (int i = 0; i < mList.size(); i++) {
            mFragmentList.add(MultilayerSecondMvpFragment.newInstance(i));
        }
        notifyDataSetChanged();
    }

    /**
     * 完美解决强制刷新问题
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    /**
     * 获取给定位置对应的Fragment。
     *
     * @param position 给定的位置
     * @return 对应的Fragment
     */
    @Override
    public BaseFragment getItem(int position) {
        ScLog.debug("SecondViewPagerAdapter position = " + position + " getItem()");
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
//        mTags.add(makeFragmentName(container.getId(), getItemId(position)));
//        ScLog.debug("SecondViewPagerAdapter position = " + position + " instantiateItem()");
//        BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
//        mFragmentManager.beginTransaction().show(fragment).commit();
//        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);// 移出viewpager两边之外的page布局
//        ScLog.debug("SecondViewPagerAdapter position = " + position + " destroyItem()");
//        BaseFragment fragment = mFragmentList.get(position);
//        mFragmentManager.beginTransaction().hide(fragment).commit();
    }

    /**
     * 获取给定位置的项Id，用于生成Fragment名称
     *
     * @param position 给定的位置
     * @return 项Id
     */
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
