package com.caihan.scframe.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.caihan.scframe.R;
import com.caihan.scframe.utils.MyAppUtils;

import java.util.ArrayList;

/**
 * Created by caihan on 2017/4/17.
 * 权限申请工具类
 */
public class ScPermission {
    private static final String TAG = "ScPermission";

    private Activity mActivity;
    // For Android 6.0
    private OnPermissionListener mListener;
    //申请标记值
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    //手动开启权限requestCode
    private static final int SETTINGS_REQUEST_CODE = 200;
    //拒绝权限后是否关闭界面或APP
    private boolean mNeedFinish = false;
    //界面传递过来的权限列表,用于二次申请
    private ArrayList<String> mPermissionsList = new ArrayList<>();
    //必要全选,如果这几个权限没通过的话,就无法使用APP
    public static final ArrayList<String> FORCE_REQUIRE_PERMISSIONS = new ArrayList<String>() {
        {
            add(Manifest.permission.INTERNET);
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(Manifest.permission.ACCESS_FINE_LOCATION);
            add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    };

    public ScPermission(Activity activity) {
        mActivity = activity;
    }

    /**
     * 权限允许或拒绝对话框
     *
     * @param permissions 需要申请的权限
     * @param needFinish  如果必须的权限没有允许的话，是否需要finish当前 Activity
     * @param callback    回调对象
     */
    public void requestPermission(final ArrayList<String> permissions, final boolean needFinish,
                                  final OnPermissionListener callback) {
        if (permissions == null || permissions.size() == 0) {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
            return;
        }
        mNeedFinish = needFinish;
        mListener = callback;
        mPermissionsList = permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> newPermissions = checkEachSelfPermission(permissions);
            if (newPermissions.size() > 0) {// 检查是否声明了权限
                requestEachPermissions(newPermissions.toArray(new String[newPermissions.size()]));
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param permissions
     */
    private void requestEachPermissions(String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(permissions);
        } else {
            ActivityCompat.requestPermissions(mActivity, permissions,
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param permissions
     */
    private void showRationaleDialog(final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.tips)
                .setMessage(R.string.permission_desc)
                .setPositiveButton(R.string.confrim,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(mActivity, permissions,
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        })
                .setNegativeButton(R.string.cancle,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (mNeedFinish) MyAppUtils.restart(mActivity);
                            }
                        })
                .setCancelable(false)
                .show();
    }

    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return newPermissions.size > 0 表示有权限需要申请
     */
    private ArrayList<String> checkEachSelfPermission(ArrayList<String> permissions) {
        ArrayList<String> newPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                newPermissions.add(permission);
            }
        }
        return newPermissions;
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS && permissions != null) {
            ArrayList<String> deniedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            // 判断被拒绝的权限中是否有包含必须具备的权限
            ArrayList<String> forceRequirePermissionsDenied =
                    checkForceRequirePermissionDenied(FORCE_REQUIRE_PERMISSIONS, deniedPermissions);
            if (forceRequirePermissionsDenied != null && forceRequirePermissionsDenied.size() > 0) {
                // 必备的权限被拒绝，
                if (mNeedFinish) {
                    showPermissionSettingDialog();
                } else {
                    if (mListener != null) {
                        mListener.onPermissionDenied();
                    }
                }
            } else {
                // 不存在必备的权限被拒绝，可以进首页
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> checkForceRequirePermissionDenied(
            ArrayList<String> forceRequirePermissions, ArrayList<String> deniedPermissions) {
        ArrayList<String> forceRequirePermissionsDenied = new ArrayList<>();
        if (forceRequirePermissions != null && forceRequirePermissions.size() > 0
                && deniedPermissions != null && deniedPermissions.size() > 0) {
            for (String forceRequire : forceRequirePermissions) {
                if (deniedPermissions.contains(forceRequire)) {
                    forceRequirePermissionsDenied.add(forceRequire);
                }
            }
        }
        return forceRequirePermissionsDenied;
    }

    /**
     * 手动开启权限弹窗
     */
    private void showPermissionSettingDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("提示")
                .setMessage("必要的权限被拒绝")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyAppUtils.getAppDetailsSettings(mActivity, SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (mNeedFinish) MyAppUtils.restart(mActivity);
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void onActivityResult(int requestCode) {
        //如果需要跳转系统设置页后返回自动再次检查和执行业务 如果不需要则不需要重写onActivityResult
        if (requestCode == SETTINGS_REQUEST_CODE) {
            requestPermission(mPermissionsList, mNeedFinish, mListener);
        }
    }
}
