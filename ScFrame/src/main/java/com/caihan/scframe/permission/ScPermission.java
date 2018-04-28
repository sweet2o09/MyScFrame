package com.caihan.scframe.permission;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.caihan.scframe.permission.base.AbstractPermission;
import com.caihan.scframe.permission.base.IRationaleDialogListener;
import com.caihan.scframe.permission.base.ISettingDialogListener;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.permission.proxy.PermissionDelegate;
import com.caihan.scframe.permission.proxy.PermissionProxy;

import java.util.ArrayList;

/**
 * 权限申请工具类,使用构建者与代理模式{@link PermissionProxy}<br/>
 * 采用注解反射机制,混淆的时候需要加入相关信息
 * 下列权限需要动态申请,属于危险权限<br/>
 * [Android6.0哪些权限要动态申请？](http://blog.csdn.net/wwdlss/article/details/52909098)
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
 *
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者 caihan
 * TODO: 2018/1/6 只是简单封装,后续如果需要的话,根据公司需求进行更改
 */
public class ScPermission extends AbstractPermission {

    private static final String TAG = "ScPermission";

    private PermissionDelegate mPermissionDelegate;


    /**
     * 实例化权限申请类
     */
    private ScPermission(Builder builder) {
        mPermissionDelegate = new PermissionProxy();
        setContext(builder.mContext);
        setListener(builder.mListener);
        setPermissionList(builder.mPermissionList);
        setNeedFinish(builder.mNeedFinish);
        setShowRationaleDialog(builder.mShowRationaleDialog);
        setRationaleDialogListener(builder.mRationaleDialogListener);
        setSettingDialogListener(builder.mSettingDialogListener);
    }

    @Override
    public void setContext(Context context) {
        mPermissionDelegate.setContext(context);
    }

    @Override
    public void setListener(OnPermissionListener listener) {
        mPermissionDelegate.setListener(listener);
    }

    @Override
    public void setPermissionList(ArrayList<String> permissionList) {
        mPermissionDelegate.setPermissionList(permissionList);
    }

    @Override
    public void setNeedFinish(boolean needFinish) {
        mPermissionDelegate.setNeedFinish(needFinish);
    }

    @Override
    public void setShowRationaleDialog(boolean showRationaleDialog) {
        mPermissionDelegate.setShowRationaleDialog(showRationaleDialog);
    }

    @Override
    public void setRationaleDialogListener(IRationaleDialogListener rationaleDialogListener) {
        mPermissionDelegate.setRationaleDialogListener(rationaleDialogListener);
    }

    @Override
    public void setSettingDialogListener(ISettingDialogListener settingDialogListener) {
        mPermissionDelegate.setSettingDialogListener(settingDialogListener);
    }

    /**
     * 申请权限
     *
     * @param permissionsList
     */
    @Override
    public void request(@NonNull ArrayList<String> permissionsList) {
        mPermissionDelegate.request(permissionsList);
    }

    /**
     * 申请权限
     *
     * @param permissionsArray
     */
    @Override
    public void request(@NonNull String[]... permissionsArray) {
        mPermissionDelegate.request(permissionsArray);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPermissionDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean isPermissionRequest(int requestCode) {
        return mPermissionDelegate.isPermissionRequest(requestCode);
    }

    @Override
    public void onDestroy() {
        mPermissionDelegate.onDestroy();
    }

    public static class Builder {


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


        public Builder(Context context) {
            mContext = context;
        }

        /**
         * 设置回调监听
         *
         * @param listener
         */
        public Builder setListener(OnPermissionListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 拒绝权限后是否关闭界面或APP,默认false
         *
         * @param needFinish
         */
        public Builder setNeedFinish(boolean needFinish) {
            mNeedFinish = needFinish;
            return this;
        }

        /**
         * 设置是否显示showRationaleDialog,默认true
         *
         * @param showRationaleDialog
         */
        public Builder setShowRationaleDialog(boolean showRationaleDialog) {
            mShowRationaleDialog = showRationaleDialog;
            return this;
        }

        /**
         * 设置RationaleDialog回调监听,实现自己的RationaleDialog
         *
         * @param rationaleDialogListener
         */
        public Builder setRationaleDialogListener(IRationaleDialogListener rationaleDialogListener) {
            mRationaleDialogListener = rationaleDialogListener;
            return this;
        }

        /**
         * 设置SettingDialog回调监听,实现自己的SettingDialog
         *
         * @param settingDialogListener
         */
        public Builder setSettingDialogListener(ISettingDialogListener settingDialogListener) {
            mSettingDialogListener = settingDialogListener;
            return this;
        }


        public ScPermission build() {
            return new ScPermission(this);
        }

    }
}
