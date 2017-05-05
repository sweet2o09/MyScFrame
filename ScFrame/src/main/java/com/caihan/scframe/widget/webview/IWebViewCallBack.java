package com.caihan.scframe.widget.webview;

import android.view.animation.Animation;

/**
 * Created by caihan on 2017/3/27.
 */

public interface IWebViewCallBack {

    void closedWebView();

    void onProgressChanged(Animation animation, int newProgress);

    void onReceivedTitle(String title);
}
