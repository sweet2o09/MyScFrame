package com.caihan.scframe.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.EncryptUtils;

/**
 * 生成设备唯一标识符
 * IMEI,AndroidId,macAddress三者拼接再MD5
 *
 * @author caihan
 * @date 2018/10/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class DeviceIdUtils {

    /**
     * 生成设备唯一标识：IMEI,AndroidId,macAddress三者拼接再MD5
     * 获取 IMEI 需要加入权限 android.permission.READ_PHONE_STATE
     * 获取 macAddress 需要加入权限 android.permission.ACCESS_WIFI_STATE
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String generateUniqueDeviceId(Context context) {
        String imei = "";
        String androidId = "";
        String macAddress = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            imei = telephonyManager.getDeviceId();
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            macAddress = wifiManager.getConnectionInfo().getMacAddress();
        }

        StringBuilder longIdBuilder = new StringBuilder();
        if (imei != null) {
            longIdBuilder.append(imei);
        }
        if (androidId != null) {
            longIdBuilder.append(androidId);
        }
        if (macAddress != null) {
            longIdBuilder.append(macAddress);
        }
        return EncryptUtils.encryptMD5ToString(longIdBuilder.toString());
    }
}
