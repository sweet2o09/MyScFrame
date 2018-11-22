package com.caihan.scframe.utils.webview;

import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * WebView生命周期优化工具
 *
 * @author caihan
 * @date 2018/11/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class WebViewLifecycleUtils {

    private WebView mWebView;

    public WebViewLifecycleUtils(WebView webView) {
        this.mWebView = webView;
    }

    public void onResume() {
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    public void onPause() {
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    public void onDestroy() {
        if (mWebView == null){
            return;
        }
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        //清除历史记录
        mWebView.clearHistory();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        //移除WebView所有的View对象
        mWebView.removeAllViews();
        ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        //销毁此的WebView的内部状态
        mWebView.destroy();
        mWebView = null;
    }
}
