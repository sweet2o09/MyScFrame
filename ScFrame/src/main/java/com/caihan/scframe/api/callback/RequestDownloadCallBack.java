package com.caihan.scframe.api.callback;

/**
 * 下载进度回调（主线程，可以直接操作UI）
 *
 * @author caihan
 * @date 2019/3/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface RequestDownloadCallBack {

    /**
     * 下载进度
     *
     * @param progress
     * @param done
     */
    void update(long progress, boolean done);

    /**
     * 开始下载
     */
    void onStart();

    /**
     * 下载并保存完成
     *
     * @param path 文件保存路径
     */
    void onComplete(String path);

    /**
     * 错误
     *
     * @param errorMsg 错误信息
     */
    void onError(String errorMsg);
}
