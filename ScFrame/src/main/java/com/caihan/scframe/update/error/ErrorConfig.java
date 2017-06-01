package com.caihan.scframe.update.error;

/**
 * Created by caihan on 2017/5/22.
 * 错误码
 */
public interface ErrorConfig {

    int UPDATE_IGNORED = 1001;//该版本已经忽略
    int UPDATE_NO_NEWER = 1002;//已经是最新版了

    int CHECK_UNKNOWN = 2001;//查询更新失败：未知错误
    int CHECK_NO_WIFI = 2002;//查询更新失败：没有 WIFI
    int CHECK_NO_NETWORK = 2003;//查询更新失败：没有网络
    int CHECK_NETWORK_IO = 2004;//查询更新失败：网络异常
    int CHECK_HTTP_STATUS = 2005;//查询更新失败：错误的HTTP状态
    int CHECK_PARSE = 2006;//查询更新失败：解析错误


    int DOWNLOAD_UNKNOWN = 3001;//下载失败：未知错误
    int DOWNLOAD_CANCELLED = 3002;//下载失败：下载被取消
    int DOWNLOAD_DISK_NO_SPACE = 3003;//下载失败：磁盘空间不足
    int DOWNLOAD_DISK_IO = 3004;//下载失败：磁盘读写错误
    int DOWNLOAD_NETWORK_IO = 3005;//下载失败：网络异常
    int DOWNLOAD_NETWORK_BLOCKED = 3006;//下载失败：网络中断
    int DOWNLOAD_NETWORK_TIMEOUT = 3007;//下载失败：网络超时
    int DOWNLOAD_HTTP_STATUS = 3008;//下载失败：错误的HTTP状态
    int DOWNLOAD_INCOMPLETE = 3009;//下载失败：下载不完整
    int DOWNLOAD_VERIFY = 3010;//下载失败：校验错误
}
