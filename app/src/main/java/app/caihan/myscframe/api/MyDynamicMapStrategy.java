package app.caihan.myscframe.api;

import com.caihan.scframe.api.interceptor.DynamicMapStrategy;
import com.zhouyou.http.utils.HttpLog;

import java.util.TreeMap;

/**
 * 签名拦截器
 * 用于在发送请求之前添加一些特定参数或进行签名操作
 *
 * @author caihan
 * @date 2019/3/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MyDynamicMapStrategy implements DynamicMapStrategy {
    @Override
    public TreeMap<String, String> getNewDynamicMap(TreeMap<String, String> dynamicMap) {
        dynamicMap.put("app", "1");
        dynamicMap.put("os", "Android");
        HttpLog.i("MyDynamicMapStrategy dynamicMap:" + dynamicMap.toString());
        return dynamicMap;
    }
}
