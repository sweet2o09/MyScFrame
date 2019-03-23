package app.caihan.myscframe.ui.coordinator;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.caihan.scframe.utils.imageloader.ScImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import app.caihan.myscframe.model.ResultsBean;
import butterknife.BindView;

/**
 * BottomSheet的使用
 *
 * @author caihan
 * @date 2019/3/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class BottomSheetActivity
        extends BaseScMvpActivity<BottomSheetContract.View, BottomSheetPresenter>
        implements BottomSheetContract.View {

    @BindView(R.id.content_layout)
    CoordinatorLayout mContentLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.bottom_sheet_tv)
    TextView mBottomSheetTv;
    @BindView(R.id.bottom_sheet_iv)
    ImageView mBottomSheetIv;
    @BindView(R.id.bottom_sheet_layout)
    RelativeLayout mBottomSheetLayout;
    @BindView(R.id.bottom_sheet_bar_layout)
    RelativeLayout mBottomSheetBarLayout;

    private BottomSheetAdapter mAdapter;
    private BottomSheetBehavior mBehavior;
    /**
     * 标识初始化时是否修改了底栏高度
     */
    private boolean isSetBottomSheetHeight = false;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, false);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_bottom_sheet;
    }

    @NonNull
    @Override
    public BottomSheetPresenter createPresenter() {
        return new BottomSheetPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        mToolbarTitle.setText("BottomSheet的使用");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        initRefreshView();
        initRecyclerView();
        setListener();
        mRefreshLayout.autoRefresh(1000);
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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //解决item跳动
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(null);
        mAdapter = new BottomSheetAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.openLoadAnimation();
        mAdapter.setEmptyView(R.layout.scframe_base_error_layout, mRecyclerView);
        mAdapter.isUseEmpty(false);
        mAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRefreshLayout.setEnableRefresh(false);
                getData(false);
            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("点击了第" + position + "张图片");
                ScImageLoader.getInstance()
                        .display(mAdapter.getItem(position).getUrl(),
                                R.drawable.image_nine_photo_def,
                                R.drawable.image_nine_photo_def,
                                mBottomSheetIv);
            }
        });
    }

    private void setListener() {
        //底栏状态改变的监听
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState != BottomSheetBehavior.STATE_COLLAPSED && mBottomSheetTv.getVisibility() == View.VISIBLE) {
                    mBottomSheetTv.setVisibility(View.GONE);
                    mBottomSheetIv.setVisibility(View.VISIBLE);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED && mBottomSheetTv.getVisibility() == View.GONE) {
                    mBottomSheetTv.setVisibility(View.VISIBLE);
                    mBottomSheetIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (bottomSheet.getTop() < 2 * mBottomSheetBarLayout.getHeight() + mToolbar.getHeight()) {
                    //设置底栏完全展开时，出现的顶部工具栏的动画
                    mBottomSheetBarLayout.setVisibility(View.VISIBLE);
                    mBottomSheetBarLayout.setAlpha(slideOffset);
                    mBottomSheetBarLayout.setTranslationY(bottomSheet.getTop() - 2 * mBottomSheetBarLayout.getHeight() - mToolbar.getHeight());
                } else {
                    mBottomSheetBarLayout.setVisibility(View.INVISIBLE);
                }
            }
        });


        mBottomSheetBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击顶部工具栏 将底栏变为折叠状态
                showToast("关闭BottomSheet");
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mBottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空实现,为了处理展开时会相应Adapter的点击事件
            }
        });

        //recyclerView滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    //recyclerview滚动时  如果BottomSheetBehavior不是折叠状态就置为折叠
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //修改SetBottomSheet的高度 留出顶部工具栏的位置
        if (!isSetBottomSheetHeight) {
            CoordinatorLayout.LayoutParams linearParams = (CoordinatorLayout.LayoutParams) mBottomSheetLayout.getLayoutParams();
            linearParams.height = mContentLayout.getHeight() - mBottomSheetBarLayout.getHeight() - mToolbar.getHeight();
            mBottomSheetLayout.setLayoutParams(linearParams);
            isSetBottomSheetHeight = true;
        }
    }

    private void getData(boolean isRefresh) {
        getPresenter().getData(isRefresh);
    }

    @Override
    public void getDataFinish(boolean isRefresh, List<ResultsBean> beans, int count) {
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

    class BottomSheetAdapter extends BaseQuickAdapter<ResultsBean, BaseViewHolder> {

        private int mHeadSize = 0;

        public BottomSheetAdapter() {
            super(R.layout.item_bottom_sheet);
            mHeadSize = SizeUtils.dp2px(400);
        }

        @Override
        protected void convert(BaseViewHolder helper, ResultsBean item) {

            ScImageLoader.getInstance()
                    .display(item.getUrl(),
                            R.drawable.image_nine_photo_def,
                            R.drawable.image_nine_photo_def,
                            mHeadSize,
                            mHeadSize,
                            helper.getView(R.id.pic_iv));

        }
    }
}
