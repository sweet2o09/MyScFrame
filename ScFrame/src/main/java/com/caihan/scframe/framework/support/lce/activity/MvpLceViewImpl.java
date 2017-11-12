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

    private ILceAnimator lceAnimator;

    private ILceAnimator getLceAnimator(){
        if (this.lceAnimator == null){
            this.lceAnimator = new DefaultLceAnimator();
        }
        return this.lceAnimator;
    }

    public void setLceAnimator(ILceAnimator lceAnimator) {
        this.lceAnimator = lceAnimator;
    }

    public void initView(View rootView){
        if (rootView == null){
            throw new NullPointerException("rootView不能够为空");
        }
        if (this.loadingView == null){
            this.loadingView = rootView.findViewById(R.id.loadingView);
        }
        if (this.contentView == null){
            this.contentView = rootView.findViewById(R.id.contentView);
        }
        if (this.errorView == null){
            this.errorView = rootView.findViewById(R.id.errorView);
        }
        if (loadingView == null){
            throw new NullPointerException("loadingView不能够为空");
        }
        if (contentView == null){
            throw new NullPointerException("contentView不能够为空");
        }
        if (errorView == null){
            throw new NullPointerException("errorView不能够为空");
        }
    }

    @Override
    public void showLoading(boolean isPullRefresh) {
        if (!isPullRefresh){
            getLceAnimator().showLoadingView(this.loadingView, this.contentView, this.errorView);
        }
    }

    @Override
    public void showContent(boolean isPullRefresh) {
        if (!isPullRefresh){
            getLceAnimator().showContentView(this.loadingView, this.contentView, this.errorView);
        }
    }

    @Override
    public void showError(boolean isPullRefresh) {
        if (!isPullRefresh){
            getLceAnimator().showErrorView(this.loadingView, this.contentView, this.errorView);
        }
    }

    @Override
    public void bindData(D data, boolean isPullRefresh) {

    }

    @Override
    public void loadData(boolean isPullRefresh) {

    }
}
