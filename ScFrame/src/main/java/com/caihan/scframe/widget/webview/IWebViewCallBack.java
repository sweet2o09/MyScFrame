package com.caihan.scframe.widget.webview;

import android.view.animation.Animation;


/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * Webview接口
 */
public interface IWebViewCallBack {

    void closedWebView();

    void onProgressChanged(Animation animation, int newProgress);

    void onReceivedTitle(String title);
}
