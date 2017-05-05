package com.caihan.myscframe;

import android.os.Bundle;

import com.caihan.myscframe.api.IDisposableCallback;
import com.caihan.scframe.base.ScActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * Created by caihan on 2017/5/5.
 */

public class BaseActivity extends ScActivity implements IDisposableCallback {

    //RxJava事件传递容器,当不需要请求事件传递回来的时候,调用clear()
    protected ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            listCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void reDisposable(Disposable disposable) {
        if (disposable != null) {
            listCompositeDisposable.remove(disposable);
        }
    }

    @Override
    public void clearDisposable() {
        if (!listCompositeDisposable.isDisposed()) {
            listCompositeDisposable.clear();
        }
    }
}
