package com.caihan.scframe.framework.support;


import com.caihan.scframe.framework.base.MvpPresenter;
import com.caihan.scframe.framework.base.MvpView;

/**
 * 作者：caihan
 * 创建时间：2017/10/24
 * 邮箱：93234929@qq.com
 * 实现功能：第二个代理->关联MVP,代理对象->ProxyMvpCallback
 * 备注：
 * 满足两个特点->标准的代理模式
 * 特点一：实现目标接口
 * 特点二：持有目标对象引用
 */
public class ProxyMvpCallback<V extends MvpView, P extends MvpPresenter<V>> implements MvpCallback<V, P> {

    //目标对象引用
    private MvpCallback<V, P> mvpCallback;

    public MvpCallback<V, P> getMvpCallback() {
        return mvpCallback;
    }

    public ProxyMvpCallback(MvpCallback<V, P> mvpCallback) {
        this.mvpCallback = mvpCallback;
    }

    @Override
    public P createPresenter() {
        //创建P层
        P presenter = getMvpCallback().getPresenter();
        if (presenter == null) {
            presenter = getMvpCallback().createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter不能够为空");
        }
        //缓存(目的：对象不需要反复创建)
        getMvpCallback().setPresenter(presenter);
        return presenter;
    }

    @Override
    public P getPresenter() {
        return getMvpCallback().getPresenter();
    }

    @Override
    public void setPresenter(P presenter) {
        getMvpCallback().setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        return getMvpCallback().getMvpView();
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
        getPresenter().detachModel();
    }

}
