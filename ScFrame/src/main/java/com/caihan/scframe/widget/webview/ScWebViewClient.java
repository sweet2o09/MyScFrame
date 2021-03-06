package com.caihan.scframe.widget.webview;

import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.PhoneUtils;


/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * 辅助WebView处理加载网页
 */
public class ScWebViewClient extends WebViewClient {

    private WebView mWebView;
    private Context mContext;
    private OnUrlBodyListener mBodyListener;

    public ScWebViewClient() {
    }

    public ScWebViewClient(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    public void setBodyListener(OnUrlBodyListener bodyListener) {
        mBodyListener = bodyListener;
    }

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     * API24 版本已经废弃了
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //假定传入进来的 url = "js://openActivity?arg1=111&arg2=222"，
//            // 代表需要打开本地页面，并且带入相应的参数
//            Uri uri = Uri.parse(url);
//            String scheme = uri.getScheme();
//            //如果 scheme 为 js，代表为预先约定的 js 协议
//            if (scheme.equals("js")) {
//                //如果 authority 为 openActivity，代表 web 需要打开一个本地的页面
//                if (uri.getAuthority().equals("openActivity")) {
//                    //解析 web 页面带过来的相关参数
//                    HashMap<String, String> params = new HashMap<>();
//                    Set<String> collection = uri.getQueryParameterNames();
//                    for (String name : collection) {
//                        params.put(name, uri.getQueryParameter(name));
//                    }
//                    Intent intent = new Intent(getContext(), MainActivity.class);
//                    intent.putExtra("params", params);
//                    getContext().startActivity(intent);
//                }
//                //代表应用内部处理完成
//                return true;
//            }
        if (url.startsWith("tel:")) {
            //唤醒拨号盘
            PhoneUtils.dial(Uri.parse(url).toString());
            return true;
        }
        if (mBodyListener != null) {
            mBodyListener.shouldOverrideUrlLoading(view,url);
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //在网络情况较差的情况下,等页面finish后再发起图片加载,减少loading时间
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);
        }
        if (mBodyListener != null){
            mBodyListener.onPageFinished(view,url);
        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        String CookieStr = cookieManager.getCookie(url);
//        LogUtils.d("url = " + url + " / Cookies = " + CookieStr);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // handler.cancel();// Android默认的处理方式
//        handler.proceed();// 接受所有网站的证书
        // handleMessage(Message msg);// 进行其他处理
    }

    public interface OnUrlBodyListener {
        void shouldOverrideUrlLoading(WebView view, String url);

        void onPageFinished(WebView view, String url);
    }
}
