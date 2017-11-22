package com.caihan.scframe.framework.support.lce.activity;

import android.view.View;

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

    private MvpLceViewImpl<D> lceViewImpl;
    //是否是第一次网络请求
    private boolean isFirstRequest = true;


    /**
     * 提供给子类配置自己想要的动画策略
     *
     * @param lceAnimator
     */
    public void setLceAnimator(ILceAnimator lceAnimator) {
        lceViewImpl.setLceAnimator(lceAnimator);
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
        lceViewImpl = new MvpLceViewImpl<D>();
        initLceView(getWindow().getDecorView());
    }

    private void initLceView(View v) {
        lceViewImpl.initLceView(v);
        lceViewImpl.setOnErrorViewClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onErrorClick();
            }
        });
        //初始化的时候先展示LoadingView
        showLoading(false);
    }

    @Override
    public void showLoading(boolean isPullRefresh) {
        if (isFirstRequest()) {
            //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
            lceViewImpl.showLoading(isPullRefresh);
        }
    }

    @Override
    public void showContent() {
        lceViewImpl.showContent();
    }

    @Override
    public void showError() {
        if (isFirstRequest()) {
            lceViewImpl.showError();
        }
    }

    @Override
    public void bindData(D data) {
        lceViewImpl.bindData(data);
    }

    @Override
    public void loadData(boolean isPullRefresh) {
        lceViewImpl.loadData(isPullRefresh);
    }

    public void onErrorClick() {
        loadData(false);
    }
}
