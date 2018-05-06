package com.caihan.myscframe.ui.multistoreylist.viewholder;

import android.view.View;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author caihan
 * @date 2018/5/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class MyViewHolder<L extends LocalBean>
        extends BaseViewHolder
        implements IHolder<L> {

    public MyViewHolder(View view) {
        super(view);
    }
}
