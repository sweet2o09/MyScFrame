package com.caihan.scframe.framework.v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caihan.scframe.dialog.request.DefaultRequestLoading;
import com.caihan.scframe.dialog.request.IRequestLoad;
import com.caihan.scframe.framework.v1.support.mvp.BaseView;
import com.caihan.scframe.immersion.ScImmersionBar;
import com.caihan.scframe.immersion.base.OnImmersionListener;
import com.caihan.scframe.permission.ScPermission;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.refresh.AutoRefreshListener;
import com.caihan.scframe.utils.toast.ScToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Activity基类<br/>
 * 继承于RxAppCompatActivity,并使用RxLifecycle防止RxJava导致的内存泄漏
 * <p>
 * 实现功能:<br/>
 * 1.ActionBar开关,默认关闭<br/>
 * 2.横竖屏开关,默认竖屏(禁止分屏)<br/>
 * 3.输入法弹出是否改变布局,默认改变<br/>
 * 4.动态权限<br/>
 * 5.沉浸式<br/>
 * 6.智能刷新数据<br/>
 * 7.网络请求Loading<br/>
 * 8.吐司<br/>
 * 9.BaseQuickAdapter分页判断<br/>
 *
 * @author caihan
 * @date 2018/1/5
 * @e-mail 93234929@qq.com
 */
public abstract class BaseActivity
        extends RxAppCompatActivity
        implements OnImmersionListener, AutoRefreshListener, BaseView {

    protected Context mContext;
    protected Bundle mSavedInstanceState;

    /**
     * 动态权限申请
     */
    protected ScPermission mPermission;

    /**
     * 沉浸式
     */
    private ScImmersionBar mImmersionBar = null;

    /**
     * 数据变更智能刷新,变更后是否需要自动刷新数据,刷新后记得设置成false
     */
    private boolean mGoAutoRefresh = false;
    /**
     * 数据总条数不超过一页数据时是否隐藏底部加载更多结束文字
     */
    private boolean mHideLoadMoreEnd = true;

    /**
     * 用于网络请求的Dialog
     */
    private IRequestLoad mRequestLoading;

    /**
     * 使用者定义并使用的广播
     */
    private BroadcastReceiver mOtherBroadcastReceiver;
    /**
     * 网络状态转变的广播
     */
    private BroadcastReceiver mConnectionChangeReceiver;
    /**
     * 使用者定义的广播意图过滤器
     */
    private IntentFilter mIntentFilter;

    /**
     * RxJava订阅工具
     */
    private CompositeDisposable mCompositeDisposable = null;

    //********抽象接口,这边不做过多的接口 start*********//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isNoActionBar();
        sereenOrientation();
        setSoftInputMode();
        super.onCreate(savedInstanceState);
        isLauncherTaskRoot(isLauncherAct());
        mContext = this;
        mSavedInstanceState = savedInstanceState;
        initContentView();
    }

    private void initContentView() {
        //把设置布局文件的操作交给继承的子类
        int layoutRes = setLayoutResId();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
        butterKnifebind();
        onCreate();
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
        //关闭软键盘
        hideSoftKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyImmersion();
        onDestroyPermission();
        unRegisterBroadCast();
        onDestroyRequestLoading();
        clearDisposable();
    }

    @LayoutRes
    protected abstract int setLayoutResId();

    /**
     * 如果不是直接继承BaseActivity的话,请勿使用该方法
     */
    protected abstract void onCreate();

    //********抽象接口,这边不做过多的接口 end*********//

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
     * 绑定黄油刀,项目上自行封装,便于后期更新版本用
     */
    protected void butterKnifebind() {
    }

    /**
     * 把Toolbar绑定到界面上,以ActionBar的形式展现
     *
     * @param toolbar
     * @param title
     * @param showTitleEnabled
     * @param showHomeAsUpEnabled
     */
    protected void setToolBar(Toolbar toolbar, String title,
                              boolean showTitleEnabled, boolean showHomeAsUpEnabled) {
        if (toolbar != null) {
            toolbar.setTitle(title);//设置主标题
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(showTitleEnabled);//是否显示主标题
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUpEnabled);//显示返回键
        }
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


    /**
     * 动态权限申请
     *
     * @param listener
     * @param permissionArray
     */
    public void requestPermission(@NonNull OnPermissionListener listener,
                                  @NonNull String[]... permissionArray) {
        onDestroyPermission();
        mPermission = new ScPermission.Builder(mContext)
                .setListener(listener)
                .build();
        mPermission.request(permissionArray);
    }

    /**
     * 动态权限申请
     *
     * @param listener
     * @param permissionArray
     */
    public void requestPermission(@NonNull OnPermissionListener listener,
                                  @NonNull ArrayList<String> permissionArray) {
        onDestroyPermission();
        mPermission = new ScPermission.Builder(mContext)
                .setListener(listener)
                .build();
        mPermission.request(permissionArray);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPermission != null && mPermission.isPermissionRequest(requestCode)) {
            mPermission.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onDestroyPermission() {
        if (mPermission != null) {
            mPermission.onDestroy();
            mPermission = null;
        }
    }

    /**
     * 是否横屏
     *
     * @return
     */
    protected boolean isLandscape() {
        return false;
    }

    /**
     * 选择屏幕方向
     */
    private void sereenOrientation() {
        if (!isLandscape()) {
            //强制竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //强制横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 是否横屏
     *
     * @return
     */
    protected boolean isNoTitle() {
        return true;
    }

    /**
     * 选择是否展示ActionBar,默认是不展示,现在基本是用toolbar代替
     */
    private void isNoActionBar() {
        if (isNoTitle()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
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

    /**
     * 关闭软键盘,调用三方Utils工具
     */
    protected void hideSoftKeyBoard() {
        KeyboardUtils.hideSoftInput(this);
    }

    /**
     * 解决安装界面直接点击打开应用再按home键返回桌面，重新进入app重复实例化launcher activity的问题
     * https://www.cnblogs.com/sunsh/articles/4846320.html
     */
    private void isLauncherTaskRoot(boolean isLauncher) {
        if (isLauncher && !this.isTaskRoot()) {
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }

    protected boolean isLauncherAct() {
        return false;
    }

    //**********沉浸式效果 start****************************************//

    /**
     * 该方法会初始化沉浸式
     *
     * @return
     */
    @Override
    public ScImmersionBar getImmersion() {
        if (mImmersionBar == null) {
            mImmersionBar = new ScImmersionBar(this);
        }
        return mImmersionBar;
    }

    @Override
    public void setImmersion() {
    }

    @Override
    public void onDestroyImmersion() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
    //**********沉浸式效果 end****************************************//


    //**********数据变更智能刷新 start****************************************//
    @Override
    public void autoRefreshSuccess() {
        mGoAutoRefresh = false;
    }

    @Override
    public void autoRefresh() {
        mGoAutoRefresh = true;
    }

    @Override
    public void autoRequest() {
    }
    //**********数据变更智能刷新 end****************************************//


    /**
     * 添加RxJava订阅
     * 一般用于RxView等
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 网络请求专用Loading,如果没有自定义的话,就拿框架自带的
     *
     * @return
     */
    private IRequestLoad getRequestLoading() {
        if (mRequestLoading == null) {
            mRequestLoading = new DefaultRequestLoading(this);
        }
        return mRequestLoading;
    }

    public void setRequestLoading(IRequestLoad requestLoading) {
        mRequestLoading = requestLoading;
    }

    @Override
    public void showRequestLoading() {
        getRequestLoading().show();
    }

    @Override
    public void dismissRequestLoading() {
        if (mRequestLoading != null && mRequestLoading.isShowing()) {
            mRequestLoading.dismiss();
        }
    }

    protected void onDestroyRequestLoading() {
        if (mRequestLoading != null) {
            mRequestLoading.onDestroy();
            mRequestLoading = null;
        }
    }

    /**
     * 网络请求出错
     *
     * @param error
     */
    @Override
    public void onRequestError(String error) {
        LogUtils.eTag("Request", "error = " + error);
    }

    /**
     * 吐司
     *
     * @param msg
     */
    @Override
    public void showToast(@NonNull String msg) {
        ScToast.showToast(msg);
    }

    /**
     * 吐司
     *
     * @param resId
     */
    @Override
    public void showToast(@StringRes final int resId) {
        ScToast.showToast(resId);
    }

    /**
     * 弹出吐司
     *
     * @param msg
     */
    protected void showToastLong(@NonNull String msg) {
        ScToast.showToastLong(msg);
    }

    /**
     * 拥有默认动作的启动activity方法
     *
     * @param intent   要启动activity的意图
     * @param isFinish 是否关闭当前activity
     */
    public void startActivity(Intent intent, boolean isFinish) {
        super.startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * 拥有默认动作的启动activity方法
     *
     * @param intent   要启动activity的意图
     * @param reqCode  请求码
     * @param isFinish 是否关闭当前activity
     */
    public void startActivityForResult(Intent intent, int reqCode, boolean isFinish) {
        super.startActivityForResult(intent, reqCode);
        if (isFinish) {
            finish();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 默认不开启注册广播，设置广播的过滤同时开启广播
     *
     * @param intentFilter 此activity中的广播过滤器
     */
    protected void setIntentFilter(IntentFilter intentFilter) {
        this.mIntentFilter = intentFilter;
        registerBroadCast();
    }

    /**
     * 为activity准备的广播接收器，主要为了实现activity在需要刷新的时候刷新，也可以有其他用途
     */
    private void registerBroadCast() {
        if (mOtherBroadcastReceiver == null) {
            mOtherBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveBroadCast(context, intent);
                }
            };
            registerReceiver(mOtherBroadcastReceiver, mIntentFilter);
        }
    }

    /**
     * 在activity被销毁时，注销广播
     */
    private void unRegisterBroadCast() {
        if (mOtherBroadcastReceiver != null) {
            unregisterReceiver(mOtherBroadcastReceiver);
            mOtherBroadcastReceiver = null;
        }

        if (mConnectionChangeReceiver != null) {
            unregisterReceiver(mConnectionChangeReceiver);
            mConnectionChangeReceiver = null;
        }
    }

    /**
     * 当接受到广播后的处理
     *
     * @param context 接受广播的上下文
     * @param intent  该广播的意图
     */
    protected void onReceiveBroadCast(Context context, Intent intent) {

    }

    /**
     * 设置数据不超过一页时是否隐藏加载更多结束
     *
     * @param hideLoadMoreEnd
     */
    protected void setHideLoadMoreEnd(boolean hideLoadMoreEnd) {
        mHideLoadMoreEnd = hideLoadMoreEnd;
    }

    /**
     * 判断分页是否完成,并且当不满一页数据的时候,不显示LoadMore布局
     *
     * @param adapter
     * @param total
     * @param onePageSize
     */
    protected void checkLoadMore(boolean isRefresh, BaseQuickAdapter adapter, int total, int onePageSize) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter can no be null");
        }
        if (isRefresh) {
            if (total <= onePageSize) {
                adapter.loadMoreEnd(mHideLoadMoreEnd);
            }
        } else {
            if (adapter.getData().size() >= total) {
                //数据全部加载完毕
                adapter.loadMoreEnd();
            } else {
                //成功获取更多数据
                adapter.loadMoreComplete();
            }
        }
    }
}
