package com.caihan.myscframe.demo;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caihan.myscframe.HomeAdapter;
import com.caihan.myscframe.HomeItem;
import com.caihan.myscframe.R;
import com.caihan.myscframe.demo.mvp.contract.LCEDemoContract;
import com.caihan.myscframe.demo.mvp.presenter.LCEDemoPresenter;
import com.caihan.scframe.framework.support.lce.activity.MvpLceActivity;
import com.caihan.scframe.widget.loadingview.shapeloading.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

import static com.caihan.myscframe.config.ConstValue.ACTIVITY;
import static com.caihan.myscframe.config.ConstValue.TITLE;

public class LceDemoAct extends MvpLceActivity<HomeItem, LCEDemoContract.View<HomeItem>, LCEDemoPresenter>
        implements LCEDemoContract.View<HomeItem> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.loadView)
    LoadingView mLoadView;
    @BindView(R.id.loadingView)
    RelativeLayout mLoadingView;
    @BindView(R.id.errorClickView)
    TextView mErrorClickView;
    @BindView(R.id.errorView)
    RelativeLayout mErrorView;

    private ArrayList<HomeItem> mDataList;

    @Override
    public LCEDemoPresenter createPresenter() {
        return new LCEDemoPresenter();
    }

    @Override
    public int setLayoutResId() {
        return R.layout.activity_lce_demo;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initActionBar() {
        mImmersionBar.titleBar(mToolbar)
//                .statusBarDarkFont(true, 0.2f)
                .init();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showError();
            }
        }, 1000);
    }

    @Override
    public void bindData(HomeItem data) {
        super.bindData(data);
        initAdapter();
    }

    @Override
    public void request() {
        loadData(false);
    }

    @Override
    public void onErrorClick() {
        showLoading(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bindData(null);
            }
        }, 1000);
    }

    private void initAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(mDataList);
        mRecyclerView.setAdapter(homeAdapter);
        showContent();
    }
}
