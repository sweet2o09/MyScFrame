package com.caihan.myscframe.ui.multilayerfragment;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;

/**
 * @author caihan
 * @date 2018/5/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultilayerMvpFragmentPresenter extends MvpBasePresenter<MultilayerMvpFragmentContract.View> {


    public MultilayerMvpFragmentPresenter(Context context) {
        super(context);
    }

    public void showWord(){

        getView().showWord();
    }

    @Override
    public void destroy() {

    }
}