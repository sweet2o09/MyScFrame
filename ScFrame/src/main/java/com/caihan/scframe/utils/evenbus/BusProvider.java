package com.caihan.scframe.utils.evenbus;

import android.util.Log;

import com.caihan.scframe.utils.evenbus.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by caihan on 2016/10/31.
 * EventBus是一个针对Android为了松耦合的基于事件发布/订阅模式（观察者模式）的开源库。
 */
public class BusProvider {

    static EventBus sEventBus = null;

    public static EventBus getInstance() {
        if (sEventBus == null){
            // 只设置一次，并且要在我们第一次使用EventBus之前进行设置
            // 启用EventBus3.0加速功能
            EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
            // 这样每次获取到默认实例，都是使用Subscriber Index的了，代码得到了精简。
            sEventBus = EventBus.getDefault();
        }
        return sEventBus;
    }

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void eventBusRegister(Object subscriber) {
        getInstance().register(subscriber);
    }

    /**
     * 解除注册
     *
     * @param subscriber
     */
    public static void eventBusUnregister(Object subscriber) {
        getInstance().unregister(subscriber);
    }

    /**
     * 取消事件传递,收到消息后加上这条的话,消息就不会继续传递
     * 注意:线程Mode必须是POSTING的
     *
     * @param event
     */
    public static void cancelEventDelivery(Object event) {
        getInstance().cancelEventDelivery(event);
    }

    /**
     * 分发事件
     *
     * @param event
     */
    public static void eventBusPost(Object event) {
        getInstance().post(event);
    }

    /**
     * 分发粘性事件
     *
     * @param event
     */
    public static void eventBusPostSticky(Object event) {
        getInstance().postSticky(event);
    }

    /**
     * 粘性事件执行后必须手动清除
     *
     * @param event
     */
    public static void removeStickyEvent(Object event) {
        getInstance().removeStickyEvent(event);
    }

    /*==================以下的方法只是模板,请根据需要添加到相对应的界面中===========================*/
    /*=========================================================================================*/

    /**
     * 不同组件之间传递数据
     * 1.POSTING:
     * 同步模式，顾名思义只中方式就是接收事件方法的线程和发送事件方法的线程一致，
     * 如果发送事件是在主线程中，那么接收事件也是在主线程中。
     * 如果发送事件的是在子线程，那么那么接收事件也会发送事件的子线程执行。总之会保持一致。
     * <p>
     * 2.MAIN:
     * 主线程模式，无论发送事件是在那个线程发送，接收事件一定会在主线程中执行。
     * 这样刚好就解决了只能在主线程中更新UI的问题。
     * <p>
     * 3.BACKGROUND:
     * 后台线程模式，如果发送事件的是在主线程中发送，接收事件就会在新建一个子线程中执行。
     * 发送事件是在子线程中执行，接收事件就会发发送事件的子线程中执行。这种模式适合处理耗时任务。
     * <p>
     * 4.ASYNC:
     * 新线程模式，无论发送事件是在何种线程执行，接收事件一定会新建一个子线程去接收。
     *
     * @param onBusObject
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBus(Object onBusObject) {
        Log.v("bus", "main" + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusMain(Object onBusObject) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBusPosting(Object onBusObject) {
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBusBackground(Object onBusObject) {
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onBusAsync(Object onBusObject) {
    }


    /**
     * EventBus可以通过设置每个接收事件方法的优先级@Subscribe(priority = 1)开控制接收
     * priority的值越大，接收顺序就越靠前。
     * 如果指定俩个方法的priority的值为1和2，那么priority为2的先接收到，为1的后接收到，
     * 还可以在方法内通过cancelEventDelivery()截断事件的传递。
     * <p>
     * 在取消事件传递的时候有一个注意点，在取消事件传递的方法的线程Mode必须是**POSTING**的，
     * 不然会报event handlers may only abort the incoming event的异常
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onBus1(String msg) {
    }


    /**
     * EventBus支持粘性事件,粘性事件就是在发送了事件之后，再订阅事件，而不是在发送事件之前订阅，
     * 事件接收方法也能收到，通过@Subscribe(sticky = true)去指定，发送事件必须通过postSticky发送。
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBus2(String msg) {
    }
}
