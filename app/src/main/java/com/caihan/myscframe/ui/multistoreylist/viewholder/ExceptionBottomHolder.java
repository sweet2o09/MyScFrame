package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartButtomBean;

/**
 * 不支持/无效商品底部清空布局
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ExceptionBottomHolder extends MyViewHolder<LocalShopCartButtomBean> {

    public ExceptionBottomHolder(View view) {
        super(view);
    }

    @Override
    public void setData(LocalShopCartButtomBean item) {
        CharSequence clearGoodS = "清空失效商品";
        if (item.getCartItemTradeType() == 5) {
            clearGoodS = new SpanUtils()
                    .append("清").appendSpace(SizeUtils.dp2px(8))
                    .append("空")
                    .create();
        }
        setText(R.id.clear_goods_tv, clearGoodS)
                .addOnClickListener(R.id.clear_goods_tv);
    }
}
