package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartButtomBean;
import com.caihan.scframe.utils.ScOutdatedUtils;
import com.caihan.scframe.utils.text.DoubleUtils;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalBottomHolder extends MyViewHolder<LocalShopCartButtomBean> {

    private String mRmbString = "¥";

    public NormalBottomHolder(View view) {
        super(view);
    }

    @Override
    public void setData(LocalShopCartButtomBean item) {
        boolean mIsOverLimitBuyPrice = !StringUtils.isEmpty(item.getBuyMultiItemTips());//是否超过默认限购额
        setText(R.id.total_goods_amount_tv, "商品总额：" + mRmbString + DoubleUtils.format(item.getItemTotalAmount()))
                .setGone(R.id.total_goods_amount_tv, Integer.valueOf(item.getCartItemTradeType()) != 0)
                .setText(R.id.tax_amount_tv, "税费：" + mRmbString + DoubleUtils.format(item.getTaxAmount()))
                .setGone(R.id.tax_amount_tv, Integer.valueOf(item.getCartItemTradeType()) != 0)
                .setText(R.id.buy_multi_item_tips_tv, item.getBuyMultiItemTips())
                .setGone(R.id.buy_multi_item_tips_tv, mIsOverLimitBuyPrice)
                .setGone(R.id.free_delivery_tips_tv, true)
                .setText(R.id.free_delivery_tips_tv, "免配送费")
                .setText(R.id.total_amount_tv, new SpanUtils().append("合计（不含运费）：")
                        .append(mRmbString + DoubleUtils.format(item.getTotalAmount()))
                        .setForegroundColor(ScOutdatedUtils.getColor(R.color.color_FF5252))
                        .create())
                .setText(R.id.settle_btn, "去结算");
    }
}
