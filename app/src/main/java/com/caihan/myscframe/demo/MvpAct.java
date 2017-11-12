package com.caihan.myscframe.demo;

import android.view.View;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.demo.mvp.contract.MvpDemoContract;
import com.caihan.myscframe.demo.mvp.presenter.MvpDemoPresenter;
import com.caihan.scframe.framework.support.activity.MvpActivity;

import butterknife.BindView;

public class MvpAct extends MvpActivity<MvpDemoContract.View, MvpDemoPresenter> implements
        MvpDemoContract.View {

    @BindView(R.id.msg_tv)
    TextView mMsgTv;

    @Override
    public MvpDemoPresenter createPresenter() {
        return new MvpDemoPresenter();
    }

    @Override
    public int setLayoutResId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initActionBar() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void request() {

    }

    public void mvpBtn(View view) {
        getPresenter().sendMsg();
    }

    @Override
    public void changeView(String msg) {
        mMsgTv.setText(msg);
    }
}
