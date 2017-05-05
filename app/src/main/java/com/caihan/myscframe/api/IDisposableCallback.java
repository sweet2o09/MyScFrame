package com.caihan.myscframe.api;

import io.reactivex.disposables.Disposable;

/**
 * Created by caihan on 2017/5/5.
 * 管理RxJava事件
 */
public interface IDisposableCallback {
    /**
     * 添加Disposable
     * @param disposable
     */
    void addDisposable(Disposable disposable);

    /**
     * 移除Disposable
     * @param disposable
     */
    void reDisposable(Disposable disposable);

    /**
     * 清空所有Disposable
     */
    void clearDisposable();
}
