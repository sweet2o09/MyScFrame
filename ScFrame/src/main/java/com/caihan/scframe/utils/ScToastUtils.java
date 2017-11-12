package com.caihan.scframe.utils;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;

/**
 * 作者：caihan
 * 创建时间：2017/10/25
 * 邮箱：93234929@qq.com
 * 实现功能：吐司工具类
 * 备注：
 */
public class ScToastUtils {

    /**
     * 设置背景颜色
     * 请在showToast()之前调用
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.setBgColor(backgroundColor);
    }

    /**
     * 设置背景资源
     * 请在showToast()之前调用
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.setBgResource(bgResource);
    }

    /**
     * 设置消息颜色
     * 请在showToast()之前调用
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        ToastUtils.setMsgColor(msgColor);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showToast(@NonNull final CharSequence text) {
        ToastUtils.showShort(text);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(@StringRes final int resId) {
        ToastUtils.showShort(resId);
    }
}
