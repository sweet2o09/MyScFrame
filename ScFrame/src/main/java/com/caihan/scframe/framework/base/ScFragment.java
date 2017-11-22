package com.caihan.scframe.framework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventBusUtils;
import com.caihan.scframe.utils.evenbus.EventSticky;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：caihan
 * 创建时间：2017/11/22
 * 邮箱：93234929@qq.com
 * 说明：Fragment基类
 * 封装了:
 * 沉浸式,
 * 懒加载,
 * EventBus,
 * ButterKnife
 * 数据变更智能刷新
 */
public abstract class ScFragment extends Fragment implements ScFragmentView {
    protected Unbinder unbinder;
    protected Context mContext;
    protected View mRootView;
    protected Activity mActivity;
    protected Bundle mSavedInstanceState;
    /**
     * 是否对用户可见
     */
    protected boolean isVisible = false;
    /**
     * 是否首次界面挂在完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean isPrepare = false;

    //沉浸式解决方案
    protected ImmersionBar mImmersionBar;
    //数据变更智能刷新,变更后是否需要自动刷新数据,刷新后记得设置成false
    private boolean mGoAutoRefresh = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //把设置布局文件的操作交给继承的子类
        int layoutRes = setLayoutResId();
        if (layoutRes > 0) {
            mRootView = inflater.inflate(setLayoutResId(), container, false);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        //使用黄油刀
        if (isRegisterButterKnife()) {
            bindButterKnife();
        }
        if (isLazyLoad()) {
            isPrepare = true;
            onLazyLoad();
        } else {
            getIntentData();
            if (immersionBarEnabled()) {
                initImmersionBar();
            } else {
                initActionBar();
            }
            initView();
            setListener();
            registerEventBus();
            initData();
            request();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //数据变更了,允许刷新数据
        if (mGoAutoRefresh) {
            autoRequest();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyimmersionBar();
        unregisterEventBus();
        unBindButterKnife();
    }

    //**********懒加载解决方案 start****************************************//

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }

    /**
     * 当mIsVisible = true,mIsPrepare = true的时候表示首次界面加载
     * 这时候去加载沉浸式解决方案以及发起网络请求
     */
    private void onLazyLoad() {
        getIntentData();
        if (isVisible) {
            if (immersionBarEnabled()) {
                initImmersionBar();
            } else {
                initActionBar();
            }
        }
        initView();
        setListener();
        if (isVisible && isPrepare) {
            isPrepare = false;
            registerEventBus();
            initData();
            request();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    private void onVisible() {
        isVisible = true;
        if (immersionBarEnabled()) {
            initImmersionBar();
        } else {
            initActionBar();
        }
    }

    /**
     * 用户不可见执行
     */
    private void onInvisible() {
        isVisible = false;
    }
    //**********懒加载解决方案 end****************************************//


    //**********沉浸式解决方案 start****************************************//

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean immersionBarEnabled() {
        return true;
    }

    /**
     * 初始化
     */
    private void initImmersionBar() {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.navigationBarWithKitkatEnable(false).init();
        }
        initActionBar();
    }

    /**
     * 销毁
     */
    private void destroyimmersionBar() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
    }
    //**********沉浸式解决方案 end****************************************//

    /**
     * 是否注册黄油刀
     *
     * @return
     */
    protected boolean isRegisterButterKnife() {
        return true;
    }

    private void bindButterKnife() {
        unbinder = ButterKnife.bind(this, mRootView);
    }

    private void unBindButterKnife() {
        if (isRegisterButterKnife() && unbinder != null) {
            unbinder.unbind();
        }
    }

    //**********EventBus解决方案 start****************************************//

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 注册EventBus
     */
    private void registerEventBus() {
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
    }

    /**
     * 注销EventBus
     */
    private void unregisterEventBus() {
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (!isAdded() || event == null) return;
        receiveEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventSticky event) {
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
     * 接受到分发的粘性事件,事件处理完之后调用super执行removeStickyEvent
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(EventSticky event) {
        EventBusUtils.removeStickyEvent(event);
    }
    //**********EventBus解决方案 end****************************************//


    //**********数据变更智能刷新 start****************************************//

    /**
     * 智能刷新数据完成,在网络请求回调成功的时候调用该方法
     */
    protected void autoRefreshSuccess() {
        mGoAutoRefresh = false;
    }

    /**
     * 需要智能刷新数据
     */
    protected void autoRefresh() {
        mGoAutoRefresh = true;
    }

    /**
     * 智能刷新数据所需的网络请求
     */
    protected void autoRequest() {
    }
    //**********数据变更智能刷新 end****************************************//
}
