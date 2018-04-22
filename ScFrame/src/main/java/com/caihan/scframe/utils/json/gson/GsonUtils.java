package com.caihan.scframe.utils.json.gson;


import com.caihan.scframe.utils.json.IJson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson解析实例
 *
 * @author caihan
 * @date 2018/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class GsonUtils implements IJson {

    private static Gson create() {
        return GsonUtils.GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new Gson();
    }

    @Override
    public <T> String toJson(T t) {
        return create().toJson(t);
    }

    @Override
    public <T> String toJson(T t, Type type) {
        return create().toJson(t, type);
    }

    @Override
    public <T> T fromJson(String json, Class<? extends T> clazz) {
        return create().fromJson(json, clazz);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        return create().fromJson(json, type);
    }

    @Override
    public <T> String listToJson(List<T> array) {
        return create().toJson(array, new TypeToken<List<T>>() {
        }.getType());
    }

    @Override
    public <T> List<T> listFromJson(String json, Class<? extends T> clazz) {
        List<T> lst = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(create().fromJson(elem, clazz));
        }
        return lst;
    }
}
