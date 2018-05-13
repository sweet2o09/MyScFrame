package com.caihan.myscframe.ui.multistoreylist;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScMvpActivity;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

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
    //底部布局
    @BindView(R.id.free_delivery_fee_tips_tv)
    TextView mFreeDeliveryFeeTipsTv;
    @BindView(R.id.full_check_layout)
    RelativeLayout mFullCheckLayout;
    @BindView(R.id.full_check_cb)
    CheckBox mFullCheckCb;
    @BindView(R.id.settle_btn)
    CheckedTextView mSettleBtn;
    @BindView(R.id.total_amount_tv)
    TextView mTotalAmountTv;
    @BindView(R.id.footer_settle_cl)
    ConstraintLayout mFooterSettleCl;

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
                        getPresenter().changeAllSelected(mAdapter.getData(),
                                !localShopCartBean.isAllSelected(),
                                localShopCartBean.getCartItemTradeType());
                        break;
                    case R.id.head_activity_tv:
                        //有效商品头部活动点击跳转活动专区
                        showToast("点击了活动");
                        break;
                    case R.id.goods_cb_layout:
                        //有效商品选中按钮
                        CartItemBean cartItemBean = (CartItemBean) adapter.getItem(position);
//                        String isSelected = cartItemBean.getIsSelected();
//                        cartItemBean.setIsSelected("1".equals(isSelected) ? "0" : "1");
//                        boolean hasUpdate2 = getPresenter()
//                                .checkHaveAllSelected(mAdapter.getData(), cartItemBean);
//                        if (hasUpdate2) {
//                            mAdapter.notifyDataSetChanged();
//                        } else {
//                            mAdapter.notifyItemChanged(position);
//                        }
                        getPresenter().goodsSelectedChange(mAdapter.getData(),cartItemBean);
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
                        ArrayList<String> strings = getPresenter().setSelectedGoodsList(mAdapter.getData());
                        if (strings.size() > 0) {
                            showToast("结算");
                        }
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

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                LocalBean localBean = (LocalBean) adapter.getItem(position);
                if (localBean instanceof CartItemBean) {
                    if (((CartItemBean) localBean).getCartItemTradeType() >= 0
                            && ((CartItemBean) localBean).getCartItemTradeType() <= 3) {
                        showDelDialog((CartItemBean) localBean);
                    }
                }
                return false;
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

    @Override
    public void delGoodsFinish(LocalData requestData) {
        showToast("删除完成");
        mAdapter.setNewData(requestData.getShoppingCartList());
    }

    @Override
    public void UiButtomAllSelectedBtnStatus(boolean isAllSelected) {
        mFullCheckCb.setChecked(isAllSelected);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.toolbar_right_tv, R.id.full_check_layout, R.id.settle_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_tv:
                mIsEditStatus = !mIsEditStatus;
                mToolbarRightTv.setText(mIsEditStatus ? "完成" : "编辑");
                mFooterSettleCl.setVisibility(mIsEditStatus ? View.VISIBLE : View.GONE);
                break;
            case R.id.full_check_layout:
                boolean isChecked = mFullCheckCb.isChecked();
                getPresenter().changeAllSelected(mAdapter.getData(), !isChecked, -1);
                break;
            case R.id.settle_btn:
                showToast("底部结算按钮");
                break;
            default:
                break;
        }
    }


    private void showDelDialog(final CartItemBean cartItemBean) {
        new MaterialDialog.Builder(mContext)
                .theme(Theme.LIGHT)
                .content("确定删除商品？")
                .positiveText("确定")
                .negativeText("取消")
                .positiveColorRes(R.color.color_FF5252)
                .negativeColorRes(R.color.color_999999)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getPresenter().delGoods(cartItemBean);
                    }
                }).show();
    }

}
