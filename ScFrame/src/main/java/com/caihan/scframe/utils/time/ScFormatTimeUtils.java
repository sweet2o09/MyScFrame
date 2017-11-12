package com.caihan.scframe.utils.time;


import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：用来计算显示的时间是多久之前的
 * 备注：
 */
public final class ScFormatTimeUtils {

    private ScFormatTimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis 开始时间戳
     * @return
     */
    public static String getTimeSpanByNow1(long millis) {
        return TimeUtils.getFriendlyTimeSpanByNow(millis);
    }


    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis 开始时间戳
     * @return
     */
    public static String getTimeSpanByNow2(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        long day = span / TimeConstants.DAY;
        if (day == 0) {// 今天
            long hour = span / TimeConstants.HOUR;
            if (hour <= 4) {
                return String.format("凌晨%tR", millis);
            } else if (hour > 4 && hour <= 6) {
                return String.format("早上%tR", millis);
            } else if (hour > 6 && hour <= 11) {
                return String.format("上午%tR", millis);
            } else if (hour > 11 && hour <= 13) {
                return String.format("中午%tR", millis);
            } else if (hour > 13 && hour <= 18) {
                return String.format("下午%tR", millis);
            } else if (hour > 18 && hour <= 19) {
                return String.format("傍晚%tR", millis);
            } else if (hour > 19 && hour <= 24) {
                return String.format("晚上%tR", millis);
            } else {
                return String.format("今天%tR", millis);
            }
        } else if (day == 1) {// 昨天
            return String.format("昨天%tR", millis);
        } else if (day == 2) {// 前天
            return String.format("前天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

}
