package com.caihan.scframe.rxjava;

import android.support.annotation.NonNull;

import com.caihan.scframe.framework.v1.support.mvp.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava调度工具类
 * <p>
 * 1.{@link #io_main()}线程切换:io线程->主线程<br/>
 * 2.{@link #request(RxAppCompatActivity)}网络请求绑定Rxlifecycle与1<br/>
 * 3.{@link #request(RxAppCompatActivity, BaseView, boolean)}网络请求绑定requestLoadingView与2
 *
 * @author caihan
 * @date 2018/2/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RxSchedulers {

    /**
     * 用于网络请求绑定Rxlifecycle与io_main
     *
     * @param activity
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(final RxAppCompatActivity activity) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, activity);
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle与io_main
     *
     * @param rxFragment
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(final RxFragment rxFragment) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, rxFragment);
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle与io_main
     *
     * @param v4Fragment
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(
            final com.trello.rxlifecycle2.components.support.RxFragment v4Fragment) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, v4Fragment);
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle与io_main
     *
     * @param lifecycleProvider
     * @param event
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T,E> ObservableTransformer<T, T> request(
            @NonNull final LifecycleProvider<E> lifecycleProvider, @NonNull final E event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, lifecycleProvider,event);
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(BaseView)}配合使用
     *
     * @param activity
     * @param view
     * @param isShowLoading
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(
            final RxAppCompatActivity activity, final BaseView view, final boolean isShowLoading) {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, activity)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (isShowLoading) {
                                    view.showRequestLoading();
                                } else {
                                    view.dismissRequestLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(BaseView)}配合使用
     * 默认开启Loading
     *
     * @param activity
     * @param view
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(
            final RxAppCompatActivity activity, final BaseView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, activity)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                view.showRequestLoading();
                            }
                        });
            }

        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(BaseView)}配合使用
     *
     * @param rxFragment
     * @param view
     * @param isShowLoading
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(
            final RxFragment rxFragment, final BaseView view, final boolean isShowLoading) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, rxFragment)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (isShowLoading) {
                                    view.showRequestLoading();
                                } else {
                                    view.dismissRequestLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(BaseView)}配合使用
     *
     * @param v4Fragment
     * @param view
     * @param isShowLoading
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> request(
            final com.trello.rxlifecycle2.components.support.RxFragment v4Fragment,
            final BaseView view, final boolean isShowLoading) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, v4Fragment)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (isShowLoading) {
                                    view.showRequestLoading();
                                } else {
                                    view.dismissRequestLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(BaseView)}配合使用
     *
     * @param lifecycleProvider
     * @param event
     * @param view
     * @param isShowLoading
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> ObservableTransformer<T, T> request(
            @NonNull final LifecycleProvider<E> lifecycleProvider, @NonNull final E event,
            final BaseView view, final boolean isShowLoading) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return bindLifeCycle(upstream, lifecycleProvider, event)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (isShowLoading) {
                                    view.showRequestLoading();
                                } else {
                                    view.dismissRequestLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * io线程处理事件,回调主线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 使用Rxlifecycle2管理RxJava,防止内存泄漏
     *
     * @param observable
     * @param rxAppCompatActivity
     * @param <T>
     * @return
     */
    private static <T> Observable<T> bindLifeCycle(Observable<T> observable, RxAppCompatActivity rxAppCompatActivity) {
        return observable.compose(rxAppCompatActivity.<T>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 使用Rxlifecycle2管理RxJava,防止内存泄漏
     *
     * @param observable
     * @param rxFragment
     * @param <T>
     * @return
     */
    private static <T> Observable<T> bindLifeCycle(Observable<T> observable, RxFragment rxFragment) {
        return observable.compose(rxFragment.<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 使用Rxlifecycle2管理RxJava,防止内存泄漏
     *
     * @param observable
     * @param v4Fragment
     * @param <T>
     * @return
     */
    private static <T> Observable<T> bindLifeCycle(
            Observable<T> observable, com.trello.rxlifecycle2.components.support.RxFragment v4Fragment) {
        return observable.compose(v4Fragment.<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 自定义Rxlifecycle2管理RxJava
     *
     * @param observable
     * @param lifecycleProvider
     * @param event
     * @param <T>
     * @param <E>
     * @return
     */
    private static <T, E> Observable<T> bindLifeCycle(
            Observable<T> observable, @NonNull LifecycleProvider<E> lifecycleProvider, @NonNull E event) {
        return observable.compose(lifecycleProvider.<T>bindUntilEvent(event))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
