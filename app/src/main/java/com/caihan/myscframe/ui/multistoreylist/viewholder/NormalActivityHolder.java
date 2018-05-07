package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;

import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalActivityBean;

/**
 * 有效商品活动Header
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalActivityHolder extends MyViewHolder<LocalActivityBean> {

    public NormalActivityHolder(View view) {
        super(view);
    }

    @Override
    public void setData(LocalActivityBean item) {
        setText(R.id.head_activity_tv, item.getCartActivityItemSubTitle());
    }
}
