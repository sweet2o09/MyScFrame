package com.caihan.myscframe.ui.immersion;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;

import butterknife.BindView;

/**
 * Fragment沉浸式
 *
 * @author caihan
 * @date 2018/5/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionFragmentActivity extends BaseScActivity {

    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;



    @Override
    protected int setLayoutResId() {
        return R.layout.activity_immersion_fragment;
    }

    @Override
    protected void onCreate() {
        ImmersionViewPagerAdapter pagerAdapter = new ImmersionViewPagerAdapter(mContext, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        //多缓存几个界面,这样Fragment就不会被销毁
//        mViewPager.setOffscreenPageLimit((pagerAdapter.PAGE_COUNT - 1));
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
//            mTabLayout.getTabAt(i).setText(mTabTitles[i]).setIcon(mIconSelectIds[i]).isSelected();
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 当position = ((TimelinePagerAdapter) mViewPager.getAdapter()).getCount() -1,
             * positionOffset = 0.0,
             * positionOffsetPixels = 0的时候,
             * 表示已经滑动到最右边,可以执行切换操作
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(0);
    }
}
