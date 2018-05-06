package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalGoodsHolder extends MyViewHolder<CartItemBean> {

    private String mRmbString = "¥";

    public NormalGoodsHolder(View view) {
        super(view);
    }

    @Override
    public void setData(CartItemBean item) {
        final int limitItemNum = Integer.valueOf(item.getLimitItemNum());//商品限购数
        final int itemNum = Integer.valueOf(item.getItemNum());//购物车商品数
        final int storeCount = Integer.valueOf(item.getStoreCount());//库存数
        final int buyItemNum = Integer.valueOf(item.getBuyItemNum());//已购买件数
        final int minItemBuyNum = Integer.valueOf(item.getMinItemBuyNum());//最低起售件数
        final int allowBuyNum = limitItemNum - buyItemNum;//剩余可购买数

        Glide.with(getConvertView().getContext())
                .load(item.getPicUrl())
                .centerCrop()
                .error(R.drawable.list_loading_goods)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) getView(R.id.goods_pic_iv));

        setText(R.id.goods_title_tv, item.getTitle())
                .setText(R.id.goods_sku_tv, item.getSkuProperty())
                .setText(R.id.goods_price_tv, mRmbString + item.getMemberPrice())
                .addOnClickListener(R.id.goods_pic_iv)
                .addOnClickListener(R.id.goods_title_tv);

        setText(R.id.store_num_tv, "仅剩" + storeCount + "件")
                .setGone(R.id.store_num_tv, itemNum > storeCount);

        setGone(R.id.goods_processing_tv, !StringUtils.isEmpty(item.getProcessingItemName()))
                .setText(R.id.goods_processing_tv, "口味: " + item.getProcessingItemName());

        TextView limitBuyTips = getView(R.id.goods_limit_buy_tv);
        limitBuyTips.setVisibility(View.GONE);
        //判断限购
        if (item.getIsOverLimitItemNum().equals("1")) {
            //限购数量提示
            if (buyItemNum == 0) {
                limitBuyTips.setText("限购" + limitItemNum + "件");
            } else if (limitItemNum > buyItemNum) {
                limitBuyTips.setText("限购" + limitItemNum + "件，您还可购买" + allowBuyNum + "件");
            } else if (limitItemNum <= buyItemNum) {
                limitBuyTips.setText("限购" + limitItemNum + "件，已购买" + buyItemNum + "件");
            }
            limitBuyTips.setVisibility(View.VISIBLE);
        } else {
            //判断起售
            if (minItemBuyNum > 1) {
                limitBuyTips.setText(minItemBuyNum + "件起售");
                if (minItemBuyNum > storeCount) {
                    limitBuyTips.setText(minItemBuyNum + "件起售,当前库存不足");
                } else if (minItemBuyNum > 0) {
                    if (limitItemNum > minItemBuyNum && allowBuyNum < minItemBuyNum) {
                        limitBuyTips.setText("限购" + limitItemNum + "件");
                    } else if (limitItemNum < minItemBuyNum) {
                        limitBuyTips.setText(minItemBuyNum + "件起售，剩余可购买" + allowBuyNum + "件");
                    }
                }
                limitBuyTips.setVisibility(View.VISIBLE);
            }
        }

    }
}
