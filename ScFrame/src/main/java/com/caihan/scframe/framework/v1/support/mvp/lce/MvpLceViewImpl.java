package com.caihan.scframe.framework.v1.support.mvp.lce;

import android.view.View;

import com.caihan.scframe.R;
import com.caihan.scframe.framework.v1.support.mvp.lce.animator.DefaultLceAnimator;
import com.caihan.scframe.framework.v1.support.mvp.lce.animator.ILceAnimator;


/**
 * LCE设计->目标对象
 * <p>
 * 当不是下拉刷新的时候才去进行LCE布局的切换操作
 * <p>
 * 适用于首次开启展示或每次网络网络请求都展示
 * 如果只需要首次展示的话,首次调用{@link #showLoadingLayout(boolean)}传递true,后续都传递false,默认Loading布局就不会显示
 *
 * @author caihan
 * @date 2018/1/20
 * @e-mail 93234929@qq.com
 * 维护者
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
    public void showLoadingLayout(boolean isPullRefresh) {
        //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
        if (!isPullRefresh) {
            getLceAnimator().showLoadingLayout(this.loadingView, this.contentView, this.errorView);
        }
    }

    @Override
    public void showContentLayout() {
        getLceAnimator().showContentLayout(this.loadingView, this.contentView, this.errorView);
    }

    @Override
    public void showErrorLayout() {
        getLceAnimator().showErrorLayout(this.loadingView, this.contentView, this.errorView);
    }

    @Override
    public void bindData(D data) {

    }

    @Override
    public void loadData(boolean isPullRefresh) {

    }

    @Override
    public void showRequestLoading() {

    }

    @Override
    public void dismissRequestLoading() {

    }

    @Override
    public void onRequestError(String error) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showToast(int resId) {

    }
}
