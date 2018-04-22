package com.caihan.scframe.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.caihan.scframe.R;

/**
 * 默认Dialog样式
 *
 * @author caihan
 * @date 2018/2/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class DefaultDialog {
    //单例模式->双重检查模式
    private static final String TAG = "DefaultDialog";
    //volatile表示去掉虚拟机优化代码,但是会消耗少许性能,可忽略
    private volatile static DefaultDialog sInstance = null;

    public static DefaultDialog getInstance() {
        if (sInstance == null) {
            //同步代码块
            synchronized (DefaultDialog.class) {
                if (sInstance == null) {
                    sInstance = new DefaultDialog();
                }
            }
        }
        return sInstance;
    }

    private DefaultDialog() {
    }

    /**
     * 返回Material样式Dialog
     *
     * @param context
     * @return
     */
    public MaterialDialog.Builder getDialog(Context context) {
        return new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .positiveText("确定")
                .negativeText("取消")
                .positiveColorRes(R.color.u1city_frame_dialog_positive_color)
                .negativeColorRes(R.color.u1city_frame_dialog_negative_color);
    }

    /**
     * 不带默认按钮文字的
     *
     * @param context
     * @return
     */
    public MaterialDialog.Builder getDialogNoBtn(Context context) {
        return new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .positiveColorRes(R.color.u1city_frame_dialog_positive_color)
                .negativeColorRes(R.color.u1city_frame_dialog_negative_color);
    }
}