package com.caihan.scframe.update.builder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.caihan.scframe.update.agent.ScUpdateAgent;
import com.caihan.scframe.update.checker.ScUpdateChecker;
import com.caihan.scframe.update.listener.OnDownloadListener;
import com.caihan.scframe.update.listener.OnFailureListener;

import java.nio.charset.Charset;

/**
 * Created by caihan on 2017/5/22.
 * 下载管理器Builder
 */
public class ScUpdateBuilder {
    private Context mContext;
    private String mCheckUrl;
    private byte[] mPostData;
    private boolean mIsManual;
    private boolean mIsWifiOnly;
    private int mNotifyId = 0;

    private OnDownloadListener mOnNotificationDownloadListener;
    private OnDownloadListener mOnDownloadListener;
    private IUpdatePrompter mPrompter;
    private OnFailureListener mOnFailureListener;

    private IUpdateParser mParser;
    private IUpdateChecker mChecker;
    private IUpdateDownloader mDownloader;

    private static long sLastTime;

    public ScUpdateBuilder(Context context) {
        mContext = context;
    }

    /**
     * 设置查询地址
     * @param checkUrl
     * @return
     */
    public ScUpdateBuilder setCheckUrl(String checkUrl) {
        mCheckUrl = checkUrl;
        return this;
    }

    public ScUpdateBuilder setPostData(@NonNull byte[] data) {
        mPostData = data;
        return this;
    }

    /**
     * 设置请求参数
     * <p>
     * UpdateManager.create(this).setCheckUrl(mCheckUrl).setPostData("param=abc&param2=xyz").check();
     *
     * @param data
     * @return
     */
    public ScUpdateBuilder setPostData(@NonNull String data) {
        mPostData = data.getBytes(Charset.forName("UTF-8"));
        return this;
    }

    /**
     * 设置通知栏Id
     * @param notifyId
     * @return
     */
    public ScUpdateBuilder setNotifyId(int notifyId) {
        mNotifyId = notifyId;
        return this;
    }

    /**
     * 是否返UpdateError
     * @param isManual
     * @return
     */
    public ScUpdateBuilder setManual(boolean isManual) {
        mIsManual = isManual;
        return this;
    }

    /**
     * 是否只允许WIFI下更新
     * @param isWifiOnly
     * @return
     */
    public ScUpdateBuilder setWifiOnly(boolean isWifiOnly) {
        mIsWifiOnly = isWifiOnly;
        return this;
    }

    /**
     * 定制检测版本回调解析过程
     *
     * @param parser
     * @return
     */
    public ScUpdateBuilder setParser(@NonNull IUpdateParser parser) {
        mParser = parser;
        return this;
    }

    /**
     * 定制检测版本查询
     *
     * @param checker
     * @return
     */
    public ScUpdateBuilder setChecker(@NonNull IUpdateChecker checker) {
        mChecker = checker;
        return this;
    }

    /**
     * 定制下载
     * @param downloader
     * @return
     */
    public ScUpdateBuilder setDownloader(@NonNull IUpdateDownloader downloader) {
        mDownloader = downloader;
        return this;
    }

    /**
     * 定制更新版本对话框
     * @param prompter
     * @return
     */
    public ScUpdateBuilder setPrompter(@NonNull IUpdatePrompter prompter) {
        mPrompter = prompter;
        return this;
    }

    /**
     * 定制通知栏进度
     * @param listener
     * @return
     */
    public ScUpdateBuilder setOnNotificationDownloadListener(@NonNull OnDownloadListener listener) {
        mOnNotificationDownloadListener = listener;
        return this;
    }

    /**
     * 定制下载进度的对话框
     * @param listener
     * @return
     */
    public ScUpdateBuilder setOnDownloadListener(@NonNull OnDownloadListener listener) {
        mOnDownloadListener = listener;
        return this;
    }

    /**
     * 定制没有新版本或出错返回提示
     * @param listener
     * @return
     */
    public ScUpdateBuilder setOnFailureListener(@NonNull OnFailureListener listener) {
        mOnFailureListener = listener;
        return this;
    }

    /**
     * 查询
     */
    public void check() {
        long now = System.currentTimeMillis();
        if (now - sLastTime < 3000) {
            return;
        }
        sLastTime = now;

        if (TextUtils.isEmpty(mCheckUrl)) {
            //检测版本地址为空
            return;
        }

        ScUpdateAgent agent = new ScUpdateAgent(mContext, mCheckUrl, mIsManual, mIsWifiOnly, mNotifyId);
        if (mOnNotificationDownloadListener != null) {
            agent.setOnNotificationDownloadListener(mOnNotificationDownloadListener);
        }
        if (mOnDownloadListener != null) {
            agent.setOnDownloadListener(mOnDownloadListener);
        }
        if (mOnFailureListener != null) {
            agent.setOnFailureListener(mOnFailureListener);
        }
        if (mChecker != null) {
            agent.setChecker(mChecker);
        } else {
            agent.setChecker(new ScUpdateChecker(mPostData));
        }
        if (mParser != null) {
            agent.setParser(mParser);
        }
        if (mDownloader != null) {
            agent.setDownloader(mDownloader);
        }
        if (mPrompter != null) {
            agent.setUpdateDialog(mPrompter);
        }
        agent.check();
    }
}
