package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.text.SpannableStringBuilder;
import android.view.View;

import com.blankj.utilcode.util.SpanUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.scframe.utils.ScOutdatedUtils;

/**
 * 不支持/失效商品Header
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ExceptionTradeHolder extends MyViewHolder<LocalShopCartBean> {

    public ExceptionTradeHolder(View view) {
        super(view);
    }

    @Override
    public void setData(LocalShopCartBean item) {
        if (item.getCartItemTradeType() == 5) {
            //不支持
            SpannableStringBuilder sp;
            int count = item.getCartItemTradeTypeTitle().indexOf("不支持") + 3;
            String S1 = item.getCartItemTradeTypeTitle().substring(0, count);
            String S2 = item.getCartItemTradeTypeTitle().substring(count,
                    item.getCartItemTradeTypeTitle().length());

            sp = new SpanUtils().append(S1)
                    .append(S2).setForegroundColor(ScOutdatedUtils.getColor(R.color.color_FF5252))
                    .create();

            setText(R.id.cart_item_ex_head_tv, sp);
        } else if (item.getCartItemTradeType() == 4) {
            //失效
            setText(R.id.cart_item_ex_head_tv, item.getCartItemTradeTypeTitle());
        }

    }
}
