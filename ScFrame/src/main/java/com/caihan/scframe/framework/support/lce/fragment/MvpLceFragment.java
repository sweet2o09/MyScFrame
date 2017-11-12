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
 */
public class MvpLceFragment<D, V extends MvpLceView<D>, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> implements MvpLceView<D> {

    private MvpLceViewImpl<D> mvpLceView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpLceView().initView(view);
    }

    private MvpLceViewImpl<D> getMvpLceView() {
        if (this.mvpLceView == null) {
            this.mvpLceView = new MvpLceViewImpl<D>();
        }
        return mvpLceView;
    }

    public void setLceAnimator(ILceAnimator lceAnimator) {
        this.mvpLceView.setLceAnimator(lceAnimator);
    }

    @Override
    public void showLoading(boolean isPullRefresh) {
        getMvpLceView().showLoading(isPullRefresh);
    }

    @Override
    public void showContent(boolean isPullRefresh) {
        getMvpLceView().showContent(isPullRefresh);
    }

    @Override
    public void showError(boolean isPullRefresh) {
        getMvpLceView().showError(isPullRefresh);
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
