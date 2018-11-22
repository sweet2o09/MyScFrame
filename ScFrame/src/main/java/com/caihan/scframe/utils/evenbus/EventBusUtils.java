package com.caihan.scframe.utils.evenbus;

/**
 * EventBus工具类
 *
 * @author caihan
 * @date 2018/11/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class EventBusUtils {

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
     *
     * @param event
     */
    public static <T extends ScEvent> void post(T event) {
        BusProvider.eventBusPost(event);
    }

    /**
     * 分发粘性事件
     *
     * @param event
     */
    public static <T extends ScEventSticky> void postSticky(T event) {
        BusProvider.eventBusPostSticky(event);
    }

    /**
     * 取消事件传递,收到消息后加上这条的话,消息就不会继续传递
     * 注意:线程Mode必须是POSTING的
     *
     * @param event
     */
    public static <T extends ScEvent> void cancelEventDelivery(T event) {
        BusProvider.cancelEventDelivery(event);
    }

    /**
     * 粘性事件执行后必须手动删除
     *
     * @param event
     */
    public static <T extends ScEventSticky> void removeStickyEvent(T event) {
        BusProvider.removeStickyEvent(event);
    }
}
