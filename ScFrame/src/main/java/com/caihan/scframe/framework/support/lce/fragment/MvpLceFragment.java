package com.caihan.scframe.framework.support.lce.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.support.fragment.MvpFragment;
import com.caihan.scframe.framework.support.lce.activity.MvpLceView;
import com.caihan.scframe.framework.support.lce.activity.MvpLceViewImpl;
import com.caihan.scframe.framework.support.lce.animator.ILceAnimator;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：
 * 备注：
 * MVP->LCE设计模式,Fragment基类
 * 实现Loading,Content,Error布局之间的切换以及数据D的绑定
 */
public abstract class MvpLceFragment<D, V extends MvpLceView<D>, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> implements MvpLceView<D> {
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
    }

    private void initLceView(View v) {
        lceViewImpl.initLceView(v);
        lceViewImpl.setOnErrorViewClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onErrorClick();
            }
        });
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
        isFirstRequest = true;
        //数据变更,关闭智能刷新
        autoRefreshSuccess();
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
