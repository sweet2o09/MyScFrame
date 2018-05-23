package com.caihan.scframe.framework.v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.caihan.scframe.dialog.request.DefaultRequestLoading;
import com.caihan.scframe.dialog.request.IRequestLoad;
import com.caihan.scframe.framework.v1.support.mvp.BaseView;
import com.caihan.scframe.immersion.ScImmersionBar;
import com.caihan.scframe.immersion.base.OnImmersionListener;
import com.caihan.scframe.refresh.AutoRefreshListener;
import com.caihan.scframe.utils.toast.ScToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Fragment基类<br/>
 * 继承于RxFragment,并使用RxLifecycle防止RxJava导致的内存泄漏
 * <p>
 * 实现功能:<br/>
 * 1.懒加载<br/>
 * 2.沉浸式<br/>
 * 3.智能刷新数据<br/>
 * 7.网络请求Loading<br/>
 * 8.吐司<br/>
 * 9.BaseQuickAdapter分页判断<br/>
 * <p>
 * 注意事项:
 * {@link FragmentPagerAdapter#destroyItem(ViewGroup, int, Object)}重写并且去掉super调用
 * {@link ViewPager#setOffscreenPageLimit(int)}不要调用
 *
 * @author caihan
 * @date 2018/1/5
 * @e-mail 93234929@qq.com
 */
public abstract class BaseFragment
        extends RxFragment
        implements OnImmersionListener, AutoRefreshListener, BaseView {


    protected Context mContext;
    protected Bundle mSavedInstanceState;
    /**
     * 是否对用户可见
     */
    private boolean mIsVisible = false;
    /**
     * 是否是首次展示Fragment
     */
    protected boolean mIsFirstVisite = true;

    protected View mRootView = null;

    /**
     * 沉浸式
     */
    private ScImmersionBar mImmersionBar = null;

    /**
     * 数据变更智能刷新,变更后是否需要自动刷新数据,刷新后记得设置成false
     */
    private boolean mGoAutoRefresh = false;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrepareData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutResId(), container, false);
    }

    /**
     * 初始化,如果不是直接继承的话请不要使用该方法
     */
    protected abstract void onViewCreated();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当该Fragment为第一个时,会直接调用下面的代码
        if (getUserVisibleHint()) {
            mIsVisible = true;
            if (mIsFirstVisite) {
                onFragmentFirstVisible();
            }
            onVisible();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDestroyImmersion();
        initPrepareData();
        butterKnifeUnBind();
        unRegisterBroadCast();
        onDestroyRequestLoading();
    }

    /**
     * onCreateView()后onActivityCreated()前执行
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = view;
            mContext = getActivity();
            mSavedInstanceState = savedInstanceState;
            butterKnifeBind(mRootView);
            onViewCreated();
        }
        super.onViewCreated(mRootView, savedInstanceState);
    }

    /**
     * setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
     * 如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
     * 如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
     * 总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
     * 如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (mRootView == null) {
            return;
        }
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
            if (mIsFirstVisite) {
                onFragmentFirstVisible();
            }
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mIsVisible = false;
            onInvisible();
        } else {
            mIsVisible = true;
            onVisible();
        }
    }

    /**
     * 绑定黄油刀,项目上自行封装,便于后期更新版本用
     */
    protected void butterKnifeBind(View rootView) {
    }

    protected void butterKnifeUnBind() {
    }

    /**
     * 用户可见时执行的操作
     * <p>
     * 去除{@link #setUserVisibleHint(boolean)}多余的回调场景，
     * 保证只有当fragment可见状态发生变化时才回调
     * <p>
     * 回调时机在view创建完后，所以支持ui操作，
     * 解决在{@link #setUserVisibleHint(boolean)}里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     */
    protected void onVisible() {
        setImmersion();
    }

    /**
     * 用户不可见执行
     */
    protected void onInvisible() {
        dismissRequestLoading();
    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {
        onLazyLoad();
    }

    /**
     * 当mIsVisible = true,mIsFirstVisite = true的时候表示首次界面加载
     */
    private void onLazyLoad() {
        if (mIsVisible && mIsFirstVisite) {
            // TODO: 2018/1/13 加载数据啊
            lazyLoadData();
            mIsFirstVisite = false;
        }
    }

    /**
     * 初始化参数
     */
    protected void initPrepareData() {
        mIsFirstVisite = true;
        mIsVisible = false;
        mRootView = null;
    }

    /**
     * 该方法会初始化沉浸式
     *
     * @return
     */
    @Override
    public ScImmersionBar getImmersion() {
        if (mImmersionBar == null) {
            mImmersionBar = new ScImmersionBar(getActivity(), this);
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

    /**
     * 添加布局文件
     *
     * @return
     */
    @LayoutRes
    protected abstract int setLayoutResId();

    /**
     * 懒加载数据
     */
    protected abstract void lazyLoadData();

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
     * 网络请求专用Loading,如果没有自定义的话,就拿框架自带的
     *
     * @return
     */
    public IRequestLoad getRequestLoading() {
        if (mRequestLoading == null) {
            mRequestLoading = new DefaultRequestLoading(getActivity());
        }
        return mRequestLoading;
    }

    public void setRequestLoading(IRequestLoad requestLoading) {
        mRequestLoading = requestLoading;
    }

    @Override
    public void showRequestLoading() {
        if (mIsVisible) {
            getRequestLoading().show();
        }
    }

    @Override
    public void dismissRequestLoading() {
        if (mRequestLoading != null && mRequestLoading.isShowing()) {
            getRequestLoading().dismiss();
        }
    }

    protected void onDestroyRequestLoading() {
        if (mRequestLoading != null) {
            getRequestLoading().onDestroy();
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
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void finish(){
        getActivity().finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void overridePendingTransition(int enterAnim, int exitAnim){
        getActivity().overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 默认不开启注册广播，设置广播的过滤同时开启广播
     *
     * @param intentFilter 此activity中的广播过滤器
     */
    public void setIntentFilter(IntentFilter intentFilter) {
        this.mIntentFilter = intentFilter;
        registerBroadCast();
    }

    /**
     * 在activity被销毁时，注销广播
     */
    private void unRegisterBroadCast() {
        if (mOtherBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mOtherBroadcastReceiver);
            mOtherBroadcastReceiver = null;
        }

        if (mConnectionChangeReceiver != null) {
            getActivity().unregisterReceiver(mConnectionChangeReceiver);
            mConnectionChangeReceiver = null;
        }
    }

    /**
     * 为fragment准备的广播接收器，主要为了实现fragment在需要刷新的时候刷新，也可以有其他用途
     */
    public void registerBroadCast() {
        if (mOtherBroadcastReceiver == null) {
            mOtherBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveBroadCast(context, intent);
                }
            };
            getActivity().registerReceiver(mOtherBroadcastReceiver, mIntentFilter);
        }
    }

    /**
     * 当接受到广播后的处理
     *
     * @param context 接受广播的上下文
     * @param intent  该广播的意图
     */
    public void onReceiveBroadCast(Context context, Intent intent) {

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
                adapter.loadMoreEnd(true);
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
