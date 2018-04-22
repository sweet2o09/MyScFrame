package com.caihan.scframe.permission.proxy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.caihan.scframe.permission.base.IRationaleDialogListener;
import com.caihan.scframe.permission.base.ISettingDialogListener;
import com.caihan.scframe.permission.base.OnPermissionListener;

import java.util.ArrayList;

/**
 * 二重代理->目标对象
 *
 * @author caihan
 * @date 2018/1/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface PermissionDelegate {

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
     * 销毁
     */
    void onDestroy();
}
