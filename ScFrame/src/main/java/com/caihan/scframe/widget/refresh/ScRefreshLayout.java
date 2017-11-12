package com.caihan.scframe.widget.refresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 作者：caihan
 * 创建时间：2017/10/28
 * 邮箱：93234929@qq.com
 * 实现功能：尝试封装SmartRefreshLayout,实现基础的Header+RecyclerView+Footer
 * 备注：
 */
public class ScRefreshLayout extends SmartRefreshLayout {
    private Context mContext;
    private boolean isAddFinish = false;
    private RecyclerView mRecyclerView;

    public ScRefreshLayout(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public ScRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    public ScRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initViews();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void initViews() {
        if (!isAddFinish) {
            initHeaderView();
            initRecyclerView();
            initFooterView();
            isAddFinish = true;
        }
    }

    private void initHeaderView() {

    }

    private void initRecyclerView() {
        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        addView(mRecyclerView);
    }

    private void initFooterView() {
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null && mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null && mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }
}
