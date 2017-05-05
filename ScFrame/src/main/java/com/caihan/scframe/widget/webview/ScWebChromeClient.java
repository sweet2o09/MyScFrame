package com.caihan.scframe.widget.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.caihan.scframe.R;


/**
 * Created by caihan on 2017/5/5.
 */

public class ScWebChromeClient extends WebChromeClient {

    private Animation animation;
    private IWebViewCallBack mCallBack;
    private Context mContext;

    public ScWebChromeClient(Context context) {
        this.mContext = context;
    }

    public void setCallBack(IWebViewCallBack callBack) {
        mCallBack = callBack;
    }

    //--start--HTML5定位--
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    //--end--HTML5定位--

    //--start--多窗口的问题--
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(view);
        resultMsg.sendToTarget();
        return true;
    }
    //--end--多窗口的问题--

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mCallBack != null) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.web_animation);
            mCallBack.onProgressChanged(animation, newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (mCallBack != null) {
            mCallBack.onReceivedTitle(title);
        }
    }
}
