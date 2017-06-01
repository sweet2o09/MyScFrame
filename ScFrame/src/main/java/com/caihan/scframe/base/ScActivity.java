package com.caihan.scframe.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.caihan.scframe.common.ScActStack;
import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventBusUtil;
import com.caihan.scframe.utils.permission.OnPermissionListener;
import com.caihan.scframe.utils.permission.ScPermission;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by caihan on 2017/4/17.
 * Activity基类
 */
public abstract class ScActivity extends AppCompatActivity implements ICallback {
    private static final String TAG = "ScActivity";

    protected Context mContext;
    protected ScPermission mPermission;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
            mContext = this;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//没actionbar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//取消横屏
        setSoftInputMode();//输入法弹出后改变布局
        super.onCreate(savedInstanceState);
        ScActStack.getInstance().addActivity(this);
        if (getLayoutResId() > 0) {
            setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类
        }
//        setFitsSystemWindows();//对根布局设置setFitsSystemWindows属性
//        androidBug5497Workaround();//webview软键盘弹出bug
        mContext = this;
        initData(savedInstanceState);
        initView();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    /**
     * 输入法弹出后改变布局
     * 什么都不设置的话,默认SOFT_INPUT_ADJUST_PAN
     * SOFT_INPUT_ADJUST_PAN: 布局被顶起
     * SOFT_INPUT_ADJUST_RESIZE: 布局不被顶起
     * 当界面中有WebView的时候,建议用SOFT_INPUT_ADJUST_RESIZE,其他时候都用SOFT_INPUT_ADJUST_PAN
     */
    private void setSoftInputMode() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 对根布局设置setFitsSystemWindows属性
     */
    private void setFitsSystemWindows() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
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
     * webview软键盘弹出bug
     */
    protected void androidBug5497Workaround() {
        MyBug5497.assistActivity(this);
    }

    protected void initPermission() {
        mPermission = new ScPermission(this);
    }

    /**
     * 权限允许或拒绝对话框
     *
     * @param permissions 需要申请的权限
     * @param needFinish  如果必须的权限没有允许的话，是否需要finish当前 Activity
     * @param callback    回调对象
     */
    protected void requestPermission(final ArrayList<String> permissions, final boolean needFinish,
                                     final OnPermissionListener callback) {
        if (mPermission != null) {
            mPermission.requestPermission(permissions, needFinish, callback);
        }
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (mPermission != null) {
            mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPermission != null) {
            mPermission.onActivityResult(requestCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScActStack.getInstance().finishActivity();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
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
    protected void receiveStickyEvent(Event event) {
        EventBusUtil.removeStickyEvent(event);
    }

}
