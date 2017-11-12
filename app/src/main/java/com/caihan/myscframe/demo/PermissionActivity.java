package com.caihan.myscframe.demo;

import android.view.View;

import com.caihan.myscframe.R;
import com.caihan.scframe.framework.base.ScActivity;
import com.caihan.scframe.utils.permission.OnPermissionListener;
import com.yanzhenjie.permission.Permission;

/**
 * 作者：caihan
 * 创建时间：2017/10/29
 * 邮箱：93234929@qq.com
 * 说明：
 * 动态权限申请Demo,测试OK
 */
public class PermissionActivity extends ScActivity implements OnPermissionListener {

    @Override
    public int setLayoutResId() {
        return R.layout.activity_permission;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initActionBar() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void request() {

    }

    public void click1(View view){
        //相机权限
        requestPermission(this, true, Permission.STORAGE,Permission.CAMERA);
    }
    public void click2(View view){
        //定位权限
        requestPermission(this, Permission.STORAGE,Permission.LOCATION);
    }
    public void click3(View view){
        //日历权限
        requestPermission(this, Permission.CALENDAR);
    }

    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied() {

    }
}
