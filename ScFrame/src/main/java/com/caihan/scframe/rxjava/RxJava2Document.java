package com.caihan.scframe.rxjava;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 该类是为了让开发者更好的使用RxJava<br/>
 * RxJava实际上就是一种观察者模式<br/>
 * <p>
 * 角色划分:<br/>
 * 角色一:被观察者<br/>
 * 角色二:观察者<br/>
 * 角色三:订阅<br/>
 * 角色四:事件<br/>
 * <p>
 * 基本步骤:<br/>
 * 第一步:创建被观察者({@link Observable})以及事件({@link Observable#create(ObservableOnSubscribe)})<br/>
 * 第二步:创建观察者({@link Observer})并定义响应事件的行为({@link Observer#onNext(Object)})<br/>
 * 第三步:通过订阅({@link Observable#subscribe()})连接观察者和被观察者<br/>
 * <p>
 * 创建操作符(13种,个别类型有多种重载方式,这里就不一一举例):<br/>
 * 1.基础创建{@link Observable#create(ObservableOnSubscribe)}<br/>
 * <p>
 * 2.快速创建并发送事件{@link Observable#just(Object)},
 * eg:{@link #justUse()},
 * 快速创建1个被观察者对象并直接发送传入的事件(最多只能发送10个)<br/>
 * <p>
 * 3.快速创建并发送事件{@link Observable#fromArray(Object[])},
 * eg:{@link #fromArrayUse(String...)},
 * 快速创建1个被观察者对象并直接发送传入的事件(发送10个以上),遍历数组元素<br/>
 * <p>
 * 4.快速创建并发送事件{@link Observable#fromIterable(Iterable)},
 * eg:{@link #fromIterableUse(ArrayList)},
 * 快速创建1个被观察者对象并直接发送传入的事件(发送10个以上),遍历集合元素<br/>
 * <p>
 * 5.快速创建并发送事件{@link Observable#never()},
 * 不发送任何事件,即观察者接收后什么都不调用<br/>
 * <p>
 * 6.快速创建并发送事件{@link Observable#empty()},
 * 仅发送Complete事件，直接通知完成,即观察者接收后会直接调用onCompleted()<br/>
 * <p>
 * 7.快速创建并发送事件{@link Observable#error(Throwable)},
 * 仅发送Error事件，直接通知异常,可自定义异常,即观察者接收后会直接调用onError()<br/>
 * <p>
 * 8.延迟创建{@link Observable#defer(Callable)},
 * eg:{@link #deferUse()},
 * 直到有观察者Observer订阅时,才动态创建被观察者Observable并且发送事件,这样可以保证Observable数据是最新<br/>
 * <p>
 * 9.延迟创建{@link Observable#timer(long, TimeUnit)},
 * eg:{@link #timerUse()},
 * 延迟指定时间后，调用一次 onNext()<br/>
 * <p>
 * 10.延迟创建{@link Observable#interval(long, TimeUnit)},
 * eg:{@link #intervalUse()},
 * 每隔指定时间就发送事件<br/>
 * <p>
 * 11.延迟创建{@link Observable#intervalRange(long, long, long, long, TimeUnit)},
 * eg:{@link #intervalRangeUse()},
 * 每隔指定时间就发送事件,可指定发送的数据的数量<br/>
 * <p>
 * 12.延迟创建{@link Observable#range(int, int)},
 * eg:{@link #rangeUse()},
 * 连续发送事件序列,可指定范围<br/>
 * <p>
 * 13.延迟创建{@link Observable#rangeLong(long, long)},
 * 与range类似<br/>
 * <p>
 * 变换操作符:
 * 1.{@link Observable#map(Function)},
 * eg:{@link #mapUse()},
 * 数据类型转换<br/>
 * <p>
 * 2.{@link Observable#flatMap(Function)}
 * eg:{@link #flatMapUse(Context)},
 * 无序的将被观察者发送的整个事件序列进行变换<br/>
 * <p>
 * 3.{@link Observable#concatMap(Function)}
 * eg:{@link #concatMapUse()},
 * 有序的将被观察者发送的整个事件序列进行变换<br/>
 * <p>
 * 4.{@link Observable#buffer(int)}
 * eg:{@link #bufferUse()},
 * 定期从被观察者Obervable需要发送的事件中获取一定数量的事件并放到缓存区中,最终发送<br/>
 * <p>
 * 组合/合并操作符:
 * 这里不多做说明,常用的有{@link #zipUse()}
 *
 * 线程切换用法:{@link #threadUse()}
 *
 *
 * @author caihan
 * @date 2018/1/16
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RxJava2Document {
    private static final String TAG = "RxJava2";

    /**
     * 基础用法
     * observable就是一个被订阅者
     */
    private void defaultUse() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();//第一次才有效,表示结束
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    /**
     * 链路基础用法
     * 可以使用{@link Disposable#dispose()}切断观察者与被观察者之间的连接
     */
    private void Observable() {
        Observable.create(
                new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();//第一次才有效,表示结束
                    }
                })
                .subscribeOn(Schedulers.newThread())//在异步线程里执行subscribe操作,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//在主线程中处理结果,可以设置多次
                .subscribe(new Observer<Object>() {
                    private Disposable mDisposable;
                    private int i;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                        //对Disposable类变量赋值
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Object value) {
                        Log.d(TAG, "对Next事件" + value + "作出响应");
                        i++;
                        if (i == 2) {
                            //切断下游,不再接收
                            //调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
                            mDisposable.dispose();
                            Log.d(TAG, "已经切断了连接：" + mDisposable.isDisposed());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 线程切换用法
     * <p>
     * {@link Schedulers#io()}代表io操作的线程, 应用于网络,读写文件等io密集型的操作<br/>
     * {@link Schedulers#computation()}代表CPU计算密集型的操作, 应用于需要大量计算的操作<br/>
     * {@link Schedulers#newThread()}代表一个常规的新线程,应用于耗时操作<br/>
     * {@link AndroidSchedulers#mainThread()}代表Android的主线程,应用于操作UI
     * <p>
     * {@link Observable#subscribeOn(Scheduler)}}指定被观察者与生产事件的线程,第一次设置有效,后面都无效<br/>
     * {@link Observable#observeOn(Scheduler)}}指定观察者,接收和响应事件的线程,每次都有效<br/>
     */
    private void threadUse() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())//指定被观察者与生产事件的线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定观察者,接收和响应事件的线程
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " +
                                Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(io), current thread is : " +
                                Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);
    }

    /**
     * 数据返回用法
     * <p>
     * Observable中不要调用subscribe方法
     *
     * @return
     */
    private Observable<List<String>> returnUse() {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                String cursor = null;
                int p = 1;
                int max = 10;
                List<String> result = new ArrayList<>();
                for (int i = 0; i < max; i++) {
                    cursor = "TABLE_NAME" + p;
                    result.add(cursor);
                    p++;
                }
                emitter.onNext(result);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * map操作符用法
     * <p>
     * 这里是把上游的Integer类转换成String发给下游
     * map操作符是有序的
     */
    private void mapUse() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "This is result " + integer;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, s);
                    }
                });
    }


    /**
     * flatMap操作符用法
     * <p>
     * 这里是把上游的Integer类转换成String发给下游
     * flatMap操作符是无序的,因为他中途又创建了多个上游
     */
    private void flatMapUse(final Context context) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });

        /**
         * 看它如何把注册跟登录连接起来
         *
         * 这里请把<String>看成<RegisterResponse>注册请求返回参数
         * 这里请把<Integer>看成<LoginResponse>登录请求返回参数
         */
        Observable<String> registerRequest = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(TAG);
                emitter.onComplete();
            }
        });

        Observable<Integer> loginRequest = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onComplete();
            }
        });


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                //发起注册请求
                emitter.onNext(TAG);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())//在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求注册结果
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String registerResponse) throws Exception {
                        //先根据注册的响应结果去做一些操作
                    }
                })
                //回到IO线程去发起登录请求
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String registerResponse) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                                emitter.onNext(1);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                //回到主线程去处理请求登录的结果
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer loginResponse) throws Exception {
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * concatMap操作符用法
     * <p>
     * 这里是把上游的Integer类转换成String发给下游
     * concatMap操作符是有序的flatMap
     */
    private void concatMapUse() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    private void bufferUse() {
        // 被观察者需要发送5个数字
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        //
                        Log.d(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            Log.d(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * zip操作符用法
     * <p>
     * 在多个上游中各取1个参数合并成新的参数后发送给下游
     * zip是有序的
     * <p>
     * 需要注意的是,下游接收的个数是根据发送事件最小的上游决定的,这里observable1发送4个,observable2发送3个
     * 所以下游只会接收到3个,但不表示observable1的第四个数据不会发送
     */
    private void zipUse() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Thread.sleep(1000);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Thread.sleep(1000);

                Log.d(TAG, "emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");

                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d(TAG, "emit C");
                emitter.onNext("C");
                Thread.sleep(1000);

                Log.d(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });

        /**
         * 使用zip对2组网络请求数据进行打包
         *
         * 这里请把<String>看成<UserBaseInfoResponse>注册请求返回参数
         * 这里请把<Integer>看成<UserExtraInfoResponse>登录请求返回参数
         */
        Observable<String> UserBaseInfo = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<Integer> UserExtraInfo = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(
                UserBaseInfo, UserExtraInfo,
                new BiFunction<String, Integer, UserInfo>() {
                    @Override
                    public UserInfo apply(String baseInfo, Integer extraInfo) throws Exception {
                        return new UserInfo(baseInfo, extraInfo);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        //do something;
                    }
                });
    }

    class UserInfo {
        String msg;
        int id;

        public UserInfo(String msg, int id) {
            this.msg = msg;
            this.id = id;
        }
    }

    /**
     * Flowable的基础用法
     * BackpressureStrategy参数使用来选择背压,也就是出现上下游流速不均衡的时候应该怎么处理的办法
     * BackpressureStrategy.ERROR:这种方式会在出现上下游流速不均衡的时候直接抛出一个异常,这个异常就是著名的MissingBackpressureException
     */
    private void flowableDefaultUse() {
        //创建一个上游Flowable
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "current requested: " + emitter.requested());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR) //增加了一个参数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //创建一个下游Subscriber
        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            public Subscription mSubscription;

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                mSubscription = s;
                /**
                 *Subscription.request()该方法是下游告知上游我能处理多少个参数,上游根据这个方法里的参数来
                 * 发放事件给下游,这里可以把它看成是下游处理事件的能力
                 * 这是Flowable在设计的时候采用了一种新的思路也就是"响应式拉取"的方式来更好的解决上下游流速不均衡的问题
                 * 如果不加这句话的话,上游会认为下游没有处理事件的能力,就会抛出MissingBackpressureException
                 */
                mSubscription.request(Long.MAX_VALUE);  //注意这句代码,
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        upstream.subscribe(downstream);
    }


    /**
     * just操作符
     * <p>
     * 快速创建1个被观察者对象并直接发送传入的事件(最多只能发送10个参数)
     */
    private void justUse() {
        // 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4)
                // 至此，一个Observable对象创建完毕，以下步骤仅为展示一个完整demo，可以忽略
                // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                // 3. 创建观察者 & 定义响应事件的行为
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    /**
     * interval操作符的用法,每隔一段时间就发送一个对象
     */
    private void intervalUse() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Flowable.interval(3, 1, TimeUnit.SECONDS)
                .onBackpressureDrop()  //加上背压策略
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        mSubscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long integer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 每隔指定时间就发送事件,可指定发送的数据的数量
     */
    private void intervalRangeUse() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                        // 默认最先调用复写的 onSubscribe（）
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    /**
     * fromIterable的用法,传入list或者map,它会遍历每个元素
     *
     * @param list
     */
    private void fromIterableUse(ArrayList<String> list) {
        Flowable.fromIterable(list).subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String entry) throws Exception {
                        entry.toString();
                    }
                });

        Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String entry) throws Exception {
                        entry.toString();
                    }
                });
    }

    /**
     * fromIterable操作符的用法,传入list或者map,它会遍历每个元素
     *
     * @param map
     */
    private void fromIterableUse(Map<String, Integer> map) {
        Flowable.fromIterable(map.entrySet()).subscribe(
                new Consumer<Map.Entry<String, Integer>>() {
                    @Override
                    public void accept(@NonNull Map.Entry<String, Integer> entry) throws Exception {
                        entry.getKey().toString();
                        entry.getValue().toString();
                    }
                });

        Observable.fromIterable(map.entrySet()).subscribe(
                new Consumer<Map.Entry<String, Integer>>() {
                    @Override
                    public void accept(@NonNull Map.Entry<String, Integer> entry) throws Exception {
                        entry.getKey().toString();
                        entry.getValue().toString();
                    }
                });
    }

    /**
     * fromArray操作符的用法,传入一个数组,遍历对象
     *
     * @param strings
     */
    private void fromArrayUse(String... strings) {
        Flowable.fromArray(strings).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                s.toString();
            }
        });
        Observable.fromArray(strings).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                s.toString();
            }
        });
    }

    /**
     * filter操作符用法
     * 这个操作符可以作为数据筛选器，帮你过滤不想要的数据。
     * 下面的例子当数据>3才能放过，因此输出4 5
     */
    private void filterUse() {
        Observable.fromArray(1, 2, -3, 4, 5)
                .filter(new Predicate<Integer>() {

                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer > 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer s) throws Exception {
                        s.toString();
                    }
                });

        /**
         * 下面的例子当数据开头包含"https"字符的时候才能放过
         */
        ArrayList<String> list = new ArrayList<>();
        Observable.fromIterable(list)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s.startsWith("https");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String entry) throws Exception {
                        entry.toString();
                    }
                });
    }


    Integer i = 10;

    /**
     * 直到有观察者Observer订阅时,才动态创建被观察者Observable并且发送事件,
     * 这样可以保证Observable数据是最新
     */
    private void deferUse() {
        //1. 第1次对i赋值
        // 2. 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        //2. 第2次对i赋值
        i = 15;
        //3. 观察者开始订阅
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                //这里接收到的i=15
                Log.d(TAG, "接收到的整数是" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * take操作符用法
     * 此操作符用于指定想要的数据数量
     * 下面的例子最终只输出1和2
     */
    private void takeUse() {
        Observable.fromArray(1, 2, -3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {

                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                    }
                });
    }

    /**
     * skip操作符用法
     * 此操作符用于跳过指定数量的数据
     * 下面的例子最终只输出-3,4,5
     */
    private void skipUse() {
        Observable.fromArray(1, 2, -3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {

                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                    }
                });
    }

    /**
     * range操作符用法
     * 此方法可以发射一个指定范围的数
     * 会直接输出2 3 4 5
     */
    private void rangeUse() {
        Observable.range(2, 5).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(@NonNull Integer integer) throws Exception {

            }
        });
    }

    /**
     * timer操作符用法
     * 创建一个在指定延迟时间后发射一条数据(固定值:0)的Observable对象，可用来做定时操作。
     */
    private void timerUse() {
        //3秒后会输出0
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {

                    @Override
                    public void accept(@NonNull Long integer) throws Exception {

                    }
                });
    }

    /**
     * doXXX相关操作符
     */
    private void doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doFinally: ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * onErrorReturn/onErrorResumeNext/onExceptionResumeNext操作符,捕获异常
     */
    private void onErrorReturnUse() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        // 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());

                        return 666;
                        // 发生错误事件后，发送一个"666"事件，最终正常结束
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                        //onErrorReturn后依然会调用该方法,这边会收到value = 666的事件
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {

                        // 1. 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );

                        // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11,22);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                        //onErrorResumeNext后依然会调用该方法,这边会收到value = 11和22的事件
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
            }
        })
                .onExceptionResumeNext(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(11);
                        observer.onNext(22);
                        observer.onComplete();
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                        //onExceptionResumeNext后依然会调用该方法,这边会收到value = 11和22的事件
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
