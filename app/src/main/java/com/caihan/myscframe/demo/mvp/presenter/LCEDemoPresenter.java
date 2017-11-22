package com.caihan.myscframe.demo.mvp.presenter;

import com.caihan.myscframe.HomeItem;
import com.caihan.myscframe.demo.mvp.contract.LCEDemoContract;
import com.caihan.scframe.framework.base.impl.MvpBasePresenter;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public class LCEDemoPresenter extends MvpBasePresenter<LCEDemoContract.View<HomeItem>>
        implements LCEDemoContract.Presenter {
    @Override
    public void detachModel() {

    }

    @Override
    public void changeView(String msg) {

    }
}
