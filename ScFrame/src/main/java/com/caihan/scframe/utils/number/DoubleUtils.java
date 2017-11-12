package com.caihan.scframe.utils.number;

import com.blankj.utilcode.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 作者：caihan
 * 创建时间：2017/10/5
 * 邮箱：93234929@qq.com
 * 实现功能：处理数字,保留N位小数,不足补零
 * 备注：方法中默认都是保留2位小数,采用BigDecimal.ROUND_DOWN模式
 * <p>
 * BigDecimal 8种舍入方式
 * <p>
 * <li>{@link BigDecimal#ROUND_UP}:
 * 在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_DOWN}:
 * 在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_CEILING}:
 * 如果BigDecimal为正,则舍入行为与ROUND_UP相同;如果为负,则舍入行为与ROUND_DOWN相同</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_FLOOR}:
 * 如果BigDecimal为正,则舍入行为与ROUND_DOWN相同;如果为负,则舍入行为与ROUND_UP相同</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_HALF_UP}:
 * 如果舍弃部分>= 0.5,则舍入行为与ROUND_UP相同;否则舍入行为与ROUND_DOWN相同(四舍五入)</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_HALF_DOWN}:
 * 如果舍弃部分> 0.5,则舍入行为与ROUND_UP相同;否则舍入行为与ROUND_DOWN相同(五舍六入)</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_HALF_EVEN}:
 * 如果舍弃部分左边的数字为奇数,则舍入行为与ROUND_HALF_UP相同;如果为偶数,则舍入行为与ROUND_HALF_DOWN相同</li>
 * <p>
 * <li>{@link BigDecimal#ROUND_UNNECESSARY}:
 * 断言请求的操作具有精确的结果，因此不需要舍入</li>
 */

public class DoubleUtils {

    //标准保留两位小数形式
    private static final DecimalFormat sFORMAT = new DecimalFormat("0.00");
    //金额样式保留两位小数形式
    private static final DecimalFormat sMONEY = new DecimalFormat("#,###,##0.00");
    //金额样式取整形式
    private static final DecimalFormat sMONEY_INT = new DecimalFormat("#,###,##0");
    //金额样式特殊符号集合
    private static final String[] sCharacter = {",", "￥", "元"};
    //普通常规使用四舍五入方式保留小数
    private static final int MODE = BigDecimal.ROUND_HALF_UP;
    //金额模式下一般使用舍去的方式保留小数,以免造成金额超过实际金额的尴尬局面
    private static final int MODE_MONEY = BigDecimal.ROUND_DOWN;

    /**
     * 将double类型的数据格式化成字符串表示,保留两位小数
     * 如果想要强制显示2位小数的话,就必须调用此方法,否则在小数点不足的情况下,系统会自动忽略
     *
     * @param d
     * @return
     */
    public static String format(double d) {
        return sFORMAT.format(d);
    }

    /**
     * 把String类型去除多余内容,转换成保留2位的数字String形式
     * 如果想要强制显示2位小数的话,就必须调用此方法,否则在小数点不足的情况下,系统会自动忽略
     *
     * @param string
     * @return
     */
    public static String format(String string) {
        String s = delCharacter(string);
        return sFORMAT.format(s);
    }

    public static String forMoney(double d) {
        return sMONEY.format(d);
    }

    public static String forMoneyInt(double d) {
        return sMONEY_INT.format(d);
    }

    public static String forMoney(String string) {
        String s = delCharacter(string);
        return sMONEY.format(s);
    }

    public static String forMoneyInt(String string) {
        String s = delCharacter(string);
        return sMONEY_INT.format(s);
    }

    /**
     * 将object类型对象转换为double类型,保留两位小数
     *
     * @param obj 要转换成double类型的对象
     * @return
     */
    public static double object2Double(Object obj) {
        return string2Double(obj.toString());
    }

    public static double object2Double(Object obj, int decimal) {
        return string2Double(obj.toString(), decimal);
    }

    /**
     * String类型转double类型,默认保留2位小数,舍去后面小数,并且会删除多于内容
     *
     * @param string
     * @return
     */
    public static double string2Double(String string) {
        String s = delCharacter(string);
        return string2Double(s, 2, MODE_MONEY);
    }

    public static double string2Double(String string, int decimal) {
        String s = delCharacter(string);
        return string2Double(s, decimal, MODE_MONEY);
    }

    /**
     * 精确的加法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double add(Double d1, Double d2) {
        BigDecimal bgd1 = new BigDecimal(Double.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 0 : d2));
        return bgd1.add(bgd2).doubleValue();
    }

    public static double add(String... strings) {
        double d = 0;
        for (int i = 0; i < strings.length; i++) {
            String stringItem = strings[i];
            if (StringUtils.isEmpty(stringItem)) {
                stringItem = "0.00";
            }
            BigDecimal bgd1 = new BigDecimal(Double.toString(d));
            Double d2 = string2Double(stringItem);
            BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 0 : d2));
            d = bgd1.add(bgd2).doubleValue();
        }
        return object2Double(d);
    }

    public static double add(Double... doubles) {
        double d = 0;
        for (int i = 0; i < doubles.length; i++) {
            BigDecimal bgd1 = new BigDecimal(Double.toString(d));
            BigDecimal bgd2 = new BigDecimal(Double.toString(doubles[i] == null ? 0 : doubles[i]));
            d = bgd1.add(bgd2).doubleValue();
        }
        return object2Double(d);
    }

    /**
     * 精确的减法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(Double d1, Double d2) {
        BigDecimal bgd1 = new BigDecimal(Double.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 0 : d2));
        return bgd1.subtract(bgd2).doubleValue();
    }

    /**
     * @param strings
     * @return
     */
    public static double sub(String... strings) {
        double d = 0;
        Double item;
        /**
         * 减法运算:被减数 - 减数 = 得数
         */
        BigDecimal bgd1;//被减数
        BigDecimal bgd2;//减数

        for (int i = 0; i < strings.length; i++) {
            String stringItem = strings[i];
            if (StringUtils.isEmpty(stringItem)) {
                stringItem = "0.00";
            }
            item = string2Double(stringItem);
            if (i == 0) {
                bgd1 = new BigDecimal(Double.toString(item == null ? 0 : item));
                bgd2 = new BigDecimal(Double.toString(d));
            } else {
                bgd1 = new BigDecimal(Double.toString(d));
                bgd2 = new BigDecimal(Double.toString(item == null ? 0 : item));
            }
            d = bgd1.subtract(bgd2).doubleValue();
        }
        return object2Double(d);
    }

    public static double sub(Double... doubles) {
        double d = 0;
        Double item;
        /**
         * 减法运算:被减数 - 减数 = 得数
         */
        BigDecimal bgd1;//被减数
        BigDecimal bgd2;//减数

        for (int i = 0; i < doubles.length; i++) {
            item = doubles[i];
            if (i == 0) {
                bgd1 = new BigDecimal(Double.toString(item == null ? 0 : item));
                bgd2 = new BigDecimal(Double.toString(d));
            } else {
                bgd1 = new BigDecimal(Double.toString(d));
                bgd2 = new BigDecimal(Double.toString(item == null ? 0 : item));
            }
            d = bgd1.subtract(bgd2).doubleValue();
        }
        return object2Double(d);
    }

    /**
     * 精确的乘法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(Double d1, Double d2) {
        BigDecimal bgd1 = new BigDecimal(Double.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 0 : d2));
        double d = bgd1.multiply(bgd2).doubleValue();
        return object2Double(d);
    }

    public static double mul(Double... doubles) {
        double d = 1;
        Double item;
        /**
         * 乘法运算:被乘数*乘数 = 得数
         */
        BigDecimal bgd1;//被乘数
        BigDecimal bgd2;//乘数

        for (int i = 0; i < doubles.length; i++) {
            item = doubles[i];
            if (i == 0) {
                bgd1 = new BigDecimal(Double.toString(item == null ? 0 : item));
                bgd2 = new BigDecimal(Double.toString(d));
            } else {
                bgd1 = new BigDecimal(Double.toString(d));
                bgd2 = new BigDecimal(Double.toString(item == null ? 0 : item));
            }
            d = bgd1.multiply(bgd2).doubleValue();
        }
        return object2Double(d);
    }

    /**
     * 精确的除法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(Double d1, Double d2) {
        BigDecimal bgd1 = new BigDecimal(Double.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 1 : d2));
        double d = bgd1.divide(bgd2, 10, MODE_MONEY).doubleValue();
        return object2Double(d);
    }

    /**
     * 精确的除法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(Double d1, Double d2, int decimal) {
        BigDecimal bgd1 = new BigDecimal(Double.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Double.toString(d2 == null ? 1 : d2));
        double d = bgd1.divide(bgd2, 10, MODE_MONEY).doubleValue();
        return object2Double(d, decimal);
    }

    public static double div(Double... doubles) {
        double d = 1;
        Double item;
        /**
         * 除法运算:被除数/除数 = 得数
         */
        BigDecimal bgd1;//被除数
        BigDecimal bgd2;//除数

        for (int i = 0; i < doubles.length; i++) {
            item = doubles[i];
            if (i == 0) {
                bgd1 = new BigDecimal(Double.toString(item == null ? 0 : item));
                bgd2 = new BigDecimal(Double.toString(d));
            } else {
                bgd1 = new BigDecimal(Double.toString(d));
                bgd2 = new BigDecimal(Double.toString(item == null ? 0 : item));
            }
            d = bgd1.divide(bgd2, 10, MODE_MONEY).doubleValue();
        }
        return object2Double(d);
    }

    /**
     * 计算收益
     *
     * @param money 存放金额
     * @param day   存放天数
     * @param apr   7日年化利率
     * @return 返回的是金额模式的String类型
     */
    public static String getIncome(String money, String day, String apr) {
        String account = incomeCalculator(
                string2Double(money),
                string2Double(day),
                string2Double(apr));
        return account;
    }

    /**
     * 收益计算器
     *
     * @param loanAmount 金额
     * @param day        天数
     * @param rates      7日年化收益率
     * @return 实际收益金额, 返回的是金额模式
     */
    private static String incomeCalculator(double loanAmount, double day, double rates) {
        double totalRevenue = 0; // 总收益
        try {
            double dailyRate = div(rates, 365.0, 100.0);//根据7日年化收益算出每日利率
            totalRevenue = mul(loanAmount, dailyRate, day);//根据金额,日化利率,时间算出实际收益金额
        } catch (Exception e) {
        }
        return forMoney(totalRevenue);
    }


    /**
     * 去除掉多余内容
     *
     * @param string
     * @return
     */
    private static String delCharacter(String string) {
        if (StringUtils.isEmpty(string)){
            return "0";
        }
        for (int i = 0; i < sCharacter.length; i++) {
            string = string.trim().replaceAll(sCharacter[i], "");
        }
        return string;
    }

    /**
     * 处理小数点位数
     *
     * @param d            要处理的double类型数值
     * @param num          需要保留几位小数
     * @param roundingMode 处理小数的模式
     * @return
     */
    private static double double2Double(double d, int num, int roundingMode) {
        if (num < 0) {
            throw new IllegalArgumentException(
                    "illegal decimal value [" + num + "]");
        }
        return new BigDecimal(d).setScale(num, roundingMode).doubleValue();
    }

    /**
     * String类型转double类型
     *
     * @param d            要处理的String类型数值
     * @param num          需要保留几位小数
     * @param roundingMode 处理小数的模式
     * @return
     */
    private static double string2Double(String d, int num, int roundingMode) {
        if (num < 0) {
            throw new IllegalArgumentException(
                    "illegal decimal value [" + num + "]");
        }
        return new BigDecimal(d).setScale(num, roundingMode).doubleValue();
    }
}
