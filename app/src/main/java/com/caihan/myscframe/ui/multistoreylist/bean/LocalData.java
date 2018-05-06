package com.caihan.myscframe.ui.multistoreylist.bean;

import java.util.List;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LocalData {

    private String isCrossBorderBusiness;//商家是否跨境商家 0-否   1-是
    private String businessId;//门店对应的品牌商ID
    private String businessName;//商品所属品牌商名称
    private String exemptionAmount;//商家设置的免征关税金额
    private String maxCrossBorderProductAmount;//跨境商品多件最大可买金额(一般是商品总额(不含税)不能超过¥1000，如果为0，则表示跨境订单限额为关闭或者开启时设置为0，此时不用判断最大可购买金额)
    private List<LocalBean> shoppingCartList;//商家购物车列表

    public String getIsCrossBorderBusiness() {
        return isCrossBorderBusiness;
    }

    public void setIsCrossBorderBusiness(String isCrossBorderBusiness) {
        this.isCrossBorderBusiness = isCrossBorderBusiness;
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

    public String getExemptionAmount() {
        return exemptionAmount;
    }

    public void setExemptionAmount(String exemptionAmount) {
        this.exemptionAmount = exemptionAmount;
    }

    public String getMaxCrossBorderProductAmount() {
        return maxCrossBorderProductAmount;
    }

    public void setMaxCrossBorderProductAmount(String maxCrossBorderProductAmount) {
        this.maxCrossBorderProductAmount = maxCrossBorderProductAmount;
    }

    public List<LocalBean> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<LocalBean> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
}
