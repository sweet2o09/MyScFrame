package com.caihan.scframe.update.agent;

import android.content.Context;
import android.os.AsyncTask;

import com.caihan.scframe.update.ScUpdateUtil;
import com.caihan.scframe.update.builder.IUpdateChecker;
import com.caihan.scframe.update.builder.IUpdateDownloader;
import com.caihan.scframe.update.builder.IUpdateParser;
import com.caihan.scframe.update.builder.IUpdatePrompter;
import com.caihan.scframe.update.checker.ScUpdateChecker;
import com.caihan.scframe.update.data.ScUpdateInfo;
import com.caihan.scframe.update.defaultclass.DefaultDialogDownloadListener;
import com.caihan.scframe.update.defaultclass.DefaultDownloadListener;
import com.caihan.scframe.update.defaultclass.DefaultFailureListener;
import com.caihan.scframe.update.defaultclass.DefaultNotificationDownloadListener;
import com.caihan.scframe.update.defaultclass.DefaultUpdateDownloader;
import com.caihan.scframe.update.defaultclass.DefaultUpdateParser;
import com.caihan.scframe.update.defaultclass.DefaultUpdateDialog;
import com.caihan.scframe.update.error.UpdateError;
import com.caihan.scframe.update.listener.OnDownloadListener;
import com.caihan.scframe.update.listener.OnFailureListener;

import java.io.File;

import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NO_NETWORK;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NO_WIFI;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_PARSE;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_UNKNOWN;
import static com.caihan.scframe.update.error.ErrorConfig.UPDATE_IGNORED;
import static com.caihan.scframe.update.error.ErrorConfig.UPDATE_NO_NEWER;

/**
 * Created by caihan on 2017/5/22.
 * 自升级代理类
 */
public class ScUpdateAgent implements ICheckAgent, IUpdateAgent, IDownloadAgent {

    private Context mContext;
    private String mCheckUrl;
    private File mTmpFile;
    private File mApkFile;
    private boolean mIsManual = false;//是否显示错误信息
    private boolean mIsWifiOnly = false;//是否只在WIFI下载

    private ScUpdateInfo mInfo;
    private UpdateError mError = null;

    private IUpdateParser mParser = new DefaultUpdateParser();//检测版本回调解析
    private IUpdateChecker mChecker;//查询请求
    private IUpdateDownloader mDownloader;//下载请求
    private IUpdatePrompter mUpdateDialog;//自升级Dialog
    private OnFailureListener mOnFailureListener;//错误或无版本监听
    private OnDownloadListener mOnDownloadListener;//下载进度对话框监听
    private OnDownloadListener mOnNotificationDownloadListener;//通知栏进度监听

    public ScUpdateAgent(Context context, String checkUrl, boolean isManual, boolean isWifiOnly, int notifyId) {
        mContext = context.getApplicationContext();
        mCheckUrl = checkUrl;
        mIsManual = isManual;
        mIsWifiOnly = isWifiOnly;
        mDownloader = new DefaultUpdateDownloader(mContext);
        mUpdateDialog = new DefaultUpdateDialog(context);
        mOnFailureListener = new DefaultFailureListener(context);
        mOnDownloadListener = new DefaultDialogDownloadListener(context);
        if (notifyId > 0) {
            mOnNotificationDownloadListener = new DefaultNotificationDownloadListener(
                    mContext, notifyId);
        } else {
            mOnNotificationDownloadListener = new DefaultDownloadListener();
        }
    }


    /**
     * 设置检测版本回调解析
     * @param parser
     */
    public void setParser(IUpdateParser parser) {
        mParser = parser;
    }

    /**
     * 设置版本查询请求
     * @param checker
     */
    public void setChecker(IUpdateChecker checker) {
        mChecker = checker;
    }

    /**
     * 设置下载请求
     * @param downloader
     */
    public void setDownloader(IUpdateDownloader downloader) {
        mDownloader = downloader;
    }

    /**
     * 设置新版本更新Dialog
     * @param updateDialog
     */
    public void setUpdateDialog(IUpdatePrompter updateDialog) {
        mUpdateDialog = updateDialog;
    }

    /**
     * 设置通知栏下载监听
     * @param listener
     */
    public void setOnNotificationDownloadListener(OnDownloadListener listener) {
        mOnNotificationDownloadListener = listener;
    }

    /**
     * 设置下载进度对话框监听
     * @param listener
     */
    public void setOnDownloadListener(OnDownloadListener listener) {
        mOnDownloadListener = listener;
    }

    /**
     * 设置错误提示语监听
     * @param listener
     */
    public void setOnFailureListener(OnFailureListener listener) {
        mOnFailureListener = listener;
    }


    public void setInfo(ScUpdateInfo info) {
        mInfo = info;
    }

    @Override
    public ScUpdateInfo getInfo() {
        return mInfo;
    }

    @Override
    public void setInfo(String source) {
        try {
            mInfo = mParser.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
            setError(new UpdateError(CHECK_PARSE));
        }
    }

    @Override
    public void setError(UpdateError error) {
        mError = error;
    }

    @Override
    public void update() {
        mApkFile = new File(mContext.getExternalCacheDir(), mInfo.md5 + ".apk");
        if (ScUpdateUtil.verify(mApkFile, mInfo.md5)) {
            doInstall();
        } else {
            doDownload();
        }
    }

    @Override
    public void ignore() {
        ScUpdateUtil.setIgnore(mContext, getInfo().md5);
    }

    @Override
    public void onStart() {
        if (mInfo.isSilent) {
            mOnNotificationDownloadListener.onStart();
        } else {
            mOnDownloadListener.onStart();
        }
    }

    @Override
    public void onProgress(int progress) {
        if (mInfo.isSilent) {
            mOnNotificationDownloadListener.onProgress(progress);
        } else {
            mOnDownloadListener.onProgress(progress);
        }
    }

    @Override
    public void onFinish() {
        if (mInfo.isSilent) {
            mOnNotificationDownloadListener.onFinish();
        } else {
            mOnDownloadListener.onFinish();
        }
        if (mError != null) {
            mOnFailureListener.onFailure(mError);
        } else {
            mTmpFile.renameTo(mApkFile);
            if (mInfo.isAutoInstall) {
                doInstall();
            }
        }

    }


    public void check() {
        ScUpdateUtil.log("check");
        if (mIsWifiOnly) {
            if (ScUpdateUtil.checkWifi(mContext)) {
                doCheck();
            } else {
                doFailure(new UpdateError(CHECK_NO_WIFI));
            }
        } else {
            if (ScUpdateUtil.checkNetwork(mContext)) {
                doCheck();
            } else {
                doFailure(new UpdateError(CHECK_NO_NETWORK));
            }
        }
    }


    void doCheck() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                if (mChecker == null) {
                    mChecker = new ScUpdateChecker();
                }
                mChecker.check(ScUpdateAgent.this, mCheckUrl);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                doCheckFinish();
            }
        }.execute();
    }

    void doCheckFinish() {
        ScUpdateUtil.log("check finish");
        UpdateError error = mError;
        if (error != null) {
            doFailure(error);
        } else {
            ScUpdateInfo info = getInfo();
            if (info == null) {
                doFailure(new UpdateError(CHECK_UNKNOWN));
            } else if (!info.hasUpdate) {
                doFailure(new UpdateError(UPDATE_NO_NEWER));
            } else if (ScUpdateUtil.isIgnore(mContext, info.md5)) {
                doFailure(new UpdateError(UPDATE_IGNORED));
            } else {
                ScUpdateUtil.log("update md5" + mInfo.md5);
                ScUpdateUtil.ensureExternalCacheDir(mContext);
                ScUpdateUtil.setUpdate(mContext, mInfo.md5);
                mTmpFile = new File(mContext.getExternalCacheDir(), info.md5);
                mApkFile = new File(mContext.getExternalCacheDir(), info.md5 + ".apk");

                if (ScUpdateUtil.verify(mApkFile, mInfo.md5)) {
                    doInstall();
                } else if (info.isSilent) {
                    doDownload();
                } else {
                    doPrompt();
                }
            }
        }

    }

    void doPrompt() {
        mUpdateDialog.prompt(this);
    }

    void doDownload() {
        mDownloader.download(this, mInfo.url, mTmpFile);
    }

    void doInstall() {
        ScUpdateUtil.install(mContext, mApkFile, mInfo.isForce);
    }

    void doFailure(UpdateError error) {
        if (mIsManual || error.isError()) {
            mOnFailureListener.onFailure(error);
        }
    }

}