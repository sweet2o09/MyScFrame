package com.caihan.scframe.update.error;

import android.util.SparseArray;

import static com.caihan.scframe.update.error.ErrorConfig.CHECK_HTTP_STATUS;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NETWORK_IO;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NO_NETWORK;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_NO_WIFI;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_PARSE;
import static com.caihan.scframe.update.error.ErrorConfig.CHECK_UNKNOWN;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_CANCELLED;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_DISK_IO;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_DISK_NO_SPACE;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_HTTP_STATUS;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_INCOMPLETE;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_NETWORK_BLOCKED;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_NETWORK_IO;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_NETWORK_TIMEOUT;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_UNKNOWN;
import static com.caihan.scframe.update.error.ErrorConfig.DOWNLOAD_VERIFY;
import static com.caihan.scframe.update.error.ErrorConfig.UPDATE_IGNORED;
import static com.caihan.scframe.update.error.ErrorConfig.UPDATE_NO_NEWER;

/**
 * Created by caihan on 2017/5/22.
 * 错误码解析
 */
public class UpdateError extends Throwable {

    public final int code;

    public UpdateError(int code) {
        this(code, null);
    }

    public UpdateError(int code, String message) {
        super(make(code, message));
        this.code = code;
    }

    public boolean isError() {
        return code >= 2000;
    }

    @Override
    public String toString() {
        if (isError()) {
            return "[" + code + "]" + getMessage();
        }
        return getMessage();
    }

    private static String make(int code, String message) {
        String m = messages.get(code);
        if (m == null) {
            return message;
        }
        if (message == null) {
            return m;
        }
        return m + "(" + message + ")";
    }

    public static final SparseArray<String> messages = new SparseArray<>();

    static {

        messages.append(UPDATE_IGNORED, "该版本已经忽略");
        messages.append(UPDATE_NO_NEWER, "已经是最新版了");

        messages.append(CHECK_UNKNOWN, "查询更新失败：未知错误");
        messages.append(CHECK_NO_WIFI, "查询更新失败：没有 WIFI");
        messages.append(CHECK_NO_NETWORK, "查询更新失败：没有网络");
        messages.append(CHECK_NETWORK_IO, "查询更新失败：网络异常");
        messages.append(CHECK_HTTP_STATUS, "查询更新失败：错误的HTTP状态");
        messages.append(CHECK_PARSE, "查询更新失败：解析错误");

        messages.append(DOWNLOAD_UNKNOWN, "下载失败：未知错误");
        messages.append(DOWNLOAD_CANCELLED, "下载失败：下载被取消");
        messages.append(DOWNLOAD_DISK_NO_SPACE, "下载失败：磁盘空间不足");
        messages.append(DOWNLOAD_DISK_IO, "下载失败：磁盘读写错误");
        messages.append(DOWNLOAD_NETWORK_IO, "下载失败：网络异常");
        messages.append(DOWNLOAD_NETWORK_BLOCKED, "下载失败：网络中断");
        messages.append(DOWNLOAD_NETWORK_TIMEOUT, "下载失败：网络超时");
        messages.append(DOWNLOAD_HTTP_STATUS, "下载失败：错误的HTTP状态");
        messages.append(DOWNLOAD_INCOMPLETE, "下载失败：下载不完整");
        messages.append(DOWNLOAD_VERIFY, "下载失败：校验错误");
    }
}
