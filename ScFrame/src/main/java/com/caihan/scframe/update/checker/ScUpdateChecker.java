package com.caihan.scframe.update.checker;

import com.caihan.scframe.update.ScUpdateUtils;
import com.caihan.scframe.update.agent.ICheckAgent;
import com.caihan.scframe.update.builder.IUpdateChecker;
import com.caihan.scframe.update.error.UpdateError;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.caihan.scframe.update.error.ErrorConfig.CHECK_HTTP_STATUS;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NETWORK_IO;

/**
 * Created by caihan on 2017/5/23.
 * 默认的查询请求
 */
public class ScUpdateChecker implements IUpdateChecker {

    final byte[] mPostData;

    public ScUpdateChecker() {
        mPostData = null;
    }
    public ScUpdateChecker(byte[] data) {
        mPostData = data;
    }

    @Override
    public void check(ICheckAgent agent, String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Accept", "application/json");

            if (mPostData == null) {
                connection.setRequestMethod("GET");
                connection.connect();
            } else {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString(mPostData.length));
                connection.getOutputStream().write(mPostData);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                agent.setInfo(ScUpdateUtils.readString(connection.getInputStream()));
            } else {
                agent.setError(new UpdateError(CHECK_HTTP_STATUS, "" + connection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            agent.setError(new UpdateError(CHECK_NETWORK_IO));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}