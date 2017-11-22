package com.caihan.myscframe.demo.mvp.presenter;

import com.caihan.myscframe.demo.mvp.contract.EBDemoContract;
import com.caihan.myscframe.demo.mvp.model.EBDemoModel;
import com.caihan.scframe.framework.base.impl.MvpBasePresenter;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public class EBDemoPresenter extends MvpBasePresenter<EBDemoContract.View>
        implements EBDemoContract.Presenter {
    EBDemoModel mEBDemoModel;
    public EBDemoPresenter(){
        mEBDemoModel = new EBDemoModel();
    }
    public void eventBtn() {
        mEBDemoModel.eventBtn();
    }

    @Override
    public void detachModel() {

    }

    @Override
    public void changeView(String msg) {

    }
}
