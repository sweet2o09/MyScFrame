package com.caihan.myscframe.demo;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.demo.mvp.contract.EBDemoContract;
import com.caihan.myscframe.demo.mvp.presenter.EBDemoPresenter;
import com.caihan.scframe.framework.support.activity.MvpActivity;
import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventSticky;

import butterknife.BindView;

public class EventBusAct extends MvpActivity<EBDemoContract.View, EBDemoPresenter> implements
        EBDemoContract.View {

    @BindView(R.id.msg_tv)
    TextView mMsgTv;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        LogUtils.d(event.getCode());
        if ("now".equals(event.getCode())) {
            changeView("普通EventBus事件" + TimeUtils.getNowMills());
        }
    }

    @Override
    protected void receiveStickyEvent(EventSticky event) {
        LogUtils.d(event.getCode());
        if ("Sticky".equals(event.getCode())) {
            changeView("黏性EventBus事件" + TimeUtils.getNowMills());
        }
        super.receiveStickyEvent(event);
    }

    @Override
    public EBDemoPresenter createPresenter() {
        return new EBDemoPresenter();
    }

    @Override
    public int setLayoutResId() {
        return R.layout.activity_event_bus;
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

    @Override
    public void changeView(String msg) {
        mMsgTv.setText(msg);
    }

    public void eventBtn(View view) {
        getPresenter().eventBtn();
    }
}
