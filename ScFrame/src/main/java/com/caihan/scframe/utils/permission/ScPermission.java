package com.caihan.scframe.utils.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.caihan.scframe.utils.MyAppUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：caihan
 * 创建时间：2017/4/17
 * 邮箱：93234929@qq.com
 * 实现功能：权限申请工具类
 * 备注：下列权限需要动态申请,属于危险权限http://blog.csdn.net/wwdlss/article/details/52909098
 * <p>
 * Dangerous Permissions:
 * group:android.permission-group.CONTACTS
 * permission:android.permission.WRITE_CONTACTS
 * permission:android.permission.GET_ACCOUNTS
 * permission:android.permission.READ_CONTACTS
 * <p>
 * group:android.permission-group.PHONE
 * permission:android.permission.READ_CALL_LOG
 * permission:android.permission.READ_PHONE_STATE
 * permission:android.permission.CALL_PHONE
 * permission:android.permission.WRITE_CALL_LOG
 * permission:android.permission.USE_SIP
 * permission:android.permission.PROCESS_OUTGOING_CALLS
 * permission:com.android.voicemail.permission.ADD_VOICEMAIL
 * <p>
 * group:android.permission-group.CALENDAR
 * permission:android.permission.READ_CALENDAR
 * permission:android.permission.WRITE_CALENDAR
 * <p>
 * group:android.permission-group.CAMERA
 * permission:android.permission.CAMERA
 * <p>
 * group:android.permission-group.SENSORS
 * permission:android.permission.BODY_SENSORS
 * <p>
 * group:android.permission-group.LOCATION
 * permission:android.permission.ACCESS_FINE_LOCATION
 * permission:android.permission.ACCESS_COARSE_LOCATION
 * <p>
 * group:android.permission-group.STORAGE
 * permission:android.permission.READ_EXTERNAL_STORAGE
 * permission:android.permission.WRITE_EXTERNAL_STORAGE
 * <p>
 * group:android.permission-group.MICROPHONE
 * permission:android.permission.RECORD_AUDIO
 * <p>
 * group:android.permission-group.SMS
 * permission:android.permission.READ_SMS
 * permission:android.permission.RECEIVE_WAP_PUSH
 * permission:android.permission.RECEIVE_MMS
 * permission:android.permission.RECEIVE_SMS
 * permission:android.permission.SEND_SMS
 * permission:android.permission.READ_CELL_BROADCASTS
 * <p>
 * Rationale能力:
 * Android运行时权限有一个特点，在拒绝过一次权限后，再此申请该权限，在申请框会多一个不再提示的复选框，
 * 当用户勾选了不再提示并拒绝了权限后，下次再申请该权限将直接回调申请失败。
 * 因此Rationale功能是在用户拒绝一次权限后，再次申请时检测到已经申请过一次该权限了，
 * 允许开发者弹窗说明申请权限的目的，获取用户的同意后再申请权限，避免用户勾选不再提示，导致不能再次申请权限。
 */
public class ScPermission {
    private static final String TAG = "ScPermission";
    //申请标记值
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    //手动开启权限requestCode
    private static final int REQUEST_CODE_SETTING = 200;

    private Activity mActivity;
    // For Android 6.0
    private OnPermissionListener mListener;
    //拒绝权限后是否关闭界面或APP
    private boolean mNeedFinish = false;

    private ArrayList<String> permissionList;

    public ScPermission(Activity act, OnPermissionListener listener) {
        this.mActivity = act;
        this.mListener = listener;
    }

    /**
     * 权限被拒绝后是否直接关闭界面
     *
     * @param needFinish
     */
    private void setNeedFinish(boolean needFinish) {
        mNeedFinish = needFinish;
    }

    public void requestPermission(String[]... permissionsArray) {
        requestPermission(false, permissionsArray);
    }

    public void requestPermission(boolean needFinish, String[]... permissionsArray) {
        LogUtils.d(TAG, needFinish,permissionsArray);
        setNeedFinish(needFinish);
        AndPermission.with(mActivity)
                .requestCode(REQUEST_CODE_ASK_PERMISSIONS)
                .permission(permissionsArray)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        showRationaleDialog(rationale);
                    }
                })
                .start();
    }

    @PermissionYes(REQUEST_CODE_ASK_PERMISSIONS)
    public void yes(List<String> permissions) {
        if (this.mListener != null) {
            this.mListener.onPermissionGranted();
        }
    }

    @PermissionNo(REQUEST_CODE_ASK_PERMISSIONS)
    public void no(List<String> permissions) {
        if (permissionList == null) {
            permissionList = new ArrayList<>();
        }
        permissionList.addAll(permissions);
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(mActivity, permissions)) {
            // 第一种：用默认的提示语。
            showDefaultSettingDialog();
        }
        if (this.mListener != null) {
            this.mListener.onPermissionDenied();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(TAG, requestCode,resultCode);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                //从设置那边回来后看是否还需要继续检查权限
                if (permissionList != null && permissionList.size() > 0) {
                    requestPermission(permissionList.toArray(new String[permissionList.size()]));
                }
                break;
            }
        }
    }

    /**
     * 提示用户必要权限被关闭了
     *
     * @param rationale
     */
    private void showRationaleDialog(Rationale rationale) {
        LogUtils.d(TAG, "showRationaleDialog");
        AndPermission.rationaleDialog(mActivity, rationale)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (mNeedFinish && mActivity != null) {
                            MyAppUtils.restart(mActivity);
                        }
                    }
                }).show();
    }

    /**
     * 提示去设置界面更改权限Dialog
     */
    private void showDefaultSettingDialog() {
        LogUtils.d(TAG, "showDefaultSettingDialog");
        AndPermission.defaultSettingDialog(mActivity, REQUEST_CODE_SETTING)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (mNeedFinish && mActivity != null) {
                            MyAppUtils.restart(mActivity);
                        }
                    }
                }).show();
    }

    public void onDestroy() {
        mActivity = null;
        mListener = null;
        mNeedFinish = false;
        permissionList = null;
    }
}
