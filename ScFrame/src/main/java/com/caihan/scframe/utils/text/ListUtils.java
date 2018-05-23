package com.caihan.scframe.utils.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * List Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class ListUtils {

    /**
     * default join separator
     **/
    public static final String DEFAULT_JOIN_SEPARATOR = ",";

    private ListUtils() {
        throw new AssertionError();
    }

    /**
     * 将数组转化为List
     *
     * @param sourceObjects 为空时返回new ArrayList()
     * @return 转化后的列表
     **/
    public static List fromArray(Object[] sourceObjects) {
        List list = new ArrayList();
        if (sourceObjects != null && sourceObjects.length > 0) {
            list = Arrays.asList(sourceObjects);
        }

        return list;
    }

    /**
     * get size of list
     * <p>
     * <pre>
     * getSize(null)   =   0;
     * getSize({})     =   0;
     * getSize({1})    =   1;
     * </pre>
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or empty, return 0, else return {@link List#size()}.
     */
    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * is null or its size is 0
     * <p>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static <V> boolean isEmpty(Set<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }


}
