package app.caihan.myscframe.ui.coordinator;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.caihan.scframe.widget.recyclerview.NoScrollRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import butterknife.BindView;

/**
 * CoordinatorLayout实现复杂联动
 *
 * @author caihan
 * @date 2019/3/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CoordinatorActivity
        extends BaseScMvpActivity<CoordinatorContract.View, CoordinatorPresenter>
        implements CoordinatorContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_tv)
    TextView mLeftTv;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.all_sort_tv)
    TextView mAllSortTv;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.content_rv)
    RecyclerView mContentRv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;


    private CoordinatorAdapter mAdapter;
    private NoScrollRecyclerView mHeaderView;
    private CoordinatorHeaderAdapter mHeaderAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, false);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_coordinator;
    }

    @NonNull
    @Override
    public CoordinatorPresenter createPresenter() {
        return new CoordinatorPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        setBaseToolbarLayout(mToolbar, "复杂联动");
        initRefreshView();
        initRecyclerView();
        mRefreshLayout.autoRefresh();
    }


    private void initRefreshView() {
        //内容不偏移
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        //是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenRefresh(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mContentRv.setLayoutManager(layoutManager);
        mContentRv.setItemAnimator(null);
        mAdapter = new CoordinatorAdapter();
        mAdapter.bindToRecyclerView(mContentRv);
        mAdapter.openLoadAnimation();
        mAdapter.setEmptyView(R.layout.scframe_base_error_layout, mContentRv);
        mAdapter.isUseEmpty(false);
        mAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRefreshLayout.setEnableRefresh(false);
                getData(false);
            }
        }, mContentRv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("点击了第" + position + "张图片");
            }
        });
        initHeaderView();
        mAdapter.setHeaderView(mHeaderView);
    }

    private void initHeaderView() {
        mHeaderView = (NoScrollRecyclerView) LayoutInflater.from(mContext)
                .inflate(R.layout.item_coordinator_header, null);
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        mHeaderView.setLayoutManager(layoutManager);
        mHeaderView.setItemAnimator(null);
        mHeaderAdapter = new CoordinatorHeaderAdapter();
        mHeaderAdapter.bindToRecyclerView(mHeaderView);
        mHeaderAdapter.openLoadAnimation();
        mHeaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("点击了第" + position + "张图片");
            }
        });
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            strings.add("分类" + (i + 1));
        }
        mHeaderAdapter.setNewData(strings);
    }


    private void getData(boolean isRefresh) {
        getPresenter().getData(isRefresh);
    }

    @Override
    public void getDataFinish(boolean isRefresh, List<String> beans, int count) {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.finishRefresh();
        mAdapter.isUseEmpty(true);
        if (isRefresh) {
            mAdapter.setNewData(beans);
        } else {
            mAdapter.addData(beans);
        }
        checkLoadMore(isRefresh, mAdapter, count, getPresenter().getPageSize());
    }

    @Override
    public void getDataError(boolean isRefresh, String msg, int count) {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.finishRefresh();
        mAdapter.isUseEmpty(true);
        checkLoadMore(isRefresh, mAdapter, count, getPresenter().getPageSize());
    }

}
