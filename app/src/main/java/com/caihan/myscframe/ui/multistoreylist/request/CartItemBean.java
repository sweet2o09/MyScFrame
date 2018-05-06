package com.caihan.myscframe.ui.multistoreylist.request;

import com.caihan.myscframe.ui.multistoreylist.MultiItemConstValue;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CartItemBean extends LocalBean {

    private String isSelected;//是否选中状态，0-未选中  1-选中
    private String itemCartId;//购物车ID（主键）
    private String businessId;//商品所属品牌商ID
    private String businessName;//商品所属品牌商名称
    private String businessLogo;//商品所属品牌商LOGO
    private String localItemId;//商品ID
    private String title;//商品标题
    private String picUrl;//商品图片URL(如果skuId>0时，取购物车skuId对应的图片不为空，则使用skuId对应的图片)
    private String price;//商品原价
    private String memberPrice;//商品会员价/促销价
    private String itemNum;//购物车商品数
    private String storeCount;//库存数(当DeliveryTypeId=2或者DeliveryTypeId=3时，需要判断渠道库存与门店库存的大小，取两者最小库存，扫码购如果是不限库存，则返回最大值)
    private String skuProperty;//SKU属性名
    private String onSale;//商品是否在售 0-下架     1-在售
    private String itemSaveAmount;//商品节省金额(单个)
    private String promotionLimitQuantityTips;//顾客当前购物车数量超出活动限购数量
    private String limitItemNum;//商品限购数
    private String buyItemNum;//已购买件数
    private String isOverLimitItemNum;//购买此商品数是否超过限购数 0-不超过  1-已超过
    private String isPromotion;//是否享受限时折扣 0-否  1-是
    private String isCrossBorderProduct;//是否跨境商品 0-否    1-是
    /**
     * 0-正常
     * 1-下架显示（包括取消授权）
     * 2-商品售罄（库存不足）显示（扫码购库存不足按商品售罄处理）
     * 3-商品属性变更（包括切换为预售商品）
     * 4-不支持快递配送（当DeliveryTypeId=2，如果无门店库存，cartItemTradeType=5, invalidCartType=4）
     * 5-不支持到店自提（当DeliveryTypeId=3，如果无门店库存，cartItemTradeType=5, invalidCartType=5）
     * 6-不支持快速配送（当DeliveryTypeId=1，商品不支持快递配送cartItemTradeType=5, invalidCartType=6）
     * 7-不支持次日达（当DeliveryTypeId=4，商品不支持次日达cartItemTradeType=5, invalidCartType=7）
     */
    private String invalidCartType;//购物车商品失效类型
    /**
     * 当invalidCartType=0时invalidCartTypeTips= “”
     * 当invalidCartType=1时invalidCartTypeTips=宝贝已下架
     * 当invalidCartType=2时invalidCartTypeTips=宝贝已卖光
     * 当invalidCartType=3时invalidCartTypeTips=宝贝暂不能购买
     * 当invalidCartType=4时invalidCartTypeTips=不支持快速送
     * 当invalidCartType=5时invalidCartTypeTips=不支持到店自提
     * 当invalidCartType=6时invalidCartTypeTips=不支持快递配送
     * 当invalidCartType=7时invalidCartTypeTips=不支持次日达
     */
    private String invalidCartTypeTips;//购物车商品失效类型提示语
    private String minItemBuyNum;//最低起售件数
    private String liveId;//直播/点播ID
    private String liveType;//直播类型（1-直播  2-点播）

    /*  v1.3.1*/
    private String processingItemName;//口味

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getItemCartId() {
        return itemCartId;
    }

    public void setItemCartId(String itemCartId) {
        this.itemCartId = itemCartId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(String businessLogo) {
        this.businessLogo = businessLogo;
    }

    public String getLocalItemId() {
        return localItemId;
    }

    public void setLocalItemId(String localItemId) {
        this.localItemId = localItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(String storeCount) {
        this.storeCount = storeCount;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getOnSale() {
        return onSale;
    }

    public void setOnSale(String onSale) {
        this.onSale = onSale;
    }

    public String getItemSaveAmount() {
        return itemSaveAmount;
    }

    public void setItemSaveAmount(String itemSaveAmount) {
        this.itemSaveAmount = itemSaveAmount;
    }

    public String getPromotionLimitQuantityTips() {
        return promotionLimitQuantityTips;
    }

    public void setPromotionLimitQuantityTips(String promotionLimitQuantityTips) {
        this.promotionLimitQuantityTips = promotionLimitQuantityTips;
    }

    public String getLimitItemNum() {
        return limitItemNum;
    }

    public void setLimitItemNum(String limitItemNum) {
        this.limitItemNum = limitItemNum;
    }

    public String getBuyItemNum() {
        return buyItemNum;
    }

    public void setBuyItemNum(String buyItemNum) {
        this.buyItemNum = buyItemNum;
    }

    public String getIsOverLimitItemNum() {
        return isOverLimitItemNum;
    }

    public void setIsOverLimitItemNum(String isOverLimitItemNum) {
        this.isOverLimitItemNum = isOverLimitItemNum;
    }

    public String getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getIsCrossBorderProduct() {
        return isCrossBorderProduct;
    }

    public void setIsCrossBorderProduct(String isCrossBorderProduct) {
        this.isCrossBorderProduct = isCrossBorderProduct;
    }

    public String getInvalidCartType() {
        return invalidCartType;
    }

    public void setInvalidCartType(String invalidCartType) {
        this.invalidCartType = invalidCartType;
    }

    public String getInvalidCartTypeTips() {
        return invalidCartTypeTips;
    }

    public void setInvalidCartTypeTips(String invalidCartTypeTips) {
        this.invalidCartTypeTips = invalidCartTypeTips;
    }

    public String getMinItemBuyNum() {
        return minItemBuyNum;
    }

    public void setMinItemBuyNum(String minItemBuyNum) {
        this.minItemBuyNum = minItemBuyNum;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getProcessingItemName() {
        return processingItemName;
    }

    public void setProcessingItemName(String processingItemName) {
        this.processingItemName = processingItemName;
    }

    @Override
    public int getItemType() {
        return invalidCartType.equals("0") ?
                MultiItemConstValue.NORMAL_GOODS_ITEM : MultiItemConstValue.EXCEPTION_GOODS_ITEM;
    }
}
