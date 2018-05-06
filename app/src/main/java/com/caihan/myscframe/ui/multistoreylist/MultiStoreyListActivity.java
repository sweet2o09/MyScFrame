package com.caihan.myscframe.ui.multistoreylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScMvpActivity;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 多层级数据处理
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListActivity
        extends BaseScMvpActivity<MultiStoreyListContract.View, MultiStoreyListPresenter>
        implements MultiStoreyListContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_tv)
    TextView mToolbarRightTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private boolean mIsEditStatus = false;// 是否编辑状态
    private MultiStoreyListAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_multi_storey_list;
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        mToolbarTitle.setText("多层数据嵌套");
        mToolbarRightTv.setVisibility(View.VISIBLE);
        mToolbarRightTv.setText("编辑");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initAdapter();
        getPresenter().requestData();
    }

    private void initAdapter() {
        mAdapter = new MultiStoreyListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//            }
//        });
    }

    @NonNull
    @Override
    public MultiStoreyListPresenter createPresenter() {
        return new MultiStoreyListPresenter(this);
    }

    @Override
    public void requestDataFinish(LocalData requestData) {
        showToast("数据请求完成");
        mAdapter.setNewData(requestData.getShoppingCartList());
    }

    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        mIsEditStatus = !mIsEditStatus;
        mToolbarRightTv.setText(mIsEditStatus ? "完成" : "编辑");
    }
}
