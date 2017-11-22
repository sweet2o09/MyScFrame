package com.caihan.scframe.widget.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.caihan.scframe.R;


/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * 辅助WebView处理 JavaScript 的对话框、网站图标、网站title、加载进度等
 */
public class ScWebChromeClient extends WebChromeClient {

    private Animation animation;
    private IWebViewCallBack mCallBack;
    private IWebViewOpenFileCallBack mOpenFileCallBack;
    private Context mContext;

    public ScWebChromeClient(Context context) {
        this.mContext = context;
    }

    public void setCallBack(IWebViewCallBack callBack) {
        mCallBack = callBack;
    }

    public void setOpenFileCallBack(IWebViewOpenFileCallBack openFileCallBack) {
        mOpenFileCallBack = openFileCallBack;
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
    public void onGeolocationPermissionsShowPrompt(final String origin,
                                                   final GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    //--end--HTML5定位--

    //--start--多窗口的问题--
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog,
                                  boolean isUserGesture, Message resultMsg) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(view);
        resultMsg.sendToTarget();
        return true;
    }
    //--end--多窗口的问题--

    /**
     * 警告框(WebView上alert无效，需要定制WebChromeClient处理弹出)
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
//        LogUtils.d("XXX","onJsAlert = " + message);
//        result.confirm();//这里必须调用，否则页面会阻塞造成假死
        return super.onJsAlert(view,url,message,result);
    }

    /**
     * 确定框
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//        LogUtils.d("XXX","onJsConfirm = " + message);
        return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 提示框
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {
//        LogUtils.d("XXX","onJsPrompt = " + message);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mCallBack != null) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.web_view_animation);
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

    /**
     * openFileChooser方法为隐藏方法
     */
    //The undocumented magic method override
    //Eclipse will swear at you if you try to put @Override here
    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (mOpenFileCallBack != null) {
            mOpenFileCallBack.openFile(uploadMsg);
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        if (mOpenFileCallBack != null) {
            mOpenFileCallBack.openFile(uploadMsg);
        }
    }

    //For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (mOpenFileCallBack != null) {
            mOpenFileCallBack.openFile(uploadMsg);
        }

    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (mOpenFileCallBack != null) {
            mOpenFileCallBack.openFileChooserImplForAndroid5(filePathCallback, fileChooserParams);
        }
//        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        return true;
    }


}
