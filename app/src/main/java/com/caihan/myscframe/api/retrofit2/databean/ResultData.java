package com.caihan.myscframe.api.retrofit2.databean;

import java.io.Serializable;

/**
 * Created by caihan on 2017/4/1.
 */

public class ResultData implements Serializable{
    private static final long serialVersionUID = 4795372080370941457L;
    /**
     * versionName : 2.0.4
     * versionCode : 14
     * isQiangzhi : 0
     * url : 	http://upload.guaishoubobo.com/app-bobo-release.apk
     * updateContent : 问题修复! 如升级意外失败,请从官网www.guaishoubobo.com重新下载!
     * size : 25650067
     */

    private String versionName;
    private int versionCode;
    private int isQiangzhi;
    private String url;
    private String updateContent;
    private int size;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getIsQiangzhi() {
        return isQiangzhi;
    }

    public void setIsQiangzhi(int isQiangzhi) {
        this.isQiangzhi = isQiangzhi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", isQiangzhi=" + isQiangzhi +
                ", url='" + url + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", size=" + size +
                '}';
    }
}
