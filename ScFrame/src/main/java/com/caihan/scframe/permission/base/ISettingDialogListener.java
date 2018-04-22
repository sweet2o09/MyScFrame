package com.caihan.scframe.permission.base;

/**
 * 自定义提示用户到设置中授权接口
 *
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface ISettingDialogListener {

    /**
     * 自定义SettingDialog
     *
     * @param requestCode
     */
    void showSettingDialog(int requestCode);
}
