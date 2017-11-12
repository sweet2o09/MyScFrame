package com.caihan.scframe.framework.support.lce.refresh.activity;

import android.support.v7.widget.RecyclerView;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.support.lce.activity.MvpLceActivity;
import com.caihan.scframe.framework.support.lce.activity.MvpLceView;
import com.caihan.scframe.widget.refresh.ScRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * 作者：caihan
 * 创建时间：2017/11/11
 * 邮箱：93234929@qq.com
 * 说明：集成下拉刷新+LCE设计+MVP的Activity
 */
public abstract class MvpLceRefreshActivity<D, V extends MvpLceView<D>,
        P extends MvpPresenter<V>, A extends BaseQuickAdapter>
        extends MvpLceActivity<D, V, P> {
    //下拉刷新组件
    private ScRefreshLayout mRefreshView;

    private RecyclerView mRecyclerView;

    private A mAdapter;

    //是否是下拉刷新
    private boolean isDownPullRefresh;


}
