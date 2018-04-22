package com.caihan.scframe.framework.v1.support.mvp;


import com.caihan.scframe.framework.v1.support.MvpPresenter;
import com.caihan.scframe.framework.v1.support.MvpView;

/**
 * 具体的代理对象－－需要持有目标接口实例
 *
 * @author caihan
 * @date 2018/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ProxyMvpCallback<V extends MvpView, P extends MvpPresenter<V>>
        implements MvpCallback<V, P> {

    //目标对象引用
    private MvpCallback<V, P> mvpCallback;

    public ProxyMvpCallback(MvpCallback<V, P> mvpCallback) {
        this.mvpCallback = mvpCallback;
    }


    @Override
    public P createPresenter() {
        //创建P层
        P presenter = mvpCallback.getPresenter();
        if (presenter == null) {
            presenter = mvpCallback.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter不能够为空");
        }
        //绑定
        mvpCallback.setPresenter(presenter);
        return getPresenter();
    }

    @Override
    public P getPresenter() {
        P presenter = mvpCallback.getPresenter();
        if (presenter == null) {
            // 抛异常
            throw new NullPointerException("Presenter is not null!");
        }
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mvpCallback.setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        return mvpCallback.getMvpView();
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
        getPresenter().destroy();
    }

    @Override
    public void setRetainInstance(boolean retaionInstance) {

    }

    @Override
    public boolean isRetainInstance() {
        return false;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return false;
    }

}
