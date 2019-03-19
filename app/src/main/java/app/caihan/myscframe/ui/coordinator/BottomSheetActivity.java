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
import com.caihan.scframe.utils.json.JsonAnalysis;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;

/**
 * BottomSheet的使用
 *
 * @author caihan
 * @date 2019/3/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class BottomSheetActivity extends BaseScActivity {

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

    @Override
    protected void onCreate() {
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
        mRefreshLayout.autoRefresh(1500);
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
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.finishRefresh();
        mAdapter.isUseEmpty(true);
        List<ImageItemBean> itemBeans = JsonAnalysis.getInstance().listFromJson(getPicJsonString(), ImageItemBean.class);
        if (isRefresh) {
            mAdapter.setNewData(itemBeans);
        } else {
            mAdapter.addData(itemBeans);
        }
    }

    private String getPicJsonString() {
        return "[{\"_id\":\"5c6a4ae99d212226776d3256\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg\"},{\"_id\":\"5c6385b39d21225dd7a417ce\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1g04lsmmadlj31221vowz7.jpg\"},{\"_id\":\"5c4578db9d212264d4501d40\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg\"},{\"_id\":\"5c2dabdb9d21226e068debf9\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg\"},{\"_id\":\"5c25db189d21221e8ada8664\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg\"},{\"_id\":\"5c12216d9d21223f5a2baea2\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg\"},{\"_id\":\"5bfe1a5b9d2122309624cbb7\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg\"},{\"_id\":\"5bf22fd69d21223ddba8ca25\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg\"},{\"_id\":\"5be14edb9d21223dd50660f8\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg\"},{\"_id\":\"5bcd71979d21220315c663fc\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg\"},{\"_id\":\"5bc434ac9d212279160c4c9e\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg\"},{\"_id\":\"5bbb0de09d21226111b86f1c\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg\"},{\"_id\":\"5ba206ec9d2122610aba3440\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fvexaq313uj30qo0wldr4.jpg\"},{\"_id\":\"5b9771a29d212206c1b383d0\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg\"},{\"_id\":\"5b830bba9d2122031f86ee51\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg\"},{\"_id\":\"5b7b836c9d212201e982de6e\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg\"},{\"_id\":\"5b74e9409d21222c52ae4cb4\",\"url\":\"https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg\"},{\"_id\":\"5b7102749d2122341d563844\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg\"},{\"_id\":\"5b6bad449d21226f45755582\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg\"},{\"_id\":\"5b67b7fd9d2122195bdbd806\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg\"},{\"_id\":\"5b63cd4e9d21225e0d3f58c9\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg\"},{\"_id\":\"5b6151509d21225206860f08\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1ftu6gl83ewj30k80tites.jpg\"},{\"_id\":\"5b60356a9d212247776a2e0e\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqgy1ftt7g8ntdyj30j60op7dq.jpg\"},{\"_id\":\"5b5e93499d21220fc64181a9\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqgy1ftrrvwjqikj30go0rtn2i.jpg\"},{\"_id\":\"5b50107f421aa917a31c0565\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg\"},{\"_id\":\"5b4eaae4421aa93aa99bee16\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg\"},{\"_id\":\"5b481d01421aa90bba87b9ae\",\"url\":\"http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg\"},{\"_id\":\"5b456f5d421aa92fc4eebe48\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg\"},{\"_id\":\"5b441f06421aa92fccb520a2\",\"url\":\"https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg\"},{\"_id\":\"5b42d1aa421aa92d1cba2918\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1ft3fna1ef9j30s210skgd.jpg\"},{\"_id\":\"5b3ed2d5421aa91cfe803e35\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg\"},{\"_id\":\"5b3d883f421aa906e5b3c6f1\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsysqszneoj30hi0pvqb7.jpg\"},{\"_id\":\"5b3ae394421aa906e7db029b\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fswhaqvnobj30sg14hka0.jpg\"},{\"_id\":\"5b398cf8421aa95570db5491\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsvb1xduvaj30u013175p.jpg\"},{\"_id\":\"5b33ccf2421aa95570db5478\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg\"},{\"_id\":\"5b32807e421aa95570db5471\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsp4iok6o4j30j60optbl.jpg\"},{\"_id\":\"5b31aa33421aa9556d2cc4a7\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg\"},{\"_id\":\"5b2f8847421aa9556b44c666\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsmis4zbe7j30sg16fq9o.jpg\"},{\"_id\":\"5b0d6ac0421aa97efda86560\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1frslruxdr1j30j60ok79c.jpg\"},{\"_id\":\"5b27c7aa421aa923c0fbfda0\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1k9cb5j30sg0y7q61.jpg\"},{\"_id\":\"5b27c7bf421aa923c0fbfda1\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg\"},{\"_id\":\"5b27c7eb421aa923c43fe485\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq2pwt72j30qo0yg78u.jpg\"},{\"_id\":\"5b2269a6421aa92a5f2a35f9\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fsb0lh7vl0j30go0ligni.jpg\"},{\"_id\":\"5b1fec10421aa9793930bf99\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs8tym1e8ej30j60ouwhz.jpg\"},{\"_id\":\"5b1fec9f421aa9793930bf9a\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs8u1joq6fj30j60orwin.jpg\"},{\"_id\":\"5b1e8164421aa910a82cf54f\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs7l8ijitfj30jg0shdkc.jpg\"},{\"_id\":\"5b196deb421aa910ab3d6b3d\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs35026dloj30j60ov79x.jpg\"},{\"_id\":\"5b196d0b421aa910ab3d6b3c\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg\"},{\"_id\":\"5b17fec9421aa9109f56a6bb\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1fs1vq7vlsoj30k80q2ae5.jpg\"},{\"_id\":\"5b1026a0421aa9029661ae00\",\"url\":\"http://ww1.sinaimg.cn/large/0065oQSqly1frv032vod8j30k80q6gsz.jpg\"}]";
    }


    class BottomSheetAdapter extends BaseQuickAdapter<ImageItemBean, BaseViewHolder> {

        private int mHeadSize = 0;

        public BottomSheetAdapter() {
            super(R.layout.item_bottom_sheet);
            mHeadSize = SizeUtils.dp2px(400);
        }

        @Override
        protected void convert(BaseViewHolder helper, ImageItemBean item) {

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
