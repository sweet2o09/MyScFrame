package com.caihan.scframe.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.caihan.scframe.utils.motionevent.GestureUtils;
import com.caihan.scframe.utils.network.ScNetworkUtils;

/**
 * WebView 简单封装
 * 需要先加入权限
 * <uses-permission android:name="android.permission.INTERNET"/>
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 */
public class ScWebView extends WebView implements View.OnLongClickListener{

    private Context mContext;
    private GestureUtils mGestureUtils;
    private long mOldTime;
    private String mWebViewUrl = "";
    private boolean mTouchEventToParent = false;//是否把事件交给父控件处理
    private boolean mLessThanLOLLIPOP = false;//SDK小于21是否做处理
    private boolean mNoCopy = true;//禁止长按出现复制黏贴
    private boolean mNoClickL = false;//不执行Click事件
    private boolean mIsTop = false;//是否已经到顶
    private IWebViewCallBack mCallBack;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;

    public ScWebView(Context context) {
        this(context, null);
    }

    /**
     * 不能直接调用this(context, attrs,0),最后style是0的话，会导致无法响应点击动作。
     * 但是如果直接把最后一位写成 com.android.internal.R.attr.webViewStyle 编译时会弹出错误提示，原因：
     * You cannot access id's of com.android.internal.R at compile time, but you can access the
     * defined internal resources at runtime and get the resource by name.
     * You should be aware that this is slower than direct access and there is no guarantee.
     */
    public ScWebView(Context context, AttributeSet attrs) {
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle","attr","android"));
    }

    public ScWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * WebView回调
     *
     * @param callBack
     * @return
     */
    public ScWebView setCallBack(IWebViewCallBack callBack) {
        this.mCallBack = callBack;
        return this;
    }

    /**
     * 是否把事件交给父控件处理,默认false
     *
     * @param touchEventToParent
     * @return
     */
    public ScWebView setTouchEventToParent(boolean touchEventToParent) {
        mTouchEventToParent = touchEventToParent;
        return this;
    }

    /**
     * 是否对SDK小于21的手机进行跳转网页处理,默认false
     *
     * @param lessThanLOLLIPOP
     * @return
     */
    public ScWebView setLessThanLOLLIPOP(boolean lessThanLOLLIPOP) {
        mLessThanLOLLIPOP = lessThanLOLLIPOP;
        return this;
    }

    public ScWebView setNoCopy(boolean noCopy) {
        mNoCopy = noCopy;
        return this;
    }

    public ScWebView setNoClickL(boolean noClickL) {
        mNoClickL = noClickL;
        return this;
    }

    /**
     * 是否添加缓存数据,默认false
     *
     * @param save
     * @return
     */
    public ScWebView setDataSaveStatus(boolean save) {
        if (save) {
            //缓存数据
            saveData(getSettings());
        }
        return this;
    }

    private void init(Context context) {
        mContext = context;
        WebSettings mWebSettings = getSettings();
        //启用或禁止WebView访问文件数据
        mWebSettings.setAllowFileAccess(true);
        //设置页面是否支持缩放
        mWebSettings.setSupportZoom(true);
        // 网页内容的宽度是否可大于WebView控件的宽度
        mWebSettings.setLoadWithOverviewMode(false);
        // 设置此属性，可任意比例缩放。
        mWebSettings.setUseWideViewPort(true);
        // 排版适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        /**
         * 调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
         * 设置了这个属性后我们才能在 WebView 里与我们的 Js 代码进行交互，对于 WebApp 是非常重要的，默认是 false
         */
        mWebSettings.setJavaScriptEnabled(true);
        // WebView是否支持多个窗口。
        mWebSettings.setSupportMultipleWindows(true);

        /**
         * 设置 JS 是否可以打开 WebView 新窗口
         * html中的_bank标签就是新建窗口打开，
         * 有时会打不开，需要添加下面的代码并复写 WebChromeClient的onCreateWindow方法
         */
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);
        }
        mWebViewClient = new ScWebViewClient();
        mWebChromeClient = new ScWebChromeClient(context);
        setWebViewClient(mWebViewClient);
        setWebChromeClient(mWebChromeClient);
        mGestureUtils = new GestureUtils();
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        // 设置缓存模式
        if (ScNetworkUtils.ping()) {
            //根据cache-control决定是否从网络上取数据。
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网，则从本地获取，即离线加载
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 使用localStorage则必须打开
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        // 启动应用缓存
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAppCachePath(mContext.getCacheDir().getAbsolutePath());
    }

    @Override
    public void loadUrl(String url) {
        mWebViewUrl = url;
        if (mLessThanLOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            openBrowser();
        } else {
            if (mCallBack != null && mWebChromeClient instanceof ScWebChromeClient) {
                ((ScWebChromeClient) mWebChromeClient).setCallBack(mCallBack);
            }
            super.loadUrl(url);
        }
    }

    private void openBrowser() {
        //urlText是一个文本输入框，输入网站地址
        //Uri  是统一资源标识符
        Uri uriBrowser = Uri.parse(mWebViewUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriBrowser);
        mContext.startActivity(intent);
        if (mCallBack != null) {
            mCallBack.closedWebView();
        }
    }

    /**
     * ScWebview退出时，进行销毁操作
     */
    @Override
    public void destroy() {
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        clearHistory();
        ((ViewGroup) getParent()).removeView(this);
        super.destroy();
    }

    public boolean back(Activity activity, int keyCode, KeyEvent event, String mUrl) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                clearHistory();
                loadUrl(mUrl);
            } else if (canGoBack()) {
                goBack();
            } else {
                activity.finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mGestureUtils.actionDown(event);
                this.getParent().requestDisallowInterceptTouchEvent(true);
                mNoClickL = false;
                mIsTop = isTop();
                break;

            case MotionEvent.ACTION_UP:
                mGestureUtils.actionUp(event);
                if (mNoClickL) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                mGestureUtils.actionMove(event);
                if (mTouchEventToParent && mIsTop &&
                        mGestureUtils.getGesture(GestureUtils.Gesture.PullDown)) {
                    /**
                     * 这句话是告诉父view，我的事件是否自己处理
                     * 如果需要父类处理的话传递false
                     */
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    //dispatchTouchEvent —— true不分发，false 是分发（默认）
                    mNoClickL = true;
                    return true;
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 判断是否滚动到顶部
     */
    private boolean isTop() {
        return getScrollY() == 0 || getContentHeight() * getScale() < getHeight() + getScrollY();
    }

    @Override
    public boolean onLongClick(View v) {
        return mNoCopy;
    }
}
