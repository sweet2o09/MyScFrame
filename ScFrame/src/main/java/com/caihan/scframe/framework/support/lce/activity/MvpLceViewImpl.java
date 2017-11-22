package com.caihan.scframe.framework.support.lce.activity;

import android.view.View;

import com.caihan.scframe.R;
import com.caihan.scframe.framework.support.lce.animator.DefaultLceAnimator;
import com.caihan.scframe.framework.support.lce.animator.ILceAnimator;


/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：LCE设计->目标对象
 * 备注：
 * 当不是下拉刷新的时候才去进行LCE布局的切换操作
 */
public class MvpLceViewImpl<D> implements MvpLceView<D> {

    private View loadingView;
    private View contentView;
    private View errorView;
    private View errorClickView;

    private ILceAnimator lceAnimator;

    public void initLceView(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView不能够为空");
        }
        if (this.loadingView == null) {
            this.loadingView = rootView.findViewById(R.id.loadingView);
        }
        if (this.contentView == null) {
            this.contentView = rootView.findViewById(R.id.contentView);
        }
        if (this.errorView == null) {
            this.errorView = rootView.findViewById(R.id.errorView);
            this.errorClickView = errorView.findViewById(R.id.errorClickView);
        }
        if (loadingView == null) {
            throw new NullPointerException("loadingView不能够为空");
        }
        if (contentView == null) {
            throw new NullPointerException("contentView不能够为空");
        }
        if (errorView == null) {
            throw new NullPointerException("errorView不能够为空");
        }
    }

    /**
     * 添加重写加载监听
     *
     * @param onClickListener
     */
    public void setOnErrorViewClickListener(View.OnClickListener onClickListener) {
        if (this.errorClickView != null) {
            this.errorClickView.setOnClickListener(onClickListener);
        } else if (this.errorView != null) {
            this.errorView.setOnClickListener(onClickListener);
        }
    }

    private ILceAnimator getLceAnimator() {
        if (lceAnimator == null) {
            lceAnimator = DefaultLceAnimator.getInstance();
        }
        return lceAnimator;
    }

    /**
     * 绑定动画执行策略
     *
     * @param lceAnimator
     */
    public void setLceAnimator(ILceAnimator lceAnimator) {
        this.lceAnimator = lceAnimator;
    }

    @Override
    public void showLoading(boolean isPullRefresh) {
        //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
        if (!isPullRefresh) {
            getLceAnimator().showLoading(this.loadingView, this.contentView, this.errorView);
        }
    }

    @Override
    public void showContent() {
        getLceAnimator().showContent(this.loadingView, this.contentView, this.errorView);
    }

    @Override
    public void showError() {
        getLceAnimator().showErrorView(this.loadingView, this.contentView, this.errorView);
    }

    @Override
    public void bindData(D data) {

    }

    @Override
    public void loadData(boolean isPullRefresh) {

    }
}
