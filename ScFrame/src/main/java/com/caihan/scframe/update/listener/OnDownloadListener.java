package com.caihan.scframe.update.listener;

/**
 * Created by caihan on 2017/5/22.
 * 通知栏进度,下载进度的对话框接口
 */
public interface OnDownloadListener {

    void onStart();

    void onProgress(int progress);

    void onFinish();
}