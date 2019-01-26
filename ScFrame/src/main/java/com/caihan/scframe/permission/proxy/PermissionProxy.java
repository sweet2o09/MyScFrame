package com.caihan.scframe.permission.proxy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.caihan.scframe.permission.base.IRationaleDialogListener;
import com.caihan.scframe.permission.base.ISettingDialogListener;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态权限,代理,目标对象->AndPermission
 *
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class PermissionProxy implements PermissionDelegate {

    private static final String TAG = "PermissionProxy";

    private Context mContext;

    /**
     * For Android 6.0
     */
    private OnPermissionListener mListener = null;

    /**
     * 权限申请list,用于设置界面跳转回来判断权限是否已被手动开启
     */
    private ArrayList<String> mPermissionList = null;

    /**
     * 拒绝权限后是否关闭界面或APP,默认false
     */
    private boolean mNeedFinish = false;

    /**
     * 是否展示RationaleDialog,默认true
     */
    private boolean mShowRationaleDialog = true;

    /**
     * 自定义RationaleDialog回调
     */
    private IRationaleDialogListener mRationaleDialogListener = null;

    /**
     * 自定义SettingDialog回调
     */
    private ISettingDialogListener mSettingDialogListener = null;

    /**
     * 申请标记值
     */
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 3 << 5;

    /**
     * 系统设置返回requestCode
     */
    private static final int REQUEST_CODE_SETTING = 5 << 5;


    public PermissionProxy() {
    }

    @Override
    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public void setListener(OnPermissionListener listener) {
        this.mListener = listener;
    }

    @Override
    public void setPermissionList(ArrayList<String> permissionList) {
        this.mPermissionList = permissionList;
    }

    @Override
    public void setNeedFinish(boolean needFinish) {
        this.mNeedFinish = needFinish;
    }

    @Override
    public void setShowRationaleDialog(boolean showRationaleDialog) {
        this.mShowRationaleDialog = showRationaleDialog;
    }

    @Override
    public void setRationaleDialogListener(IRationaleDialogListener rationaleDialogListener) {
        this.mRationaleDialogListener = rationaleDialogListener;
    }

    @Override
    public void setSettingDialogListener(ISettingDialogListener settingDialogListener) {
        this.mSettingDialogListener = settingDialogListener;
    }

    @Override
    public void request(@NonNull String[]... permissionsArray) {
        AndPermission.with(mContext)
                .requestCode(REQUEST_CODE_ASK_PERMISSIONS)
                .permission(permissionsArray)
                .callback(this)
                .rationale(new RationaleListener() {
                    /**
                     * rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                     * 这样避免用户勾选不再提示，导致以后无法申请权限。
                     * 你也可以不设置
                     * @param requestCode
                     * @param rationale
                     */
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        if (mRationaleDialogListener != null) {
                            mRationaleDialogListener.showRationaleDialog(requestCode, rationale);
                        } else if (mShowRationaleDialog) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(mContext, rationale).show();
                        }
                    }
                })
                .start();
    }

    @Override
    public void request(@NonNull ArrayList<String> permissionsList) {
        if (mPermissionList == null) {
            mPermissionList = new ArrayList<>();
        }
        mPermissionList.clear();
        mPermissionList.addAll(permissionsList);
        request(permissionsList.toArray(new String[permissionsList.size()]));
    }

    @PermissionYes(REQUEST_CODE_ASK_PERMISSIONS)
    public void onSuccess(List<String> permissions) {
        mListener.onPermissionSuccessful();
        if (mPermissionList != null){
            mPermissionList.clear();
        }
    }

    @PermissionNo(REQUEST_CODE_ASK_PERMISSIONS)
    public void onFailure(List<String> permissions) {
        mPermissionList = new ArrayList<>();
        mPermissionList.addAll(permissions);
        //用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权
        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
            if (mSettingDialogListener != null) {
//                AndPermission.defaultSettingDialog((Activity) mContext, REQUEST_CODE_SETTING)
//                        .setTitle(R.string.title_dialog)
//                        .setMessage(R.string.message_permission_failed)
//                        .setPositiveButton(R.string.ok)
//                        .setNegativeButton(R.string.no, null)
//                        .show();
                mSettingDialogListener.showSettingDialog(REQUEST_CODE_SETTING);
            } else {
                //用默认的提示语。
                AndPermission.defaultSettingDialog((Activity) mContext, REQUEST_CODE_SETTING).show();
            }
        } else {
            mListener.onPermissionFailure();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING:
                //从设置那边回来后看是否还需要继续检查权限
                if (mPermissionList != null && mPermissionList.size() > 0) {
                    request(mPermissionList.toArray(
                            new String[mPermissionList.size()]));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否是权限申请回来的
     *
     * @param requestCode
     * @return
     */
    @Override
    public boolean isPermissionRequest(int requestCode){
        return requestCode == REQUEST_CODE_SETTING;
    }

    @Override
    public void onDestroy() {
        this.mContext = null;
        this.mListener = null;
        this.mPermissionList = null;
        this.mNeedFinish = false;
        this.mShowRationaleDialog = true;
        this.mRationaleDialogListener = null;
        this.mSettingDialogListener = null;
    }

}
