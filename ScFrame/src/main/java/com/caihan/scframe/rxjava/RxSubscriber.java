package com.caihan.scframe.rxjava;

import com.caihan.scframe.framework.v1.support.mvp.BaseView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Rx网络请求数据处理工具类<br/>
 * 结合{@link RxSchedulers#request(RxAppCompatActivity, BaseView)}一起使用,效果最佳
 *
 * @author caihan
 * @date 2018/2/26
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class RxSubscriber<T> implements Observer<T> {

    private boolean cancelLoading = true;
    private BaseView mBaseView;

    public RxSubscriber(BaseView baseView) {
        this.mBaseView = baseView;
    }

    public RxSubscriber(boolean cancelLoading) {
        this.cancelLoading = cancelLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        //开始采用subscribe连接
    }

    @Override
    public void onComplete() {
        //对Complete事件作出响应
//        if (cancelLoading) {
//            mBaseView.dismissRequestLoading();
//        }
    }

    @Override
    public void onError(Throwable e) {
        //对Error事件作出响应
        if (cancelLoading) {
            mBaseView.dismissRequestLoading();
        }
        if (e != null) {
            mBaseView.onRequestError(e.getMessage());
            _onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        //接收到的数据
        if (cancelLoading) {
            mBaseView.dismissRequestLoading();
        }
        _onNext(t);
    }


    public abstract void _onNext(T t);

    public abstract void _onError(Throwable error);
}
