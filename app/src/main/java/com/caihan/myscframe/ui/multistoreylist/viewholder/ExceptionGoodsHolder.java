package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.scframe.utils.ScOutdatedUtils;
import com.caihan.scframe.utils.text.BaseParser;

/**
 * 不支持/无效商品
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ExceptionGoodsHolder extends MyViewHolder<CartItemBean> {

    public ExceptionGoodsHolder(View view) {
        super(view);
    }

    @Override
    public void setData(CartItemBean item) {

        Glide.with(getConvertView().getContext())
                .load(item.getPicUrl())
                .centerCrop()
                .error(R.drawable.list_loading_goods)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) getView(R.id.goods_pic_iv));

        /**
         * 0-正常
         * 1-下架显示（包括取消授权）
         * 2-商品售罄（库存不足）显示（扫码购库存不足按商品售罄处理）
         * 3-商品属性变更（包括切换为预售商品）
         * 4-不支持快递配送（当DeliveryTypeId=2，如果无门店库存，cartItemTradeType=5, invalidCartType=4）
         * 5-不支持到店自提（当DeliveryTypeId=3，如果无门店库存，cartItemTradeType=5, invalidCartType=5）
         * 6-不支持快速配送（当DeliveryTypeId=1，商品不支持快递配送cartItemTradeType=5, invalidCartType=6）
         * 7-不支持次日达（当DeliveryTypeId=4，商品不支持次日达cartItemTradeType=5, invalidCartType=7）
         */
        setTextColor(R.id.invalid_cart_type_tips_tv,
                ScOutdatedUtils.getColor((BaseParser.parseInt(item.getInvalidCartType()) == 0 ||
                        BaseParser.parseInt(item.getInvalidCartType()) > 3) ?
                        R.color.color_FF5252 : R.color.light_text_color))
                .setText(R.id.invalid_cart_type_tips_tv, item.getInvalidCartTypeTips())
                .setText(R.id.goods_title_tv, item.getTitle())
                .setText(R.id.goods_num_tv, "X" + item.getItemNum());
    }
}
