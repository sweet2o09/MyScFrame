package com.caihan.myscframe.ui.multistoreylist.viewholder;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;

/**
 * @author caihan
 * @date 2018/5/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IHolder<L extends LocalBean> {

    void setData(L item);
}
