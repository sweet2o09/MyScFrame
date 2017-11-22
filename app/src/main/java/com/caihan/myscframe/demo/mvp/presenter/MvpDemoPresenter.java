package com.caihan.myscframe.demo.mvp.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.caihan.myscframe.demo.mvp.contract.MvpDemoContract;
import com.caihan.myscframe.demo.mvp.model.MvpDemoModel;
import com.caihan.scframe.framework.base.impl.MvpBasePresenter;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public class MvpDemoPresenter extends MvpBasePresenter<MvpDemoContract.View>
        implements MvpDemoContract.Presenter {

    MvpDemoModel mModel;

    public MvpDemoPresenter() {
        mModel = new MvpDemoModel(this);
    }

    public void sendMsg(){
        mModel.sendMsg();
    }

    @Override
    public void detachModel() {
        LogUtils.d("detachModel");
        if (mModel != null) {
            mModel.onDestroy();
            mModel = null;
        }
    }

    @Override
    public void changeView(String msg) {
        getView().changeView(msg);
    }
}
