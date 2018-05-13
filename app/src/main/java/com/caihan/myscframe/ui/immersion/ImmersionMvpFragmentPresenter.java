package com.caihan.myscframe.ui.immersion;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;

/**
 * @author caihan
 * @date 2018/5/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionMvpFragmentPresenter extends MvpBasePresenter<ImmersionMvpFragmentContract.View> {


    public ImmersionMvpFragmentPresenter(Context context) {
        super(context);
    }

    @Override
    public void destroy() {

    }
}