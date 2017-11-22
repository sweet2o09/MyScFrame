package com.caihan.myscframe.config;

import com.caihan.myscframe.demo.EventBusAct;
import com.caihan.myscframe.demo.LceDemoAct;
import com.caihan.myscframe.demo.MvpAct;
import com.caihan.myscframe.demo.PermissionActivity;

/**
 * 作者：caihan
 * 创建时间：2017/11/21
 * 邮箱：93234929@qq.com
 * 说明：
 */

public interface ConstValue {

    Class<?>[] ACTIVITY = {
            PermissionActivity.class,
            MvpAct.class,
            EventBusAct.class,
            LceDemoAct.class
    };

    String[] TITLE = {
            "AndPermission动态权限申请",
            "MVP设计Demo",
            "EventBusDemo",
            "LCE设计Demo"
    };
}
