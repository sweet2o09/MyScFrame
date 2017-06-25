package com.caihan.scframe.utils.evenbus;

/**
 * Created by caihan on 2017/6/1.
 * EventBus 定义事件Event
 */
public class Event<T> {
    private String code;
    private T data;

    public Event(String code) {
        this.code = code;
    }

    public Event(String code, T data) {
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
