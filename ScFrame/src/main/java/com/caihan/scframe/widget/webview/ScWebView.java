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
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.caihan.scframe.utils.motionevent.ScGestureUtils;
import com.caihan.scframe.utils.network.ScNetworkUtils;

import static android.app.Activity.RESULT_OK;

/**
 * WebView 简单封装
 * 需要先加入权限
 * <uses-permission android:name="android.permission.INTERNET"/>
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 */
public class ScWebView extends WebView implements View.OnLongClickListener, IWebViewOpenFileCallBack {

    private Context mContext;
    private ScGestureUtils mGestureUtils;
    private long mOldTime;
    private String mWebViewUrl = "";
    private boolean mTouchEventToParent = false;//是否把事件交给父控件处理
    private boolean mTouchForeverToParent = false;//点击的时候就把事件交给父控件处理
    private boolean mLessThanLOLLIPOP = false;//SDK小于21是否做处理
    private boolean mNoCopy = true;//禁止长按出现复制黏贴
    private boolean mNoClickL = false;//不执行Click事件
    private boolean mIsTop = false;//是否已经到顶
    private boolean mIsBottom = false;//是否已经到底部
    private IWebViewCallBack mCallBack;
    private ScWebViewClient mWebViewClient;
    private ScWebChromeClient mWebChromeClient;
    private ScWebGesture mGesture;
    private ScWebViewClient.OnUrlBodyListener mBodyListener;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private IOpenFileForAct mOpenFileForAct;

    @Override
    public void openFile(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        if (mOpenFileForAct != null) {
            mOpenFileForAct.openFile(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }
    }

    @Override
    public void openFile(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        if (mOpenFileForAct != null) {
            mOpenFileForAct.openFile(Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }
    }

    @Override
    public void openFile(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        if (mOpenFileForAct != null) {
            mOpenFileForAct.openFile(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }
    }

    @Override
    public void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg,
                                               WebChromeClient.FileChooserParams fileChooserParams) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        if (mOpenFileForAct != null) {
            mOpenFileForAct.openFile(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5) return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            //content://com.android.providers.media.documents/document/image%3A5431
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    public enum ScWebGesture {
        PullUp, PullDown, PullLeft, PullRight, Up_Down, Left_Right
    }

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
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle", "attr", "android"));
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
     * WebView调用上传图片功能回调
     *
     * @param openFileForAct
     * @return
     */
    public ScWebView setOpenFileForAct(IOpenFileForAct openFileForAct) {
        mOpenFileForAct = openFileForAct;
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
     * 点击的时候就把事件交给父控件处理,默认false
     *
     * @param touchForeverToParent
     * @return
     */
    public ScWebView setTouchForeverToParent(boolean touchForeverToParent) {
        mTouchForeverToParent = touchForeverToParent;
        mTouchEventToParent = touchForeverToParent;
        return this;
    }

    /**
     * 设置需要交给父控件处理的手势
     *
     * @param gestures
     * @return
     */
    public ScWebView setGesture(ScWebGesture gestures) {
        mGesture = gestures;
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

    public ScWebView setBodyListener(ScWebViewClient.OnUrlBodyListener bodyListener) {
        mBodyListener = bodyListener;
        if (mWebViewClient != null) {
            mWebViewClient.setBodyListener(mBodyListener);
        }
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
        } else {
            //不使用缓存：
            this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        return this;
    }

    private void init(Context context) {
        mContext = context;
        WebSettings mWebSettings = getSettings();

        //是否允许在WebView中访问内容URL（Content Url），
        // 默认允许。内容Url访问允许WebView从安装在系统中的内容提供者载入内容
//        mWebSettings.setAllowContentAccess(true);

        //是否允许访问文件，默认允许。
        // 注意，这里只是允许或禁止对文件系统的访问，
        // Assets 和 resources 文件使用file:///android_asset和file:///android_res仍是可访问的。
        mWebSettings.setAllowFileAccess(true);


        //WebView是否支持使用屏幕上的缩放控件和手势进行缩放，默认值true。
        mWebSettings.setSupportZoom(true);

        // 是否使用内置的缩放机制,内置的缩放机制包括屏幕上的缩放控件（浮于WebView内容之上）和缩放手势的运用。
        // 通过setDisplayZoomControls(boolean)可以控制是否显示这些控件，默认值为false。
        mWebSettings.setBuiltInZoomControls(true);

        // 是否允许WebView度超出以概览的方式载入页面，默认false。即缩小内容以适应屏幕宽度。
        // 该项设置在内容宽度超出WebView控件的宽度时生效，例如当getUseWideViewPort() 返回true时。
        mWebSettings.setLoadWithOverviewMode(true);

        // WebView是否支持HTML的“viewport”标签或者使用wide viewport。
        mWebSettings.setUseWideViewPort(true);

        // 设置布局，会引起WebView的重新布局（relayout）,默认值NARROW_COLUMNS
        //NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
        //NORMAL：正常显示不做任何渲染
        //SINGLE_COLUMN：把所有内容放大webview等宽的一列中
//        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        //设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发。
//        mWebSettings.setMediaPlaybackRequiresUserGesture(false);

        /**
         * 调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
         * 设置了这个属性后我们才能在 WebView 里与我们的 Js 代码进行交互，对于 WebApp 是非常重要的，默认是 false
         */
        mWebSettings.setJavaScriptEnabled(true);

        // 设置WebView是否支持多窗口。
        // 如果设置为true，主程序要实现onCreateWindow(WebView, boolean, boolean, Message)，默认false。
//        mWebSettings.setSupportMultipleWindows(false);

        /**
         * 设置 JS 是否可以打开 WebView 新窗口
         * html中的_bank标签就是新建窗口打开，
         * 有时会打不开，需要添加下面的代码并复写 WebChromeClient的onCreateWindow方法
         * 让JavaScript自动打开窗口，默认false
         */
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        //当一个安全的来源（origin）试图从一个不安全的来源加载资源时配置WebView的行为
        //不鼓励使用MIXED_CONTENT_ALWAYS_ALLOW,LOLLIPOP版本默认值MIXED_CONTENT_NEVER_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //WebView是否下载图片资源，默认为true
        //如果该设置项的值由false变为true，WebView展示的内容所引用的所有的图片资源将自动下载。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);
        }

        DomStorageDatabase(mWebSettings);

        //获取地理位置,定位是否可用，默认为true
//        mWebSettings.setGeolocationEnabled(true);

        mWebViewClient = new ScWebViewClient();
        if (mBodyListener != null) {
            mWebViewClient.setBodyListener(mBodyListener);
        }
        mWebChromeClient = new ScWebChromeClient(context);
        setWebViewClient(mWebViewClient);
        setWebChromeClient(mWebChromeClient);
        mGestureUtils = new ScGestureUtils();
        this.setOnLongClickListener(this);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        appCacheDatabase(mWebSettings);
    }

    /**
     * Dom Storage（Web Storage）存储机制：
     * 配合前端使用，使用时需要打开 DomStorage 开关。
     * DOM存储API是否可用，默认false。
     *
     * @param mWebSettings
     */
    private void DomStorageDatabase(WebSettings mWebSettings) {
        mWebSettings.setDomStorageEnabled(true);
    }

    /**
     * Web SQL Database 存储机制：
     * 不推荐使用了
     *
     * @param mWebSettings
     */
    private void webSQLDatabase(WebSettings mWebSettings) {
        mWebSettings.setDatabaseEnabled(true);
        final String dbPath = mContext.getDir("db", Context.MODE_PRIVATE).getPath();
        mWebSettings.setDatabasePath(dbPath);
    }

    /**
     * Application Cache 存储机制
     * 不推荐使用了
     *
     * @param mWebSettings
     */
    private void appCacheDatabase(WebSettings mWebSettings) {
//        mWebSettings.setAppCacheEnabled(true);
//        final String cachePath = mContext.getDir("cache",Context.MODE_PRIVATE).getPath();
//        mWebSettings.setAppCachePath(cachePath);
//        mWebSettings.setAppCacheMaxSize(5*1024*1024);
        // 设置缓存模式
        if (ScNetworkUtils.ping()) {
            //根据cache-control决定是否从网络上取数据。
            //重写使用缓存的方式，默认值LOAD_DEFAULT。
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网，则从本地获取，即离线加载
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 使用localStorage则必须打开
        mWebSettings.setDomStorageEnabled(true);
        //数据库存储API是否可用，默认值false。如何正确设置数据存储API参见setDatabasePath(String)。
        mWebSettings.setDatabaseEnabled(true);
        // 应用缓存API是否可用，默认值false, 结合setAppCachePath(String)使用。
        mWebSettings.setAppCacheEnabled(true);
        //设置应用缓存文件的路径。为了让应用缓存API可用，
        // 此方法必须传入一个应用可写的路径。该方法只会执行一次，重复调用会被忽略。
        mWebSettings.setAppCachePath(mContext.getCacheDir().getAbsolutePath());
    }

    @Override
    public void loadUrl(String url) {
        mWebViewUrl = url;
        if (mLessThanLOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            openBrowser();
        } else {
            if (mCallBack != null && mWebChromeClient instanceof ScWebChromeClient) {
                mWebChromeClient.setCallBack(mCallBack);
            }
            mWebChromeClient.setOpenFileCallBack(this);
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
        clearCache(true);
        clearHistory();
        ((ViewGroup) getParent()).removeView(this);
        //清理Webview缓存数据库
        try {
            mContext.deleteDatabase("webview.db");
            mContext.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBodyListener = null;
        mCallBack = null;
        super.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //获取历史列表
            WebBackForwardList mWebBackForwardList = copyBackForwardList();
            //判断当前历史列表是否最顶端,其实canGoBack已经判断过
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
                this.getParent().requestDisallowInterceptTouchEvent(!mTouchForeverToParent);
                mGestureUtils.actionDown(event);
                mNoClickL = false;
                mIsTop = isTop();
                mIsBottom = isBottom();
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
                        mGestureUtils.getGesture(ScGestureUtils.Gesture.PullDown)) {
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
        return getScrollY() <= 0 || getContentHeight() * getScale() < getHeight() + getScrollY();
    }

    private boolean isBottom() {
        return getHeight() + getScrollY() >= getContentHeight() * getScale();
    }

    @Override
    public boolean onLongClick(View v) {
        return mNoCopy;
    }
}
