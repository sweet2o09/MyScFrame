package com.caihan.scframe.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.caihan.scframe.R;
import com.caihan.scframe.utils.ScOutdatedUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 尝试封装SmartRefreshLayout,实现基础的MaterialHeader
 *
 * @author caihan
 * @date 2018/3/1
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ScRefreshLayout extends SmartRefreshLayout {

    Context mContext;
    MaterialHeader mMaterialHeader;
    BaseQuickAdapter mQuickAdapter;

    public ScRefreshLayout(Context context) {
        super(context);
        this.initialization(context);
    }

    public ScRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialization(context);
    }

    public ScRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialization(context);
    }

    public void setQuickAdapter(BaseQuickAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    /**
     * 初始化
     */
    private void initialization(Context context) {
        this.mContext = context;
        mMaterialHeader = new MaterialHeader(mContext);
        mMaterialHeader.setColorSchemeColors(ScOutdatedUtils.getColor(R.color.color_FF5252));
        //关闭背景
        mMaterialHeader.setShowBezierWave(false);
        //内容不偏移
        setEnableHeaderTranslationContent(false);
        //是否在刷新的时候禁止列表的操作
        setDisableContentWhenRefresh(true);
        setEnableRefresh(true);
        setEnableLoadmore(false);
    }

    /**
     * 添加下拉刷新监听
     *
     * @param listener
     */
    public void setRefreshListener(final OnRefreshListener listener) {
        this.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startRefresh();
                listener.onRefresh(refreshlayout);
            }
        });
    }

    /**
     * 添加上拉加载监听
     *
     * @param requestLoadMoreListener
     * @param recyclerView
     */
    public void setOnLoadMoreListener(final BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener,
                                      RecyclerView recyclerView) {
        if (mQuickAdapter != null) {
            mQuickAdapter.setLoadMoreView(new SimpleLoadMoreView());
            mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    startLoadMore();
                    requestLoadMoreListener.onLoadMoreRequested();
                }
            }, recyclerView);
        }
    }

    /**
     * 开始下拉刷新
     */
    private void startRefresh() {
    }

    /**
     * 开始上拉加载
     */
    private void startLoadMore() {
        setEnableRefresh(false);
    }

    /**
     * 刷新结束
     */
    private void successRefresh() {
        setEnableRefresh(true);
        finishRefresh();
    }

    /**
     * 加载结束
     */
    private void successLoadMore() {
    }

    /**
     * 全部结束
     */
    private void successRefreshLoadmore() {
        successRefresh();
        successLoadMore();
    }
}
