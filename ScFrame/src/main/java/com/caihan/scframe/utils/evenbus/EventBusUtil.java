package com.caihan.scframe.utils.evenbus;

/**
 * Created by caihan on 2017/6/1.
 * EventBus工具类
 */
public class EventBusUtil {

    private static final String TAG = "EventBusUtil";

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        BusProvider.eventBusRegister(subscriber);
    }

    /**
     * 注销
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        BusProvider.eventBusUnregister(subscriber);
    }

    /**
     * 分发事件
     * @param event
     */
    public static void post(Event event) {
        BusProvider.eventBusPost(event);
    }

    /**
     * 分发粘性事件
     * @param event
     */
    public static void postSticky(Event event){
        BusProvider.eventBusPostSticky(event);
    }

    /**
     * 取消事件传递,收到消息后加上这条的话,消息就不会继续传递
     * 注意:线程Mode必须是POSTING的
     *
     * @param event
     */
    public static void cancelEventDelivery(Object event) {
        BusProvider.cancelEventDelivery(event);
    }

    /**
     * 粘性事件执行后必须手动删除
     *
     * @param msg
     */
    public static void removeStickyEvent(Object event) {
        BusProvider.removeStickyEvent(event);
    }
}
