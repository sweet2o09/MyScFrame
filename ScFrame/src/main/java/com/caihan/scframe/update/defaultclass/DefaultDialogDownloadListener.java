package com.caihan.scframe.update.defaultclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.caihan.scframe.update.listener.OnDownloadListener;

/**
 * Created by caihan on 2017/5/22.
 * 默认的下载进度对话框监听
 */
public class DefaultDialogDownloadListener implements OnDownloadListener {
    private Context mContext;
    private ProgressDialog mDialog;

    public DefaultDialogDownloadListener(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
        if (mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("下载中...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
            mDialog = dialog;
        }
    }

    @Override
    public void onProgress(int i) {
        if (mDialog != null) {
            mDialog.setProgress(i);
        }
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
