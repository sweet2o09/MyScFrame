package com.caihan.scframe.api.interceptor;

import java.util.TreeMap;

/**
 * CustomSignInterceptor 的自定义策略
 *
 * @author caihan
 * @date 2019/3/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface DynamicMapStrategy {

    TreeMap<String, String> getNewDynamicMap(TreeMap<String, String> dynamicMap);
}
