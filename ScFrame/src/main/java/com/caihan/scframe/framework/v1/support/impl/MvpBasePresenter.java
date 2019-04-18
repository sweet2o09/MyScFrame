package com.caihan.scframe.framework.v1.support.impl;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.utils.log.ScLog;

import java.lang.ref.WeakReference;

/**
 * MVP->P层,是个类,需要实例化
 * <p>
 * 已弱引用的形式绑定View
 * 集成分页加载功能
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> view;
    private boolean viewAttachedAtLeastOnce = false;
    protected Context mContext;

    private int pageSize = 10;
    private int indexPage = 1;

    public MvpBasePresenter(Context context) {
        mContext = context;
    }

    @UiThread
    @Override
    public void attachView(@NonNull V view) {
        this.view = new WeakReference<V>(view);
        viewAttachedAtLeastOnce = true;
    }

    @UiThread
    @NonNull
    protected V getView() {
        if (!viewAttachedAtLeastOnce) {
//            throw new IllegalStateException("View层还未绑定,请先绑定");
            ScLog.e("View层还未绑定,请先绑定");
        }
        if (view != null) {
            V realView = view.get();
            if (realView != null) {
                return realView;
            }
        }
        ScLog.e("View层已经被释放掉了!");
        return null;
//        throw new IllegalStateException("View层已经被释放掉了!");
    }

    @UiThread
    @Override
    public void detachView() {
        if (view != null) {
            view.clear();
            view = null;
        }
        viewAttachedAtLeastOnce = false;
        mContext = null;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public void resetPage() {
        indexPage = 1;
    }

    public void addPage() {
        indexPage++;
    }

    public boolean isLastPage(int totalCount){
        return totalCount <= pageSize * indexPage;
    }
}
