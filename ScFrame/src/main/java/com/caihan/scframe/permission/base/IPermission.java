package com.caihan.scframe.permission.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * 动态权限申请接口
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IPermission {

//    Context getContext();

    void setContext(Context context);

//    OnPermissionListener getListener();

    void setListener(OnPermissionListener listener);

//    ArrayList<String> getPermissionList();

    void setPermissionList(ArrayList<String> permissionList);

//    boolean isNeedFinish();

    void setNeedFinish(boolean needFinish);

//    boolean isShowRationaleDialog();

    void setShowRationaleDialog(boolean showRationaleDialog);

//    IRationaleDialogListener getRationaleDialogListener();

    void setRationaleDialogListener(IRationaleDialogListener rationaleDialogListener);

//    ISettingDialogListener getSettingDialogListener();

    void setSettingDialogListener(ISettingDialogListener settingDialogListener);

    /**
     * 权限申请String[]类型
     *
     * @param permissionsArray
     */
    void request(@NonNull String[]... permissionsArray);

    /**
     * 权限申请ArrayList<String>类型
     *
     * @param permissionsList
     */
    void request(@NonNull ArrayList<String> permissionsList);

    /**
     * 系统设置界面回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * 判断是否是权限申请返回
     *
     * @param requestCode
     * @return
     */
    boolean isPermissionRequest(int requestCode);

    /**
     * 销毁
     */
    void onDestroy();
}
