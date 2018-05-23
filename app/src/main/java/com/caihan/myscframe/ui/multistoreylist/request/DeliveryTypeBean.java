package com.caihan.myscframe.ui.multistoreylist.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zss on 2017/3/8.
 * 购物车配送方式 v1.2.3
 */

public class DeliveryTypeBean implements Serializable {

    private List<DeliveryTypeItemBean> deliveryTypeList;//商家快递配送方式

    private String nextDayServiceTips;//次日达提示语
    private String nextDayIsSetCustomerAddress;//用户是否设置次日达临时地址
    private String nextDayIsCustomerRange;//当nextDayIsSetCustomerAddress=1时，次日达临时地址是否在配送范围内
    private String nextDayCustomerProvinceName;//次日达临时地址所在省份名称
    private String nextDayCustomerProvinceCode;//次日达临时地址所在省份码
    private String nextDayCustomerCityName;//次日达临时地址所在城市名称
    private String nextDayCustomerCityCode;//次日达临时地址所在城市码
    private String nextDayCustomerRegionName;//次日达临时地址所在区名称
    private String nextDayCustomerRegionCode;//次日达临时地址所在区码
    private String nextDayCustomerDeliveryId;//次日达临时地址ID
    private String nextDayIsSetDefaultAddress;//用户是否设置默认收货地址
    private String nextDayIsRange;//当nextDayIsSetDefaultAddress=1时，默认收货地址是否在配送范围内
    private String nextDayProvinceName;//用户默认收货地址所在省份名称
    private String nextDayProvinceCode;//用户默认收货地址所在省份码
    private String nextDayCityName;//用户默认收货地址所在城市名称
    private String nextDayCityCode;//用户默认收货地址所在城市码
    private String nextDayRegionName;//用户默认收货地址所在区名称
    private String nextDayRegionCode;//用户默认收货地址所在区码
    private String nextDayDeliveryId;//用户默认收货地址ID

    private String nextDayMinDeliveryAmount;//起送金额
    private String nextDayMaxFreeDeliveryAmount;//购满多少金额免配送费
    private String nextDayMaxFreeDeliveryAmountTips;//配送费用提示语
    private String nextDayDeliveryFee;//配送费

    private String deliveryTips;//快速送提示语
    private String isSetLocationAddress;//用户是否设置定位地址 0-否  1-是

    private String isSetDefaultAddress;//用户是否设置默认收货地址
    private String withoutDefaultAdressTips;//用户未设置默认收货地址提示语
    private String locationAdress;//用户默认收货地址所在的经纬度地址
    private String longitude;//用户默认收货地址所在的经度
    private String latitude;//用户默认收货地址所在的纬度

    private String deliveryId;//用户默认收货地址ID

    private List<CityDeliveryBean> cityDeliveryList;//商家设置的同城配送区域列表

    private String storePickTips;//门店自提提示语
    private String isCrossStorePick;//商家是否开启跨门店自提
    private String storeId;//自提门店ID
    private String storeName;//自提门店名称
    private String address;//自提门店地址
    private String city;//自提门店所在城市
    private String lat;//自提门店纬度
    private String lng;//自提门店经度

    private String customerLocationAddress;//用户所在的经纬度地址
    private String customerLongitude;//用户定位地址所在的经度
    private String customerLatitude;//用户定位地址所在的纬度
    private String customerDeliveryId;//用户定位地址ID
    private String provinceName;//用户定位所在省（直辖市）名称
    private String provinceCode;//用户定位所在省（直辖市）Code
    private String cityName;//用户定位所在城市名称
    private String cityCode;//用户定位所在省（直辖市）Code
    private String regionName;//用户定位所在区（县）名称
    private String regionCode;//用户定位所在区（县）Code
    private String detailAdress;//用户定位所在的详细地址

    public String getNextDayMinDeliveryAmount() {
        return nextDayMinDeliveryAmount;
    }

    public void setNextDayMinDeliveryAmount(String nextDayMinDeliveryAmount) {
        this.nextDayMinDeliveryAmount = nextDayMinDeliveryAmount;
    }

    public String getNextDayMaxFreeDeliveryAmount() {
        return nextDayMaxFreeDeliveryAmount;
    }

    public void setNextDayMaxFreeDeliveryAmount(String nextDayMaxFreeDeliveryAmount) {
        this.nextDayMaxFreeDeliveryAmount = nextDayMaxFreeDeliveryAmount;
    }

    public String getNextDayMaxFreeDeliveryAmountTips() {
        return nextDayMaxFreeDeliveryAmountTips;
    }

    public void setNextDayMaxFreeDeliveryAmountTips(String nextDayMaxFreeDeliveryAmountTips) {
        this.nextDayMaxFreeDeliveryAmountTips = nextDayMaxFreeDeliveryAmountTips;
    }

    public String getNextDayDeliveryFee() {
        return nextDayDeliveryFee;
    }

    public void setNextDayDeliveryFee(String nextDayDeliveryFee) {
        this.nextDayDeliveryFee = nextDayDeliveryFee;
    }

    public String getNextDayServiceTips() {
        return nextDayServiceTips;
    }

    public void setNextDayServiceTips(String nextDayServiceTips) {
        this.nextDayServiceTips = nextDayServiceTips;
    }

    public String getNextDayIsSetCustomerAddress() {
        return nextDayIsSetCustomerAddress;
    }

    public void setNextDayIsSetCustomerAddress(String nextDayIsSetCustomerAddress) {
        this.nextDayIsSetCustomerAddress = nextDayIsSetCustomerAddress;
    }

    public String getNextDayIsCustomerRange() {
        return nextDayIsCustomerRange;
    }

    public void setNextDayIsCustomerRange(String nextDayIsCustomerRange) {
        this.nextDayIsCustomerRange = nextDayIsCustomerRange;
    }

    public String getNextDayCustomerProvinceName() {
        return nextDayCustomerProvinceName;
    }

    public void setNextDayCustomerProvinceName(String nextDayCustomerProvinceName) {
        this.nextDayCustomerProvinceName = nextDayCustomerProvinceName;
    }

    public String getNextDayCustomerProvinceCode() {
        return nextDayCustomerProvinceCode;
    }

    public void setNextDayCustomerProvinceCode(String nextDayCustomerProvinceCode) {
        this.nextDayCustomerProvinceCode = nextDayCustomerProvinceCode;
    }

    public String getNextDayCustomerCityName() {
        return nextDayCustomerCityName;
    }

    public void setNextDayCustomerCityName(String nextDayCustomerCityName) {
        this.nextDayCustomerCityName = nextDayCustomerCityName;
    }

    public String getNextDayCustomerCityCode() {
        return nextDayCustomerCityCode;
    }

    public void setNextDayCustomerCityCode(String nextDayCustomerCityCode) {
        this.nextDayCustomerCityCode = nextDayCustomerCityCode;
    }

    public String getNextDayCustomerRegionName() {
        return nextDayCustomerRegionName;
    }

    public void setNextDayCustomerRegionName(String nextDayCustomerRegionName) {
        this.nextDayCustomerRegionName = nextDayCustomerRegionName;
    }

    public String getNextDayCustomerRegionCode() {
        return nextDayCustomerRegionCode;
    }

    public void setNextDayCustomerRegionCode(String nextDayCustomerRegionCode) {
        this.nextDayCustomerRegionCode = nextDayCustomerRegionCode;
    }

    public String getNextDayCustomerDeliveryId() {
        return nextDayCustomerDeliveryId;
    }

    public void setNextDayCustomerDeliveryId(String nextDayCustomerDeliveryId) {
        this.nextDayCustomerDeliveryId = nextDayCustomerDeliveryId;
    }

    public String getNextDayIsSetDefaultAddress() {
        return nextDayIsSetDefaultAddress;
    }

    public void setNextDayIsSetDefaultAddress(String nextDayIsSetDefaultAddress) {
        this.nextDayIsSetDefaultAddress = nextDayIsSetDefaultAddress;
    }

    public String getNextDayIsRange() {
        return nextDayIsRange;
    }

    public void setNextDayIsRange(String nextDayIsRange) {
        this.nextDayIsRange = nextDayIsRange;
    }

    public String getNextDayProvinceName() {
        return nextDayProvinceName;
    }

    public void setNextDayProvinceName(String nextDayProvinceName) {
        this.nextDayProvinceName = nextDayProvinceName;
    }

    public String getNextDayProvinceCode() {
        return nextDayProvinceCode;
    }

    public void setNextDayProvinceCode(String nextDayProvinceCode) {
        this.nextDayProvinceCode = nextDayProvinceCode;
    }

    public String getNextDayCityName() {
        return nextDayCityName;
    }

    public void setNextDayCityName(String nextDayCityName) {
        this.nextDayCityName = nextDayCityName;
    }

    public String getNextDayCityCode() {
        return nextDayCityCode;
    }

    public void setNextDayCityCode(String nextDayCityCode) {
        this.nextDayCityCode = nextDayCityCode;
    }

    public String getNextDayRegionName() {
        return nextDayRegionName;
    }

    public void setNextDayRegionName(String nextDayRegionName) {
        this.nextDayRegionName = nextDayRegionName;
    }

    public String getNextDayRegionCode() {
        return nextDayRegionCode;
    }

    public void setNextDayRegionCode(String nextDayRegionCode) {
        this.nextDayRegionCode = nextDayRegionCode;
    }

    public String getNextDayDeliveryId() {
        return nextDayDeliveryId;
    }

    public void setNextDayDeliveryId(String nextDayDeliveryId) {
        this.nextDayDeliveryId = nextDayDeliveryId;
    }

    public String getCustomerDeliveryId() {
        return customerDeliveryId;
    }

    public void setCustomerDeliveryId(String customerDeliveryId) {
        this.customerDeliveryId = customerDeliveryId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }

    public String getDeliveryTips() {
        return deliveryTips;
    }

    public void setDeliveryTips(String deliveryTips) {
        this.deliveryTips = deliveryTips;
    }

    public String getIsSetLocationAddress() {
        return isSetLocationAddress;
    }

    public void setIsSetLocationAddress(String isSetLocationAddress) {
        this.isSetLocationAddress = isSetLocationAddress;
    }

    public String getCustomerLocationAddress() {
        return customerLocationAddress;
    }

    public void setCustomerLocationAddress(String customerLocationAddress) {
        this.customerLocationAddress = customerLocationAddress;
    }

    public String getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(String customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(String customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public List<DeliveryTypeItemBean> getDeliveryTypeList() {
        return deliveryTypeList;
    }

    public void setDeliveryTypeList(List<DeliveryTypeItemBean> deliveryTypeList) {
        this.deliveryTypeList = deliveryTypeList;
    }

    public String getIsSetDefaultAddress() {
        return isSetDefaultAddress;
    }

    public void setIsSetDefaultAddress(String isSetDefaultAddress) {
        this.isSetDefaultAddress = isSetDefaultAddress;
    }

    public String getWithoutDefaultAdressTips() {
        return withoutDefaultAdressTips;
    }

    public void setWithoutDefaultAdressTips(String withoutDefaultAdressTips) {
        this.withoutDefaultAdressTips = withoutDefaultAdressTips;
    }

    public String getLocationAdress() {
        return locationAdress;
    }

    public void setLocationAdress(String locationAdress) {
        this.locationAdress = locationAdress;
    }

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

    public List<CityDeliveryBean> getCityDeliveryList() {
        return cityDeliveryList;
    }

    public void setCityDeliveryList(List<CityDeliveryBean> cityDeliveryList) {
        this.cityDeliveryList = cityDeliveryList;
    }

    public String getStorePickTips() {
        return storePickTips;
    }

    public void setStorePickTips(String storePickTips) {
        this.storePickTips = storePickTips;
    }

    public String getIsCrossStorePick() {
        return isCrossStorePick;
    }

    public void setIsCrossStorePick(String isCrossStorePick) {
        this.isCrossStorePick = isCrossStorePick;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "DeliveryTypeBean{" +
                "deliveryTypeList=" + deliveryTypeList +
                ", nextDayServiceTips='" + nextDayServiceTips + '\'' +
                ", nextDayIsSetCustomerAddress='" + nextDayIsSetCustomerAddress + '\'' +
                ", nextDayIsCustomerRange='" + nextDayIsCustomerRange + '\'' +
                ", nextDayCustomerProvinceName='" + nextDayCustomerProvinceName + '\'' +
                ", nextDayCustomerProvinceCode='" + nextDayCustomerProvinceCode + '\'' +
                ", nextDayCustomerCityName='" + nextDayCustomerCityName + '\'' +
                ", nextDayCustomerCityCode='" + nextDayCustomerCityCode + '\'' +
                ", nextDayCustomerRegionName='" + nextDayCustomerRegionName + '\'' +
                ", nextDayCustomerRegionCode='" + nextDayCustomerRegionCode + '\'' +
                ", nextDayCustomerDeliveryId='" + nextDayCustomerDeliveryId + '\'' +
                ", nextDayIsSetDefaultAddress='" + nextDayIsSetDefaultAddress + '\'' +
                ", nextDayIsRange='" + nextDayIsRange + '\'' +
                ", nextDayProvinceName='" + nextDayProvinceName + '\'' +
                ", nextDayProvinceCode='" + nextDayProvinceCode + '\'' +
                ", nextDayCityName='" + nextDayCityName + '\'' +
                ", nextDayCityCode='" + nextDayCityCode + '\'' +
                ", nextDayRegionName='" + nextDayRegionName + '\'' +
                ", nextDayRegionCode='" + nextDayRegionCode + '\'' +
                ", nextDayDeliveryId='" + nextDayDeliveryId + '\'' +
                ", nextDayMinDeliveryAmount='" + nextDayMinDeliveryAmount + '\'' +
                ", nextDayMaxFreeDeliveryAmount='" + nextDayMaxFreeDeliveryAmount + '\'' +
                ", nextDayMaxFreeDeliveryAmountTips='" + nextDayMaxFreeDeliveryAmountTips + '\'' +
                ", nextDayDeliveryFee='" + nextDayDeliveryFee + '\'' +
                ", deliveryTips='" + deliveryTips + '\'' +
                ", isSetLocationAddress='" + isSetLocationAddress + '\'' +
                ", isSetDefaultAddress='" + isSetDefaultAddress + '\'' +
                ", withoutDefaultAdressTips='" + withoutDefaultAdressTips + '\'' +
                ", locationAdress='" + locationAdress + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", deliveryId='" + deliveryId + '\'' +
                ", cityDeliveryList=" + cityDeliveryList +
                ", storePickTips='" + storePickTips + '\'' +
                ", isCrossStorePick='" + isCrossStorePick + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", customerLocationAddress='" + customerLocationAddress + '\'' +
                ", customerLongitude='" + customerLongitude + '\'' +
                ", customerLatitude='" + customerLatitude + '\'' +
                ", customerDeliveryId='" + customerDeliveryId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", detailAdress='" + detailAdress + '\'' +
                '}';
    }
}
