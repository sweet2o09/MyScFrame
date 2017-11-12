package com.caihan.scframe.utils.evenbus;

/**
 * Created by caihan on 2017/8/29.
 * EventBus 定义粘性事件EventSticky
 */
public class EventSticky<T> {
    private String code;
    private T data;

    public EventSticky(String code) {
        this.code = code;
    }

    public EventSticky(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
