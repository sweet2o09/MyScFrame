package com.caihan.myscframe.ui.multistoreylist.bean;

import com.caihan.myscframe.ui.multistoreylist.MultiItemConstValue;
import com.caihan.scframe.utils.text.BaseParser;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LocalShopCartBean extends LocalBean {

    /**
     * 0-普通商品（包括完税商品）
     * 1-海外直邮（BC）
     * 2-海外直邮（个人）
     * 3-保税商品
     * 4-失效购物车商品（*排在最后、对应原来的storeCount字段与onSale字段，删除，下架，无货，关闭或者开启SKU
     * 5-不支持快速配送/到店自提/快速送/次日达/扫码购）
     */
    private String cartItemTradeType;//购物车商品业务类型
    /**
     * 当cartItemTradeType =4时，cartItemTradeTypeTitle =失效的商品
     * 当cartItemTradeType =0时，cartItemTradeTypeTitle =商家国内发货
     * 当cartItemTradeType =1时，cartItemTradeTypeTitle =海外直邮（BC）当 cartItemTradeType =2时，cartItemTradeTypeTitle =海外直邮（个人）
     * 当cartItemTradeType =3时，cartItemTradeTypeTitle =保税仓发货
     * 当cartItemTradeType =4时，cartItemTradeTypeTitle =失效商品
     * 当cartItemTradeType =5时，cartItemTradeTypeTitle =以下商品不支持到店自提/以下商品不支持快速送/以下商品不支持快速送/以下商品不支持次日达
     */
    private String cartItemTradeTypeTitle;//购物车商品业务类型标题名称
    /**
     * 使用跨境总和税进行计税的购物车（保税仓发货购物车和海外直邮 BC模式购物车）税费文字前需要显示提示图标，
     * 用户点触提示图标时出现提示弹出层，taxTips=根据海关规定，此处显示税费为预计税费，实际税费将结合订单运费一并计算。
     */
    private String taxTips;//税费提示语
    private String itemTotalNum;//选中的总商品件数
    private String itemTotalAmount;//商品总额
    private String taxAmount;//总税费
    private String totalAmount;//合计金额(itemTotalAmount + taxAmount)
    private String buyMultiItemTips;//多件购买不能超过1000的提示语
    private String saveAmount;//节省金额

    public int getCartItemTradeType() {
        return BaseParser.parseInt(cartItemTradeType);
    }

    public String getCartItemTradeTypeTitle() {
        return cartItemTradeTypeTitle;
    }

    public String getTaxTips() {
        return taxTips;
    }

    public String getItemTotalNum() {
        return itemTotalNum;
    }

    public String getItemTotalAmount() {
        return itemTotalAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getBuyMultiItemTips() {
        return buyMultiItemTips;
    }

    public String getSaveAmount() {
        return saveAmount;
    }

    public void setCartItemTradeType(String cartItemTradeType) {
        this.cartItemTradeType = cartItemTradeType;
    }

    public void setCartItemTradeTypeTitle(String cartItemTradeTypeTitle) {
        this.cartItemTradeTypeTitle = cartItemTradeTypeTitle;
    }

    public void setTaxTips(String taxTips) {
        this.taxTips = taxTips;
    }

    public void setItemTotalNum(String itemTotalNum) {
        this.itemTotalNum = itemTotalNum;
    }

    public void setItemTotalAmount(String itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setBuyMultiItemTips(String buyMultiItemTips) {
        this.buyMultiItemTips = buyMultiItemTips;
    }

    public void setSaveAmount(String saveAmount) {
        this.saveAmount = saveAmount;
    }

    @Override
    public int getItemType() {
        return ("4".equals(cartItemTradeType) || "5".equals(cartItemTradeType)) ?
                MultiItemConstValue.EXCEPTION_CART_ITEM_TRADE : MultiItemConstValue.NORMAL_CART_ITEM_TRADE;
    }
}
