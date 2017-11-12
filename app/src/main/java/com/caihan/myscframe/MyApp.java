package com.caihan.myscframe;

import com.caihan.scframe.framework.ScApplication;

import java.util.ArrayList;

/**
 * Created by caihan on 2017/6/13.
 */

public class MyApp extends ScApplication {
    public static final String TAG = "MyScFrame";
    public static MyApp app = null;
    private final static int RE_TIME = 30 * 1000;//后台运行30秒后启动闪屏
    public static Class sLockActClass = null;
    public static String sLockActName = "LockAct";
    public static ArrayList<String> sActForUnLockList = new ArrayList<>();


    static {
        sActForUnLockList.add("WelcomeAct");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        initFlash();
        initLogUtils(true, TAG);
        initSpUtils(TAG);
    }

    private void initFlash() {
        initFlashUtil(app, sLockActClass, sActForUnLockList, RE_TIME);
    }

}
