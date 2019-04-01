![logo][logo]

[![auc][aucSvg]][auc] [![api][apiSvg]][api] [![build][buildSvg]][build]


## 功能介绍
### 1.ScFrame-Library
底层框架,里面封装了部分常用的第三方开源库以及我自己写的MVP框架

### 2.二维码扫描
`ZxingQrcodeDemoActivity`

### 3.仿iOS的SwitchBtn
`WeChatSwitchBtnActivity`<p/>[自定义View仿iOS的UiSwitch控件](https://www.jianshu.com/p/c94b9b1dfad6)

### 4.仿朋友圈九宫格
`NinePhotoShowActivity`

### 5.九宫格添加图片
`NinePhotoAddActivity`

### 6.系统相机与相册调用封装
`SystemGalleryActivity`

### 7.图像处理封装 
`ImageLoaderActivity`

### 8.弹窗队列封装
`DialogQueueActivity`<p/>[Android实现Dialog队列](https://www.jianshu.com/p/6ee3b054965a)

### 9.内存泄漏Demo
`LeakCanaryActivity`<p/>`LeakCanaryActivity2`

### 10.全面屏/刘海屏适配
```
        <!--适配华为刘海屏-->
        <meta-data
            android:name="android.notch_support"
            android:value="true"/>
        <!--适配小米刘海屏-->
        <meta-data
            android:name="notch.config"
            android:value="portrait|landscape"/>

        <!--适配全面屏-->
        <meta-data
            android:name="android.max_aspect"
            android:value="2.4"/>
```
### 11.引入RxEasyHttp
`RequestJobActivity`

### 12.Behavior的使用
`CoordinatorActivity`<p/>`BottomSheetActivity`

### 13.RecyclerView.ItemDecoration实现顶部吸附
`TopAdsorptionActivity`

## 感谢
感谢大家点开我的github,希望能留下你的start与issues

## Contact

[![jianshu][jianshuSvg]][jianshu] ![QQ][qqSvg]

![logo2][logo2]


[logo]: https://raw.githubusercontent.com/sweet2o09/MyScFrame/master/art/logo.png
[logo2]: https://raw.githubusercontent.com/sweet2o09/MyScFrame/master/art/logo2.png

[aucSvg]: https://img.shields.io/badge/MyScFrame-v1.0.0-brightgreen.svg
[auc]: https://github.com/sweet2o09/MyScFrame

[apiSvg]: https://img.shields.io/badge/API-19+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=19

[buildSvg]: https://travis-ci.org/sweet2o09/MyScFrame.svg?branch=master
[build]: https://travis-ci.org/sweet2o09/MyScFrame

[jianshuSvg]: https://img.shields.io/badge/简书-@wo叫天然呆-34a48e.svg
[jianshu]: https://www.jianshu.com/u/b55a43d1711d

[qqSvg]: https://img.shields.io/badge/QQ-93234929-34a48e.svg
