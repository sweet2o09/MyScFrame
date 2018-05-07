/*
 * 系统: U1CityModule
 * 文件名: ParseUtil.java
 * 版权: U1CITY Corporation 2015
 * 描述: 
 * 创建人: zhengjb
 * 创建时间: 2015-9-23 上午10:33:23
 */
package com.caihan.scframe.utils.text;


import com.blankj.utilcode.util.StringUtils;

import java.text.DecimalFormat;

/**
 * 基本数据类型的解析类
 *
 * @author zhengjb
 */
public class BaseParser {
    /**
     * 标准保留两位小数形式
     */
    private static final DecimalFormat sFORMAT = new DecimalFormat("0.00");

    /**
     * 解析int类型
     * 默认值为0
     *
     * @param text 解析字段
     */

    public static int parseInt(String text) {
        return parseInt(0, text);
    }

    /**
     * 解析int类型
     *
     * @param defaultValue 默认值
     * @param text         解析字段
     */

    public static int parseInt(int defaultValue, String text) {
        int intValue = defaultValue;
        if (!StringUtils.isEmpty(text)) {
            try {
                intValue = Integer.parseInt(text);
            } catch (Exception e) {
                e.printStackTrace();
                intValue = defaultValue;
            }
        }
        return intValue;
    }

    public static float parseFloat(String text) {
        float value = 0;
        if (!StringUtils.isEmpty(text)) {
            try {
                value = Float.parseFloat(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 解析double类型
     * 默认值为0.00
     *
     * @param text 解析字段
     * @author zhengjb
     */
    public static double parseDouble(String text) {
        return parseDouble(0.00d, text);
    }

    /**
     * 解析double类型,保留两位小数
     *
     * @param text
     * @return
     */
    public static String parseDoubleDecimal(String text) {
        return DoubleUtils.format(parseDouble(0.00d, text));
    }

    /**
     * 解析double类型
     *
     * @param defaultValue 默认值
     * @param text         解析字段
     * @author zhengjb
     */
    public static double parseDouble(double defaultValue, String text) {
        double doubleValue = defaultValue;

        if (!StringUtils.isEmpty(text)) {
            try {
                doubleValue = Double.parseDouble(text);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                doubleValue = defaultValue;
            }
        }
        return doubleValue;
    }

    /**
     * 保留两位小数的String类型
     *
     * @param text
     * @return
     */
    public static String parseDoubleString(String text) {
        String doubleValue = "0.00";

        if (!StringUtils.isEmpty(text)) {
            try {
                doubleValue = sFORMAT.format(Double.parseDouble(text));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return doubleValue;
    }


    /**
     * 解析long类型
     *
     * @param defaultValue 默认值
     * @param text         解析字段
     * @author zhengjb
     */
    public static long parseLong(long defaultValue, String text) {
        long longValue = defaultValue;

        if (!StringUtils.isEmpty(text)) {
            try {
                longValue = Long.parseLong(text);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                longValue = defaultValue;
            }
        }
        return longValue;
    }

    /**
     * 解析double类型
     * 默认值为0
     *
     * @param text 解析字段
     * @author zhengjb
     */
    public static long parseLong(String text) {
        return parseLong(0l, text);
    }

}
