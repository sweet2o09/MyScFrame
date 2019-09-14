package app.caihan.myscframe.ui.ninephoto;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caihan.scframe.widget.photo.NinePhotoItem;
import com.caihan.scframe.widget.photo.NinePhotoLayout;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import butterknife.BindView;

/**
 * 九宫格展示
 *
 * @author caihan
 * @date 2019/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoShowActivity
        extends BaseScMvpActivity<NinePhotoShowContract.View, NinePhotoShowPresenter>
        implements NinePhotoShowContract.View, NinePhotoLayout.onItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private NinePhotoShowAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_nine_photo_show;
    }

    @NonNull
    @Override
    public NinePhotoShowPresenter createPresenter() {
        return new NinePhotoShowPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        setBaseToolbarLayout(mToolbar,"九宫格展示");
        initRefreshView();
        initRecyclerView();
        mRefreshLayout.autoRefresh();
    }

    private void initRefreshView() {
        //内容不偏移
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        //是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenRefresh(true);
        mRefreshLayout.setOnRefreshListener(refreshlayout -> getData(true));
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new NinePhotoShowAdapter(this);
        mAdapter.openLoadAnimation();
        mAdapter.setEmptyView(R.layout.scframe_base_error_layout, mRecyclerView);
        mAdapter.isUseEmpty(false);
        mAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mAdapter.setOnLoadMoreListener(() -> {
            mRefreshLayout.setEnableRefresh(false);
            getData(false);
        }, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //空闲状态
//                    ScImageLoader.getInstance().resume(NinePhotoShowActivity.this);
//                } else {
//                    ScImageLoader.getInstance().pause(NinePhotoShowActivity.this);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }

    private void getData(boolean isRefresh) {
        getPresenter().getData(isRefresh);
    }

    @Override
    public void getDataFinish(boolean isRefresh, NinePhotoShowBean ninePhotoShowBean) {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.finishRefresh();
        mAdapter.isUseEmpty(true);
        if (ninePhotoShowBean == null) {
            return;
        }
        if (isRefresh) {
            mAdapter.setNewData(ninePhotoShowBean.getList());
//            mRecyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    int top = mRecyclerView.getChildAt(0).getTop();
//                    mRecyclerView.smoothScrollBy(0, top);
//                }
//            });
        } else {
            mAdapter.addData(ninePhotoShowBean.getList());
        }
        checkLoadMore(isRefresh, mAdapter, Integer.valueOf(ninePhotoShowBean.getTotal()), getPresenter().getPageSize());
    }

    @Override
    public void getDataError() {
        mAdapter.isUseEmpty(true);
    }

    @Override
    public void onClickLargeWhenOnlyOneIv(View view, NinePhotoItem ninePhotoItem) {
        showToast("点击大图");
    }

    @Override
    public void onClickNinePhotoIv(View view, NinePhotoItem ninePhotoItem, int position) {
        showToast("点击了九宫格图片 position = " + position);
    }
}
