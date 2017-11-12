package com.caihan.scframe.framework.support.lce.activity;

import com.caihan.scframe.R;
import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.support.activity.MvpActivity;
import com.caihan.scframe.framework.support.lce.animator.ILceAnimator;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 说明：集成LCE设计+MVP的Activity
 * LCE设计思路:
 * 先展示L_View也就是LoadingView
 * 当网络请求成功后显示C_View也就是正常布局,如果失败的话显示E_View也就是ErrorView
 * 如果第一次网络请求成功,后续的请求不成功也不再显示E_View
 */
public abstract class MvpLceActivity<D, V extends MvpLceView<D>, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> implements MvpLceView<D> {

    private MvpLceViewImpl<D> mvpLceView;
    //是否是第一次网络请求
    private boolean isFirstRequest = true;

    private MvpLceViewImpl<D> getMvpLceView() {
        if (this.mvpLceView == null) {
            this.mvpLceView = new MvpLceViewImpl<D>();
        }
        return mvpLceView;
    }

    public boolean isFirstRequest() {
        return isFirstRequest;
    }

    /**
     * 网络请求回调成功后调用该方法
     */
    protected void requestSuccess() {
        isFirstRequest = true;
        //数据变更,关闭智能刷新
        autoRefreshSuccess();
    }

    //onContentChanged:当我们的布局发生变化的时候里面回调
    //特点：在Activity第一次启动的时候，也会回调该方法
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpLceView().initView(findViewById(R.id.rootView));
    }

    //子类设置指定动画策略
    public void setLceAnimator(ILceAnimator lceAnimator) {
        getMvpLceView().setLceAnimator(lceAnimator);
    }

    @Override
    public void showLoading(boolean isPullRefresh) {
        if (isFirstRequest()){
            getMvpLceView().showLoading(isPullRefresh);
        }
    }

    @Override
    public void showContent(boolean isPullRefresh) {
        getMvpLceView().showContent(isPullRefresh);
    }

    @Override
    public void showError(boolean isPullRefresh) {
        if (isFirstRequest()){
            getMvpLceView().showError(isPullRefresh);
        }
    }

    @Override
    public void bindData(D data, boolean isPullRefresh) {
        getMvpLceView().bindData(data, isPullRefresh);
    }

    @Override
    public void loadData(boolean isPullRefresh) {
        getMvpLceView().loadData(isPullRefresh);
    }

}
