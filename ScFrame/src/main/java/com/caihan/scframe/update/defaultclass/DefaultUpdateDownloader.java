package com.caihan.scframe.update.defaultclass;

import android.content.Context;

import com.caihan.scframe.update.agent.IDownloadAgent;
import com.caihan.scframe.update.builder.IUpdateDownloader;
import com.caihan.scframe.update.downloader.ScUpdateDownloader;

import java.io.File;

/**
 * Created by caihan on 2017/5/22.
 * 默认的下载请求
 */
public class DefaultUpdateDownloader implements IUpdateDownloader {

    final Context mContext;

    public DefaultUpdateDownloader(Context context) {
        mContext = context;
    }

    @Override
    public void download(IDownloadAgent agent, String url, File temp) {
        new ScUpdateDownloader(agent, mContext, url, temp).execute();
    }
}
