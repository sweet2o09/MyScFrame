package com.caihan.scframe.utils.json;

import android.support.annotation.IntRange;

import com.caihan.scframe.utils.json.fastjson.FastJsonFactory;
import com.caihan.scframe.utils.json.gson.GsonFactory;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json解析工具双重锁单例模式
 * <p>
 * 工厂模式创建解析工具,内置Gson与FastJson两种
 * <p>
 * 默认Gson
 * <p>
 * 建议一个项目中统一用一种解析方式,这样能保证代码的统一性
 *
 * @author caihan
 * @date 2018/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class JsonAnalysis implements IJson {
    //单例模式->双重检查模式
    private static final String TAG = "JsonAnalysis";
    //volatile表示去掉虚拟机优化代码,但是会消耗少许性能,可忽略
    private volatile static JsonAnalysis sInstance = null;

    public static final int PARSER_TYPE_GSON = 0;
    public static final int PARSER_TYPE_FAST_JSON = 1;
    private int jsonType = PARSER_TYPE_GSON;

    private IJson mJsonFactory = null;

    /**
     * 如果没设置{@link #jsonType}的话,默认使用GSON
     *
     * @return
     */
    public static JsonAnalysis getInstance() {
        if (sInstance == null) {
            //同步代码块
            synchronized (JsonAnalysis.class) {
                if (sInstance == null) {
                    sInstance = new JsonAnalysis();
                }
            }
        }
        return sInstance;
    }

    private JsonAnalysis() {
    }

    /**
     * 设置使用的json工具类型,mJsonFactory == null的时候有效
     *
     * @param jsonType PARSER_TYPE_GSON 使用gson
     *                 PARSER_TYPE_FAST_JSON 使用fastJson
     */
    public void setJsonType(@IntRange(from = 0, to = 1) int jsonType) {
        this.jsonType = jsonType;
    }

    private IJson getJsonFactory() {
        if (mJsonFactory == null) {
            //说明没有设置JsonType
            if (jsonType == PARSER_TYPE_GSON) {
                mJsonFactory = new GsonFactory().getJson();
            } else if (jsonType == PARSER_TYPE_FAST_JSON) {
                mJsonFactory = new FastJsonFactory().getJson();
            }
        }
        return mJsonFactory;
    }

    @Override
    public <T> String toJson(T t) {
        return getJsonFactory().toJson(t);
    }

    @Override
    public <T> String toJson(T t, Type type) {
        return getJsonFactory().toJson(t, type);
    }

    @Override
    public <T> T fromJson(String json, Class<? extends T> clazz) {
        return getJsonFactory().fromJson(json, clazz);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        return getJsonFactory().fromJson(json, type);
    }

    @Override
    public <T> String listToJson(List<T> array) {
        return getJsonFactory().listToJson(array);
    }

    @Override
    public <T> List<T> listFromJson(String json, Class<? extends T> clazz) {
        return getJsonFactory().listFromJson(json, clazz);
    }
}