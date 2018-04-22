package com.caihan.scframe.rxjava;

import com.caihan.scframe.framework.v1.support.MvpView;
import com.caihan.scframe.framework.v1.support.mvp.BaseView;
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
                return new BuildFlowable(activity)
                        .bindLifeCycle(upstream);
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
                return new BuildFlowable(rxFragment)
                        .bindLifeCycle(upstream);
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
                return new BuildFlowable(v4Fragment)
                        .bindLifeCycle(upstream);
            }
        };
    }

    /**
     * 用于网络请求绑定Rxlifecycle,LoadingView与io_main
     * 请与{@link RxSubscriber#RxSubscriber(MvpView)}配合使用
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
                return new BuildFlowable(activity)
                        .bindLifeCycle(upstream)
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
     * 请与{@link RxSubscriber#RxSubscriber(MvpView)}配合使用
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
                return new BuildFlowable(activity)
                        .bindLifeCycle(upstream)
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
     * 请与{@link RxSubscriber#RxSubscriber(MvpView)}配合使用
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
                return new BuildFlowable(rxFragment)
                        .bindLifeCycle(upstream)
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
     * 请与{@link RxSubscriber#RxSubscriber(MvpView)}配合使用
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
                return new BuildFlowable(v4Fragment)
                        .bindLifeCycle(upstream)
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
     */
    private static class BuildFlowable {
        private RxAppCompatActivity mRxAppCompatActivity;
        private RxFragment mRxFragment;
        private com.trello.rxlifecycle2.components.support.RxFragment mV4Fragment;

        public BuildFlowable(RxAppCompatActivity rxAppCompatActivity) {
            mRxAppCompatActivity = rxAppCompatActivity;
        }

        public BuildFlowable(RxFragment rxFragment) {
            mRxFragment = rxFragment;
        }

        public BuildFlowable(com.trello.rxlifecycle2.components.support.RxFragment v4Fragment) {
            mV4Fragment = v4Fragment;
        }

        private <T> Observable<T> bindLifeCycle(Observable<T> observable) {
            if (mRxAppCompatActivity != null) {
                observable.compose(mRxAppCompatActivity.<T>bindUntilEvent(ActivityEvent.DESTROY));
            } else if (mRxFragment != null) {
                observable.compose(mRxFragment.<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            } else if (mV4Fragment != null) {
                observable.compose(mV4Fragment.<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            }
            return observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
