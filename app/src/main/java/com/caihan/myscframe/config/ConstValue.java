package com.caihan.myscframe.config;import com.caihan.myscframe.ui.immersion.ImmersionActivityDemo;import com.caihan.myscframe.ui.immersion.ImmersionFragmentActivity;import com.caihan.myscframe.ui.immersion.ImmersionFragmentMvpActivity;import com.caihan.myscframe.ui.immersion.ImmersionImageActivity;import com.caihan.myscframe.ui.leakcanary.LeakCanaryActivity;import com.caihan.myscframe.ui.multilayerfragment.MultilayerActivity;import com.caihan.myscframe.ui.multistoreylist.MultiStoreyListActivity;import com.caihan.myscframe.ui.product.ProductActivity;import com.caihan.myscframe.ui.qrcode.ZxingQrcodeDemoActivity;import com.caihan.myscframe.ui.rxandroid.RxJavaActivity;/** * 作者：caihan * 创建时间：2017/11/21 * 邮箱：93234929@qq.com * 说明： */public interface ConstValue {    Class<?>[] ACTIVITY = {            ZxingQrcodeDemoActivity.class,            MultiStoreyListActivity.class,            ImmersionActivityDemo.class,            LeakCanaryActivity.class,            RxJavaActivity.class,            ProductActivity.class,            MultilayerActivity.class    };    String[] TITLE = {            "二维码扫描",            "多层数据嵌套",            "沉浸式效果",            "LeakCanary测试",            "RxJava",            "动态打包参数",            "多层Fragment嵌套"    };    Class<?>[] IMMERSION_ACTIVITY = {            ImmersionActivityDemo.class,            ImmersionImageActivity.class,            ImmersionFragmentActivity.class,            ImmersionFragmentMvpActivity.class    };    String[] IMMERSION_TITLE = {            "Toolbar沉浸式",            "图片沉浸式",            "Fragment沉浸式",            "Mvp设计Fragment沉浸式"    };}