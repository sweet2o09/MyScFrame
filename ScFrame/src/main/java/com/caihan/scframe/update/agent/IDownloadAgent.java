package com.caihan.scframe.update.agent;

import com.caihan.scframe.update.data.ScUpdateInfo;
import com.caihan.scframe.update.error.UpdateError;
import com.caihan.scframe.update.listener.OnDownloadListener;

public interface IDownloadAgent extends OnDownloadListener {
    ScUpdateInfo getInfo();

    void setError(UpdateError error);
}