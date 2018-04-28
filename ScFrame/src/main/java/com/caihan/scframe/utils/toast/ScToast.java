package com.caihan.scframe.utils.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Toast工具管理类
 *
 * @author caihan
 * @date 2018/1/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class ScToast {

    /**
     * 设置背景颜色
     * 请在{@link #showToast(CharSequence)}之前调用
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.setBgColor(backgroundColor);
    }

    /**
     * 设置背景资源
     * 请在{@link #showToast(CharSequence)}之前调用
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.setBgResource(bgResource);
    }

    /**
     * 设置消息颜色
     * 请在{@link #showToast(CharSequence)}之前调用
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
    public static void showToast(@StringRes final int resId) {
        ToastUtils.showShort(resId);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showToastLong(@NonNull final CharSequence text){
        ToastUtils.showLong(text);
    }
}
