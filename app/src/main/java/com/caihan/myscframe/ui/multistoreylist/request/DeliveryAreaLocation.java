package com.caihan.myscframe.ui.multistoreylist.request;

import java.io.Serializable;

/**
 * @author caihan
 * @date 2018/2/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DeliveryAreaLocation implements Serializable {

    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
