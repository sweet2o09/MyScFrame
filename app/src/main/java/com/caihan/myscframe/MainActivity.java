package com.caihan.myscframe;

import android.content.Intent;
import android.view.View;

import com.caihan.myscframe.demo.MvpAct;
import com.caihan.myscframe.demo.PermissionActivity;
import com.caihan.scframe.framework.base.ScActivity;

public class MainActivity extends ScActivity {

    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
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

    public void permission(View view) {
        //权限Demo
        startActivity(new Intent(this, PermissionActivity.class));
    }

    public void mvpDemo(View view) {
        //MVP设计Demo
        startActivity(new Intent(this, MvpAct.class));
    }
}
