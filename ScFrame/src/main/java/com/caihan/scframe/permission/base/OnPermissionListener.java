package com.caihan.scframe.permission.base;

/**
 * 权限申请回调
 *
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者 caihan
 */
public interface OnPermissionListener {

    /**
     * 权限申请成功
     */
    void onPermissionSuccessful();

    /**
     * 权限申请失败
     */
    void onPermissionFailure();
}
