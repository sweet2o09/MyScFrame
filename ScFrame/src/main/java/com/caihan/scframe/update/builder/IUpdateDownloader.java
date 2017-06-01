package com.caihan.scframe.update.builder;

import com.caihan.scframe.update.agent.IDownloadAgent;

import java.io.File;

/**
 * Created by caihan on 2017/5/23.
 * 下载接口
 */
public interface IUpdateDownloader {
    void download(IDownloadAgent agent, String url, File temp);
}