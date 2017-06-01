package com.caihan.scframe.update.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caihan on 2017/5/22.
 * 版本更新Data实体
 */
public class ScUpdateInfo {
    // 是否有新版本
    public boolean hasUpdate = false;
    // 是否静默下载：有新版本时不提示直接下载
    public boolean isSilent = false;
    // 是否强制安装：不安装无法使用app
    public boolean isForce = false;
    // 是否下载完成后自动安装
    public boolean isAutoInstall = true;
    // 是否可忽略该版本
    public boolean isIgnorable = true;
    // 一天内最大提示次数，<1时不限
    public int maxTimes = 0;

    public int versionCode;
    public String versionName;

    public String updateContent;

    public String url;
    public String md5;
    public long size;

    public static ScUpdateInfo parse(String s) throws JSONException {
        JSONObject o = new JSONObject(s);
        return parse(o.has("data") ? o.getJSONObject("data") : o);
    }

    private static ScUpdateInfo parse(JSONObject o) {
        ScUpdateInfo info = new ScUpdateInfo();
        if (o == null) {
            return info;
        }
        info.hasUpdate = o.optBoolean("hasUpdate", false);
        if (!info.hasUpdate) {
            return info;
        }
        info.isSilent = o.optBoolean("isSilent", false);
        info.isForce = o.optBoolean("isForce", false);
        info.isAutoInstall = o.optBoolean("isAutoInstall", !info.isSilent);
        info.isIgnorable = o.optBoolean("isIgnorable", true);

        info.versionCode = o.optInt("versionCode", 0);
        info.versionName = o.optString("versionName");
        info.updateContent = o.optString("updateContent");

        info.url = o.optString("url");
        info.md5 = o.optString("md5");
        info.size = o.optLong("size", 0);

        return info;
    }
}