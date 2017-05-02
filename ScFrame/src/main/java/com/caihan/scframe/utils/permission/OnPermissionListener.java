package com.caihan.scframe.utils.permission;

/**
 * Created by caihan on 2017/4/17.
 * 权限申请回调
 */
public interface OnPermissionListener {
    /**
     * 权限申请成功
     */
    void onPermissionGranted();

    /**
     * 权限申请失败
     */
    void onPermissionDenied();
}
