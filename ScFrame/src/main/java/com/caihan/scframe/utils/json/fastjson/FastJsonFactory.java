package com.caihan.scframe.utils.json.fastjson;


import com.caihan.scframe.utils.json.IJson;
import com.caihan.scframe.utils.json.IJsonFactory;

/**
 * FastJson解析工厂
 *
 * @author caihan
 * @date 2018/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class FastJsonFactory implements IJsonFactory {
    @Override
    public IJson getJson() {
        return new FastJsonUtils();
    }
}
