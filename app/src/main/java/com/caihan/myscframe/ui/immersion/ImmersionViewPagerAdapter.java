package com.caihan.myscframe.ui.immersion;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.scframe.framework.v1.BaseFragment;

import java.util.HashMap;

/**
 * @author caihan
 * @date 2018/5/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles = {
            "首页",
            "分类",
            "发现",
            "购物车",
            "会员"
    };

    private int[] mIconSelectIds = {
            R.drawable.selector_tab_imag_0,
            R.drawable.selector_tab_imag_1,
            R.drawable.selector_tab_imag_2,
            R.drawable.selector_tab_imag_3,
            R.drawable.selector_tab_imag_4
    };

    public static final int PAGE_COUNT = 5;
    public final static int PAGER_0 = 0;
    public final static int PAGER_1 = 1;
    public final static int PAGER_2 = 2;
    public final static int PAGER_3 = 3;
    public final static int PAGER_4 = 4;

    private HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();
    private Context mContext;

    public ImmersionViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
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
        BaseFragment fragment = null;
        switch (position) {
            case PAGER_0:
                if (!mFragmentMap.containsKey(PAGER_0)) {
                    fragment = ImmersionFragment.newInstance(1);
                    mFragmentMap.put(PAGER_0, fragment);
                }
                fragment = mFragmentMap.get(PAGER_0);
                break;
            case PAGER_1:
                if (!mFragmentMap.containsKey(PAGER_1)) {
                    fragment = ImmersionFragment.newInstance(2);
                    mFragmentMap.put(PAGER_1, fragment);
                }
                fragment = mFragmentMap.get(PAGER_1);
                break;
            case PAGER_2:
                if (!mFragmentMap.containsKey(PAGER_2)) {
                    fragment = ImmersionFragment.newInstance(3);
                    mFragmentMap.put(PAGER_2, fragment);
                }
                fragment = mFragmentMap.get(PAGER_2);
                break;
            case PAGER_3:
                if (!mFragmentMap.containsKey(PAGER_3)) {
                    fragment = ImmersionFragment.newInstance(4);
                    mFragmentMap.put(PAGER_3, fragment);
                }
                fragment = mFragmentMap.get(PAGER_3);
                break;
            case PAGER_4:
                if (!mFragmentMap.containsKey(PAGER_4)) {
                    fragment = ImmersionFragment.newInstance(5);
                    mFragmentMap.put(PAGER_4, fragment);
                }
                fragment = mFragmentMap.get(PAGER_4);
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);// 移出viewpager两边之外的page布局
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
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


    public View getTabView(int position) {
        //首先为子tab布置一个布局
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_immersion_tab_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.tab_item_text);
        textView.setText(mTabTitles[position]);
        ImageView imageView = (ImageView) v.findViewById(R.id.tab_item_imageView);
        imageView.setImageResource(mIconSelectIds[position]);
        return v;
    }

}
