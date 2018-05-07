package com.caihan.myscframe.ui.multistoreylist;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalActivityBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartButtomBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.myscframe.ui.multistoreylist.viewholder.ExceptionBottomHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.ExceptionGoodsHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.ExceptionTradeHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.MyViewHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.NormalActivityHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.NormalBottomHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.NormalGoodsHolder;
import com.caihan.myscframe.ui.multistoreylist.viewholder.NormalTradeHolder;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListAdapter extends BaseMultiItemQuickAdapter<LocalBean, MyViewHolder> {


    public MultiStoreyListAdapter() {
        super(new ArrayList<LocalBean>());
        addItemType(MultiItemConstValue.NORMAL_CART_ITEM_TRADE, R.layout.item_normal_trade);
        addItemType(MultiItemConstValue.NORMAL_ACTIVITY_ITEM, R.layout.item_normal_activity);
        addItemType(MultiItemConstValue.NORMAL_GOODS_ITEM, R.layout.item_normal_goods);
        addItemType(MultiItemConstValue.NORMAL_CART_ITEM_BOTTOM, R.layout.item_normal_bottom);
        addItemType(MultiItemConstValue.EXCEPTION_CART_ITEM_TRADE, R.layout.item_exception_trade);
        addItemType(MultiItemConstValue.EXCEPTION_GOODS_ITEM, R.layout.item_exception_goods);
        addItemType(MultiItemConstValue.EXCEPTION_CART_ITEM_BOTTOM, R.layout.item_exception_bottom);
    }

    @Override
    public void setNewData(@Nullable List<LocalBean> data) {
        super.setNewData(data);
    }

//    @Override
//    protected MyViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case MultiItemConstValue.NORMAL_CART_ITEM_TRADE:
//                return new NormalTradeHolder(getItemView(R.layout.item_normal_trade, parent));
//            case MultiItemConstValue.NORMAL_ACTIVITY_ITEM:
//                return new NormalTradeHolder(getItemView(R.layout.item_normal_activity, parent));
//            case MultiItemConstValue.NORMAL_GOODS_ITEM:
//                return new NormalTradeHolder(getItemView(R.layout.item_normal_goods, parent));
//            case MultiItemConstValue.NORMAL_CART_ITEM_BOTTOM:
//                return new NormalTradeHolder(getItemView(R.layout.item_normal_bottom, parent));
//            case MultiItemConstValue.EXCEPTION_CART_ITEM_TRADE:
//                return new NormalTradeHolder(getItemView(R.layout.item_exception_trade, parent));
//            case MultiItemConstValue.EXCEPTION_GOODS_ITEM:
//                return new NormalTradeHolder(getItemView(R.layout.item_exception_goods, parent));
//            case MultiItemConstValue.EXCEPTION_CART_ITEM_BOTTOM:
//                return new NormalTradeHolder(getItemView(R.layout.item_exception_bottom, parent));
//            default:
//                return super.onCreateDefViewHolder(parent, viewType);
//        }
//    }

    @Override
    protected MyViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        switch (layoutResId) {
            case R.layout.item_normal_trade:
                /**{@link LocalShopCartBean}*/
                return new NormalTradeHolder(getItemView(layoutResId, parent));
            case R.layout.item_normal_activity:
                /**{@link LocalActivityBean}*/
                return new NormalActivityHolder(getItemView(layoutResId, parent));
            case R.layout.item_normal_goods:
                /**{@link CartItemBean}*/
                return new NormalGoodsHolder(getItemView(layoutResId, parent));
            case R.layout.item_normal_bottom:
                /**{@link LocalShopCartButtomBean}*/
                return new NormalBottomHolder(getItemView(layoutResId, parent));
            case R.layout.item_exception_trade:
                /**{@link LocalShopCartBean}*/
                return new ExceptionTradeHolder(getItemView(layoutResId, parent));
            case R.layout.item_exception_goods:
                /**{@link CartItemBean}*/
                return new ExceptionGoodsHolder(getItemView(layoutResId, parent));
            case R.layout.item_exception_bottom:
                /**{@link LocalShopCartButtomBean}*/
                return new ExceptionBottomHolder(getItemView(layoutResId, parent));
            default:
                return createBaseViewHolder(getItemView(layoutResId, parent));
        }
    }

    @Override
    protected void convert(MyViewHolder helper, LocalBean item) {
        switch (helper.getItemViewType()) {
            case MultiItemConstValue.NORMAL_CART_ITEM_TRADE:
            case MultiItemConstValue.NORMAL_ACTIVITY_ITEM:
            case MultiItemConstValue.NORMAL_GOODS_ITEM:
            case MultiItemConstValue.NORMAL_CART_ITEM_BOTTOM:
            case MultiItemConstValue.EXCEPTION_CART_ITEM_TRADE:
            case MultiItemConstValue.EXCEPTION_GOODS_ITEM:
            case MultiItemConstValue.EXCEPTION_CART_ITEM_BOTTOM:
                helper.setData(item);
                break;
            default:
                break;
        }
    }
}
