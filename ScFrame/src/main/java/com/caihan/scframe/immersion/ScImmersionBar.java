package com.caihan.scframe.immersion;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.caihan.scframe.immersion.base.AbstractImmersion;
import com.gyf.barlibrary.ImmersionBar;

/**
 * 沉浸式效果<br/>
 * 适用于:<br/>
 * 1.单个Activity场景<br/>
 * 2.单个Activity嵌套多个Fragment场景<br/>
 * 3.Dialog场景<br/>
 * 解决了部分机型因为无法修改状态栏颜色而出现的视效问题
 *
 * @author caihan
 * @date 2018/1/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ScImmersionBar extends AbstractImmersion {
    private static final String TAG = "U1CityImmersionBar";

    ImmersionBar mImmersionBar = null;

    /**
     * 在Activity中使用
     *
     * @param activity
     */
    public ScImmersionBar(@NonNull Activity activity) {
        mImmersionBar = ImmersionBar.with(activity);
        init();
    }

    /**
     * 在Fragment中使用
     *
     * @param activity
     * @param fragment
     */
    public ScImmersionBar(@NonNull Activity activity, @NonNull Fragment fragment) {
        mImmersionBar = ImmersionBar.with(activity, fragment);
        init();
    }

    /**
     * 在dialog里使用
     *
     * @param activity
     * @param dialog
     * @param dialogTag
     */
    public ScImmersionBar(@NonNull Activity activity, @NonNull Dialog dialog,
                          @NonNull String dialogTag) {
        mImmersionBar = ImmersionBar.with(activity, dialog, dialogTag);
        init();
    }

    @Override
    public void init() {
        mImmersionBar.init();
    }

    /**
     * 基础沉浸式效果
     *
     * @param view 顶部控件,可以是toolbar或者是ImageView等...
     */
    public void setImmersion(View view) {
        mImmersionBar.titleBar(view)
                .init();
    }

    public void setImmersion2(View view) {
        mImmersionBar.titleBarMarginTop(view)
                .init();
    }

    /**
     * 沉浸式效果,改变状态栏文字
     * <p>
     * 该方法被普遍用于单个Activity嵌套多个Fragment场景
     *
     * @param view       顶部控件,可以是toolbar或者是ImageView等...
     * @param isDarkFont 是否修改状态栏文字颜色 true = 黑色字,false = 白色字
     */
    public void setImmersionDarkFont(View view, boolean isDarkFont) {
        mImmersionBar.titleBar(view)
                .statusBarDarkFont(isDarkFont, 0.2f)
                .init();
    }

    /**
     * 该方法比较适用于当toolbar被设置成ActionBar的时候
     *
     * @param view
     * @param isDarkFont
     */
    public void setImmersionDarkFont2(View view, boolean isDarkFont) {
        mImmersionBar.titleBarMarginTop(view)
                .statusBarDarkFont(isDarkFont, 0.2f)
                .init();
    }

    /**
     * 该方法被普遍用于单个Activity嵌套多个Fragment场景
     * Fragment切换的时候调用该方法
     *
     * @param isDarkFont 是否修改状态栏文字颜色 true = 黑色字,false = 白色字
     */
    public void statusBarDarkFont(boolean isDarkFont) {
        mImmersionBar.statusBarDarkFont(isDarkFont, 0.2f)
                .init();
    }

    /**
     * 透明状态栏，默认透明
     */
    public void setImmersionTransparentStatusBar() {
        mImmersionBar.transparentStatusBar()
                .init();
    }

    /**
     * 透明状态栏和导航栏,并且全屏
     * <p>
     * 一般用于启动页,引导页
     */
    public void setImmersionTransparent() {
        mImmersionBar.transparentBar()
                .init();
    }

    /**
     * 透明状态栏和导航栏,并且全屏
     * <p>
     * 一般用于启动页,引导页
     *
     * @param isDarkFont 是否修改状态栏文字颜色 true = 黑色字,false = 白色字
     */
    public void setImmersionTransparentDarkFont(boolean isDarkFont) {
        mImmersionBar.transparentBar()
                .statusBarDarkFont(isDarkFont, 0.2f)
                .init();
    }

    /**
     * 解决软键盘与底部输入框冲突问题
     */
    public void keyboardEnable() {
        mImmersionBar.keyboardEnable(true)
                .init();
    }

    /**
     * 状态栏颜色
     *
     * @param statusBarColor 状态栏颜色，资源文件（R.color.xxx）
     */
    public void statusBarColor(@ColorRes int statusBarColor){
        mImmersionBar.statusBarColor(statusBarColor)
                .init();
    }


    @Override
    public void destroy() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
    }

    @Deprecated
    private void msg() {
//        mImmersionBar.transparentStatusBar()  //透明状态栏，不写默认透明色
//                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
//                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
//                .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
//                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
//                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
//                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
//                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//                .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
//                .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
//                .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
//                .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
//                .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一,view为状态栏view
//                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//                .supportActionBar(true) //支持ActionBar使用
//                .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
//                .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
//                .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
//                .removeSupportView(toolbar)  //移除指定view支持
//                .removeSupportAllView() //移除全部view支持
//                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
//                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
//                .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
//                .addTag("tag")  //给以上设置的参数打标记
//                .getTag("tag")  //根据tag获得沉浸式参数
//                .reset()  //重置所以沉浸式参数
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
//                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
//                    @Override
//                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
//                        LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
//                    }
//                })
//                .init();//必须调用方可沉浸式
    }
}
