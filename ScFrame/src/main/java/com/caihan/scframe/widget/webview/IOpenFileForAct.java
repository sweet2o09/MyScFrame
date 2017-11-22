package com.caihan.scframe.widget.webview;

import android.content.Intent;

/**
 * 作者：caihan
 * 创建时间：2017/10/4
 * 邮箱：93234929@qq.com
 * 实现功能：
 * 备注：要和onActivityResult配合使用
 */

public interface IOpenFileForAct {
    void openFile(Intent intent, int requestCode);
}
