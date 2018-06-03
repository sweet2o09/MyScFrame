package com.caihan.scframe.framework.v1.support.mvp.lce.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.mvp.fragment.ScMvpFragment;
import com.caihan.scframe.framework.v1.support.mvp.lce.MvpLceView;
import com.caihan.scframe.framework.v1.support.mvp.lce.MvpLceViewImpl;
import com.caihan.scframe.framework.v1.support.mvp.lce.animator.ILceAnimator;

/**
 * MVP->LCE设计模式,Fragment基类
 * <p>
 * LCE设计思路:<br/>
 * 先展示L_View也就是LoadingView<br/>
 * 当网络请求成功后显示C_View也就是正常布局,如果失败的话显示E_View也就是ErrorView<br/>
 * 如果第一次网络请求成功,后续的请求不成功也不再显示E_View<br/>
 *
 * @author caihan
 * @date 2018/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class ScMvpLceFragment
        <D, V extends MvpLceView<D>, P extends MvpPresenter<V>>
        extends ScMvpFragment<V, P>
        implements MvpLceView<D> {

    //初始化Lce UI布局(规定你的Lce布局文件的id)
    private MvpLceViewImpl<D> lceViewImpl;
    //是否是第一次网络请求
    private boolean isFirstRequest = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (lceViewImpl == null) {
            lceViewImpl = new MvpLceViewImpl<D>();
        }
        initLceView(view);
        onViewCreatedMvpLce();
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
        showLoadingLayout(false);
    }

    /**
     * 初始化,如果不是直接继承的话请不要使用该方法
     */
    protected abstract void onViewCreatedMvpLce();

    @Override
    @Deprecated
    protected void onViewCreatedMvp() {

    }

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
        isFirstRequest = false;
        //数据变更,关闭智能刷新
        autoRefreshSuccess();
    }

    @Override
    public void showLoadingLayout(boolean isPullRefresh) {
        if (isFirstRequest()) {
            //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
            lceViewImpl.showLoadingLayout(isPullRefresh);
        }
    }

    @Override
    public void showContentLayout() {
        lceViewImpl.showContentLayout();
    }

    @Override
    public void showErrorLayout() {
        if (isFirstRequest()) {
            lceViewImpl.showErrorLayout();
        }
    }

    @Override
    public void bindData(D data) {
        isFirstRequest = false;
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
