package com.caihan.myscframe.ui.multilayerfragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScMvpFragment;
import com.caihan.myscframe.ui.multistoreylist.request.data;
import com.caihan.scframe.utils.log.ScLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * @author caihan
 * @date 2018/5/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultilayerMvpFragment
        extends BaseScMvpFragment<MultilayerMvpFragmentContract.View, MultilayerMvpFragmentPresenter>
        implements MultilayerMvpFragmentContract.View {

    @BindView(R.id.text_view)
    TextView mTextView;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.other_layout)
    RelativeLayout mOtherLayout;
    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.pager_layout)
    LinearLayout mPagerLayout;


    private int mType;
    private MultilayerSecondViewPagerAdapter mSecondViewPagerAdapter;
    private int mLastTabSize = -1;

    public static MultilayerMvpFragment newInstance(int type) {
        MultilayerMvpFragment fragment = new MultilayerMvpFragment();
        Bundle args = new Bundle();
        args.putInt("key", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGoAutoRefresh()){
            ScLog.debug("MultilayerFragment setData onResume");
            mSecondViewPagerAdapter.setData(getTabList());
            autoRefreshSuccess();
        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (isGoAutoRefresh()){
            ScLog.debug("MultilayerFragment setData onVisible");
            mSecondViewPagerAdapter.setData(getTabList());
            autoRefreshSuccess();
        }
    }

    @Override
    public void setImmersion() {
        ScLog.debug("MultilayerFragment mType = " + mType + " setImmersion");
        if (mType == 1) {
            getImmersion().setImmersionDarkFont(mTabLayout, true);
        } else {
            getImmersion().setImmersionTransparentDarkFont(mType % 2 == 1);
        }
    }


    @NonNull
    @Override
    public MultilayerMvpFragmentPresenter createPresenter() {
        return new MultilayerMvpFragmentPresenter(mContext);
    }

    @Override
    protected void onViewCreatedMvp() {
        mType = getArguments().getInt("key");
        ScLog.debug("MultilayerFragment mType = " + mType + " onViewCreatedMvp");
        if (mType == 1) {
            mOtherLayout.setVisibility(View.GONE);
            mPagerLayout.setVisibility(View.VISIBLE);
            initViewPager();
        } else {
            mOtherLayout.setVisibility(View.VISIBLE);
            mPagerLayout.setVisibility(View.GONE);
            mTextView.setText("我是Multilayer mType = " + mType);
        }
    }

    private void initViewPager() {
        mTabLayout.removeAllTabs();
        mSecondViewPagerAdapter = new MultilayerSecondViewPagerAdapter(getTabList(), getChildFragmentManager());
        mViewPager.setAdapter(mSecondViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setCurrentItem(0);
    }

    private ArrayList<String> getTabList() {
        ArrayList<String> list = new ArrayList<>();
        Random random = new Random();
        int size = random.nextInt(10);
        if (mLastTabSize == size || size <= 1) {
            return getTabList();
        }
        mLastTabSize = size;
        for (int i = 0; i < mLastTabSize; i++) {
            list.add("tab" + i);
        }
        if (mLastTabSize > 4) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        return list;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_multilayer_mvp;
    }

    @Override
    protected void lazyLoadData() {
        if (mType != 1) {
            getPresenter().showWord();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getUserVisibleHint() && AppUtils.isAppForeground()){
                        ScLog.debug("MultilayerFragment mType = " + mType + " setData()");
                        mSecondViewPagerAdapter.setData(getTabList());
                    }else {
                        autoRefresh();
                        ScLog.debug("MultilayerFragment mUserVisibleHint = false setData()");
                    }
                }
            }, 5000);
        }
    }

    @Override
    public void showWord() {
        mBtn.setText("" + mType);
        showToast("MultilayerFragment mType = " + mType + " lazyLoadData");
    }


}
