package com.caihan.scframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by caihan on 2017/4/17.
 * Fragment基类
 */
public abstract class ScFragment extends Fragment implements ICallback {

    Unbinder unbinder;
    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() > 0) {
            mRootView = inflater.inflate(getLayoutResId(), container, false);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        if (isRegisterButterKnife()) {
            unbinder = ButterKnife.bind(this, mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initToolbar();
        initView();
        setListener();
        initData(savedInstanceState);
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
        if (isRegisterButterKnife()) {
            unbinder.unbind();
        }
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 是否注册黄油刀
     * @return
     */
    protected boolean isRegisterButterKnife(){
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (!isAdded() || event == null) return;
        receiveEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (!isAdded() || event == null) return;
        receiveStickyEvent(event);
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }
}
