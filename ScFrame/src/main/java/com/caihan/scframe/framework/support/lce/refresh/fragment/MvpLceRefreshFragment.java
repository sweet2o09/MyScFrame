package com.caihan.scframe.framework.support.lce.refresh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.R;
import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.support.lce.activity.MvpLceView;
import com.caihan.scframe.framework.support.lce.fragment.MvpLceFragment;
import com.caihan.scframe.widget.refresh.ScRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * 作者：caihan
 * 创建时间：2017/11/11
 * 邮箱：93234929@qq.com
 * 说明：下拉刷新Fragment
 */
public abstract class MvpLceRefreshFragment<D, V extends MvpLceView<D>,
        P extends MvpPresenter<V>, A extends BaseQuickAdapter>
        extends MvpLceFragment<D, V, P> {

    //下拉刷新组件
    private ScRefreshLayout mRefreshView;

    private RecyclerView mRecyclerView;

    private A mAdapter;

    //是否是下拉刷新
    private boolean isDownPullRefresh;

    //首次加载
    private boolean isInit = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setContentView(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initContentView(view);
        if (isInit) {
            isInit = false;
            initData();
        }
    }

    public boolean isDownPullRefresh() {
        return isDownPullRefresh;
    }

    public ScRefreshLayout getRefreshView() {
        return mRefreshView;
    }

    //初始化内容View
    public void initContentView(View contentView) {
        initRefreshView(contentView);
    }

    public void initRefreshView(View contentView) {
        mRefreshView = (ScRefreshLayout) contentView.findViewById(R.id.refreshView);
        //设置
        mRefreshView.setEnableRefresh(true);
        mRefreshView.setEnableLoadmore(false);//默认是关闭状态
        mRefreshView.setEnableLoadmoreWhenContentNotFull(false);

        mRecyclerView = mRefreshView.getRecyclerView();

        mAdapter = bindAdapter();
        //添加Adapter
        mRecyclerView.setAdapter(mAdapter);

        //添加监听
        mRefreshView.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshData(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData(true);
            }
        });
    }

    public abstract void initData();

    public abstract int setContentView();

    //这个具体的实现由子类实现
    public abstract A bindAdapter();

    //LCE设计
    @Override
    public void bindData(D data, boolean isPullRefresh) {
        super.bindData(data, isPullRefresh);
        //数据返回成功->判定刷新状态(两种类型：下拉刷新、上拉加载)
        //抽象下拉刷新组件功能功能
        if (isDownPullRefresh()) {
            //下拉刷新
//            getRefreshView().stopRefresh();
            getRefreshView().finishRefresh();
            //恢复上拉状态
            getRefreshView().setLoadmoreFinished(false);
        } else {
            //上拉加载
//            getRefreshLayout().stopLoadMore();
            getRefreshView().finishLoadmore();
        }
    }

    //更新状态->具体的实现由子类决定
    public void refreshData(boolean isDownPullRefresh) {
        this.isDownPullRefresh = isDownPullRefresh;
    }


    /**
     * 加载更多完成
     */
    public void finishLoadmore() {
        mRefreshView.finishLoadmore();
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    public void setLoadmoreFinished() {
        mRefreshView.finishLoadmore();
        mRefreshView.setLoadmoreFinished(true);
    }

}
