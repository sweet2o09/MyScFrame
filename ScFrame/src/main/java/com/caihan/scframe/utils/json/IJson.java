package com.caihan.scframe.utils.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json转换接口
 * @author caihan
 * @date 2018/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IJson {

    /**
     * 将对象转换成json字符串
     *
     * @param t 转化对象
     * @return json字符串
     */
    <T> String toJson(T t);

    /**
     * 将对象转换成json字符串
     *
     * @param t 转化对象
     * @return json字符串
     */
    <T> String toJson(T t, Type type);


    /**
     * 将json字符串转换成对象
     *
     * @param json  字符串
     * @param clazz 类
     * @return 转换得到的对象
     */
    <T> T fromJson(String json, Class<? extends T> clazz);

    /**
     * 将json字符串转换成对象
     *
     * @param json 字符串
     * @return 转换得到的对象
     */
    <T> T fromJson(String json, Type type);

    /**
     * 将对象列表转换成json字符串
     *
     * @param array 对象列表
     * @return json字符串
     */
    <T> String listToJson(List<T> array);

    /**
     * 将json字符串转换成对象列表
     *
     * @param json  字符串
     * @param clazz 类
     * @return 对象列表
     */
    <T> List<T> listFromJson(String json, Class<? extends T> clazz);
}
