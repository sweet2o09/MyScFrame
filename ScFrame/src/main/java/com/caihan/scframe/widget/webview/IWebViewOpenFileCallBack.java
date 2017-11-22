package com.caihan.scframe.widget.webview;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * 作者：caihan
 * 创建时间：2017/10/4
 * 邮箱：93234929@qq.com
 * 实现功能：解决H5界面需要唤起上传图片功能
 * 备注：
 */

public interface IWebViewOpenFileCallBack {
    /**
     * Android <3.0系统
     *
     * @param uploadMsg
     */
    void openFile(ValueCallback<Uri> uploadMsg);

    /**
     * Android 3.0+系统
     *
     * @param uploadMsg
     * @param acceptType
     */
    void openFile(ValueCallback uploadMsg, String acceptType);

    /**
     * Android >4.1系统
     *
     * @param uploadMsg
     * @param acceptType
     * @param capture
     */
    void openFile(ValueCallback<Uri> uploadMsg, String acceptType, String capture);

    /**
     * Android >5.0系统
     *
     * @param uploadMsg
     * @param fileChooserParams
     */
    void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg,
                                        WebChromeClient.FileChooserParams fileChooserParams);
}
