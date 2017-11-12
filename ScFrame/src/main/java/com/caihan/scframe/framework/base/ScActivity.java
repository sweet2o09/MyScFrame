package com.caihan.scframe.framework.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.caihan.scframe.framework.ScActStack;
import com.caihan.scframe.framework.base.bug.MyBug5497;
import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventBusUtils;
import com.caihan.scframe.utils.evenbus.EventSticky;
import com.caihan.scframe.utils.permission.OnPermissionListener;
import com.caihan.scframe.utils.permission.ScPermission;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * 作者：caihan
 * 创建时间：2017/10/29
 * 邮箱：93234929@qq.com
 * 说明：Activity基类
 * 封装了:
 * 沉浸式,
 * EventBus,
 * ButterKnife,
 * webview软键盘弹出bug解决方案,
 * 6.0动态权限
 * 数据变更智能刷新
 */

public abstract class ScActivity extends AppCompatActivity implements ScActView {

    protected Context mContext;
    protected Bundle mSavedInstanceState;
    //沉浸式解决方案
    protected ImmersionBar mImmersionBar;
    //6.0以上权限解决方案
    protected ScPermission mPermission;
    //数据变更智能刷新,变更后是否需要自动刷新数据,刷新后记得设置成false
    private boolean mGoAutoRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //没actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!isCanLandscape()) {
            //取消横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //输入法弹出后改变布局
        setSoftInputMode();
        super.onCreate(savedInstanceState);
        ScActStack.getInstance().addActivity(this);
        //把设置布局文件的操作交给继承的子类
        int layoutRes = setLayoutResId();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
        this.mContext = this;
        this.mSavedInstanceState = savedInstanceState;
        //使用黄油刀
        if (isRegisterButterKnife()) {
            bindButterKnife();
        }
        //初始化沉浸式
        if (immersionBarEnabled()) {
            initImmersionBar();
        }
        getIntentData();
        initActionBar();
        initView();
        setListener();
        registerEventBus();
        initData();
        request();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //数据变更了,允许刷新数据
        if (mGoAutoRefresh) {
            autoRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyimmersionBar();
        unregisterEventBus();
        destroyPermission();
        ScActStack.getInstance().finishActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
            mContext = this;
            newIntentDoSomething();
        }
    }

    protected void newIntentDoSomething() {

    }

    /**
     * 输入法弹出后改变布局
     * 什么都不设置的话,默认SOFT_INPUT_ADJUST_PAN
     * SOFT_INPUT_ADJUST_PAN: 布局被顶起
     * SOFT_INPUT_ADJUST_RESIZE: 布局不被顶起
     * 当界面中有WebView的时候,建议用SOFT_INPUT_ADJUST_RESIZE,其他时候都用SOFT_INPUT_ADJUST_PAN
     * 当布局文件中有控件设置成始终在底部或权重的时候,SOFT_INPUT_ADJUST_RESIZE方法会使得相关布局始终保持在输入法之上
     */
    private void setSoftInputMode() {
        if (isRegisterAdjustPAN()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }


    /*********************沉浸式解决方案*************************/
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
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 销毁
     */
    protected void destroyimmersionBar() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
    }
    /*******************************************************/

    /**
     * 是否支持横屏
     *
     * @return
     */
    protected boolean isCanLandscape() {
        return false;
    }

    /**
     * 是否在输入框弹出的时候顶起布局
     *
     * @return true布局被顶起
     */
    protected boolean isRegisterAdjustPAN() {
        return false;
    }

    /**
     * 是否注册黄油刀
     *
     * @return
     */
    protected boolean isRegisterButterKnife() {
        return true;
    }

    protected void bindButterKnife() {
        ButterKnife.bind(this);
    }

    protected void hideSoftKeyBoard() {
        KeyboardUtils.hideSoftInput(this);
    }

    /**********************EventBus解决方案***************************/
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
    protected void registerEventBus() {
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
    }

    /**
     * 注销EventBus
     */
    protected void unregisterEventBus() {
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (isFinishing() || event == null) return;
        receiveEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventSticky event) {
        if (isFinishing() || event == null) return;
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
    /*******************************************************/

    /**
     * webview软键盘弹出bug
     */
    protected void androidBug5497Workaround() {
        MyBug5497.assistActivity(this);
    }


    /*********************6.0权限解决方案*************************/
    protected void requestPermission(OnPermissionListener listener,
                                     String[]... permissionsArray) {
        if (mPermission == null) {
            mPermission = new ScPermission(this, listener);
        }
        mPermission.requestPermission(permissionsArray);
    }

    protected void requestPermission(OnPermissionListener listener, boolean needFinish,
                                     String[]... permissionsArray) {
        if (mPermission == null) {
            mPermission = new ScPermission(this, listener);
        }
        mPermission.requestPermission(needFinish, permissionsArray);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPermission != null) {
            mPermission.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void destroyPermission() {
        if (mPermission != null) {
            mPermission.onDestroy();
            mPermission = null;
        }
    }
    /*******************************************************/

    /*********************数据变更智能刷新*************************/
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

    /*******************************************************/
}
