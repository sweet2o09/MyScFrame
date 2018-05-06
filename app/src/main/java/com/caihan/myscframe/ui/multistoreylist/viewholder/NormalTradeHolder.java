package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;

import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalTradeHolder extends MyViewHolder<LocalShopCartBean> {

    public NormalTradeHolder(View view) {
        super(view);
    }

    @Override
    public void setData(LocalShopCartBean item) {
        setText(R.id.head_tv,item.getCartItemTradeTypeTitle());
    }
}
