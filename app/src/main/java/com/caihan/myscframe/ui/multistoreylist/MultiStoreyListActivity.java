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
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.head_cb_layout:
                        //有效商品头部全选按钮
                        LocalShopCartBean localShopCartBean = (LocalShopCartBean) adapter.getItem(position);
                        boolean hasUpdate = getPresenter()
                                .changeAllSelected(mAdapter.getData(), !localShopCartBean.isAllSelected());
                        if (hasUpdate) {
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.head_activity_tv:
                        //有效商品头部活动点击跳转活动专区
                        showToast("点击了活动");
                        break;
                    case R.id.goods_cb_layout:
                        //有效商品选中按钮
                        CartItemBean cartItemBean = (CartItemBean) adapter.getItem(position);
                        String isSelected = cartItemBean.getIsSelected();
                        cartItemBean.setIsSelected("1".equals(isSelected) ? "0" : "1");
                        boolean hasUpdate2 = getPresenter()
                                .checkHaveAllSelected(mAdapter.getData(), cartItemBean);
                        if (hasUpdate2) {
                            mAdapter.notifyDataSetChanged();
                        }else {
                            mAdapter.notifyItemChanged(position);
                        }
                        break;
                    case R.id.goods_pic_iv:
                    case R.id.goods_title_tv:
                        //有效商品图片与文字点击跳转商品详情
                        showToast("跳转详情");
                        break;
                    case R.id.tax_amount_tv:
                        //有效商品底部税费Dialog
                        showToast("税费");
                        break;
                    case R.id.settle_btn:
                        //有效商品底部结算按钮
                        showToast("结算");
                        break;
                    case R.id.clear_goods_tv:
                        //不支持,失效商品底部清空按钮
                        showToast("清空");
                        break;
                    default:
                        break;
                }
            }
        });
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
