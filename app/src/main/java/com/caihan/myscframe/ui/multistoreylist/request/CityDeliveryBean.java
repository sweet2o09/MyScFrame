package com.caihan.myscframe.ui.multistoreylist.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author caihan
 * @date 2018/2/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CityDeliveryBean implements Serializable {

    private String cityDeliveryId;//配送区域ID
    private String minDeliveryAmount;//起送金额
    private String maxFreeDeliveryAmount;//购满多少金额免配送费
    private String deliveryFeeTips;//配送费用提示语

    private List<DeliveryAreaLocation> deliveryAreaLocationList;//配送区域对应的经纬列表

    public String getCityDeliveryId() {
        return cityDeliveryId;
    }

    public void setCityDeliveryId(String cityDeliveryId) {
        this.cityDeliveryId = cityDeliveryId;
    }

    public String getMinDeliveryAmount() {
        return minDeliveryAmount;
    }

    public void setMinDeliveryAmount(String minDeliveryAmount) {
        this.minDeliveryAmount = minDeliveryAmount;
    }

    public String getMaxFreeDeliveryAmount() {
        return maxFreeDeliveryAmount;
    }

    public void setMaxFreeDeliveryAmount(String maxFreeDeliveryAmount) {
        this.maxFreeDeliveryAmount = maxFreeDeliveryAmount;
    }

    public String getDeliveryFeeTips() {
        return deliveryFeeTips;
    }

    public void setDeliveryFeeTips(String deliveryFeeTips) {
        this.deliveryFeeTips = deliveryFeeTips;
    }

    public List<DeliveryAreaLocation> getDeliveryAreaLocationList() {
        return deliveryAreaLocationList;
    }

    public void setDeliveryAreaLocationList(List<DeliveryAreaLocation> deliveryAreaLocationList) {
        this.deliveryAreaLocationList = deliveryAreaLocationList;
    }

    @Override
    public String toString() {
        return "CityDeliveryBean{" +
                "cityDeliveryId='" + cityDeliveryId + '\'' +
                ", minDeliveryAmount='" + minDeliveryAmount + '\'' +
                ", maxFreeDeliveryAmount='" + maxFreeDeliveryAmount + '\'' +
                ", deliveryFeeTips='" + deliveryFeeTips + '\'' +
                ", deliveryAreaLocationList=" + deliveryAreaLocationList +
                '}';
    }
}
