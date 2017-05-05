package com.caihan.myscframe.api.retrofit2.observer;

import android.content.Context;

import com.caihan.myscframe.BaseActivity;
import com.caihan.myscframe.api.retrofit2.exception.ExceptionHandle;
import com.caihan.scframe.utilcode.util.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by caihan on 2017/4/4.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    protected Disposable mDisposable;
    protected Context mContext;

    public BaseObserver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onNext(T value) {
        resSuccess(value);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            resError((ExceptionHandle.ResponeThrowable) e);
        } else {
            resError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
        onFinished();
    }

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.d("建立连接");
        mDisposable = d;
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).addDisposable(d);
        }
        showDialog();

    }

    /**
     * 网络请求完成,error不会调用到
     */
    @Override
    public void onComplete() {
        LogUtils.d("请求完毕");
        onFinished();
    }

    /**
     * 成功或失败到最后都会调用
     */
    protected void onFinished() {
        if (mContext instanceof BaseActivity && mDisposable != null) {
            ((BaseActivity) mContext).reDisposable(mDisposable);
            mDisposable.dispose();
        }
        hideDialog();
    }

    /**
     * 显示Dialog
     */
    protected abstract void showDialog();

    /**
     * 隐藏Dialog
     */
    protected abstract void hideDialog();

    /**
     * 成功返回结果时被调用
     *
     * @param result
     */
    public abstract void resSuccess(T result);

    /**
     * 返回错误提示
     *
     * @param e
     */
    public abstract void resError(ExceptionHandle.ResponeThrowable e);
}
