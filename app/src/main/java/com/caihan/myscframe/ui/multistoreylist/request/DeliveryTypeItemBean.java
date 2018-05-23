package com.caihan.myscframe.ui.multistoreylist.request;

import java.io.Serializable;

/**
 * @author caihan
 * @date 2018/2/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DeliveryTypeItemBean implements Serializable {
    private String deliveryTypeId;//配送方式 1-快递配送 2-快速送 3-门店自提 4-次日达
    private String deliveryTypeName;//配送方式名称
    private String deliveryBusinessType;//快递配送业务类型ID

    private boolean isCheck = false;//是否属于选中状态 create by lyq at 2017.6.29 自定义属性

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(String deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    public String getDeliveryTypeName() {
        return deliveryTypeName;
    }

    public void setDeliveryTypeName(String deliveryTypeName) {
        this.deliveryTypeName = deliveryTypeName;
    }

    public String getDeliveryBusinessType() {
        return deliveryBusinessType;
    }

    public void setDeliveryBusinessType(String deliveryBusinessType) {
        this.deliveryBusinessType = deliveryBusinessType;
    }

    @Override
    public String toString() {
        return "DeliveryTypeItemBean{" +
                "deliveryTypeId='" + deliveryTypeId + '\'' +
                ", deliveryTypeName='" + deliveryTypeName + '\'' +
                ", deliveryBusinessType='" + deliveryBusinessType + '\'' +
                '}';
    }
}
