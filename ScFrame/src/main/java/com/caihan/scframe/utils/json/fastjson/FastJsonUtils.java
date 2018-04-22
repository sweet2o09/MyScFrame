package com.caihan.scframe.utils.json.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caihan.scframe.utils.json.IJson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * FastJson解析实例
 *
 * @author caihan
 * @date 2018/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class FastJsonUtils implements IJson {
    @Override
    public <T> String toJson(T t) {
        return JSONObject.toJSONString(t);
    }

    @Override
    public <T> String toJson(T t, Type type) {
        return JSONObject.toJSONString(t);
    }

    @Override
    public <T> T fromJson(String json, Class<? extends T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        return JSONObject.parseObject(json, type);
    }

    @Override
    public <T> String listToJson(List<T> array) {
        return JSONObject.toJSONString(array);
    }

    @Override
    public <T> List<T> listFromJson(String json, Class<? extends T> clazz) {
        return (List<T>) JSONArray.parseArray(json, clazz);
    }
}
