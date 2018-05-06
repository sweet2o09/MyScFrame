package com.caihan.myscframe.ui.multistoreylist;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalActivityBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartButtomBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartActivityItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.RequestData;
import com.caihan.myscframe.ui.multistoreylist.request.ShopCartBean;
import com.caihan.scframe.framework.v1.support.MvpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListModel implements MvpModel {


    /**
     * 改变数据结构
     */
    public LocalData changeDataStructure(RequestData requestData){
        LocalData localData = new LocalData();
        localData.setBusinessName(requestData.getBusinessName());
        localData.setIsCrossBorderBusiness(requestData.getIsCrossBorderBusiness());
        localData.setExemptionAmount(requestData.getExemptionAmount());
        localData.setMaxCrossBorderProductAmount(requestData.getMaxCrossBorderProductAmount());
        localData.setBusinessId(requestData.getBusinessId());
        List<LocalBean> localBeanList = new ArrayList<>();

        for (ShopCartBean shopCartBean : requestData.getShoppingCartList()) {
            LocalShopCartBean localShopCartBean = new LocalShopCartBean();
            localShopCartBean.setCartItemTradeType(shopCartBean.getCartItemTradeType());
            localShopCartBean.setCartItemTradeTypeTitle(shopCartBean.getCartItemTradeTypeTitle());
            localShopCartBean.setTaxTips(shopCartBean.getTaxTips());
            localShopCartBean.setItemTotalNum(shopCartBean.getItemTotalNum());
            localShopCartBean.setItemTotalAmount(shopCartBean.getItemTotalAmount());
            localShopCartBean.setTaxAmount(shopCartBean.getTaxAmount());
            localShopCartBean.setTotalAmount(shopCartBean.getTotalAmount());
            localShopCartBean.setBuyMultiItemTips(shopCartBean.getBuyMultiItemTips());
            localShopCartBean.setSaveAmount(shopCartBean.getSaveAmount());
            localBeanList.add(localShopCartBean);

            for (CartActivityItemBean activityItemBean : shopCartBean.getCartActivityItemList()) {
                if ("1".equals(activityItemBean.getCartActivityItemType())){
                    LocalActivityBean localActivityBean = new LocalActivityBean();
                    localActivityBean.setCartActivityItemType(activityItemBean.getCartActivityItemType());
                    localActivityBean.setCartActivityItemTypeId(activityItemBean.getCartActivityItemTypeId());
                    localActivityBean.setCartActivityItemTitle(activityItemBean.getCartActivityItemTitle());
                    localActivityBean.setCartActivityItemSubTitle(activityItemBean.getCartActivityItemSubTitle());
                    localActivityBean.setCartActivityItemTips(activityItemBean.getCartActivityItemTips());
                    localBeanList.add(localActivityBean);
                }

                for (CartItemBean cartItemBean : activityItemBean.getCartItemList()) {
                    localBeanList.add(cartItemBean);
                }
            }

            LocalShopCartButtomBean localShopCartButtomBean = new LocalShopCartButtomBean();
            localShopCartButtomBean.setCartItemTradeType(shopCartBean.getCartItemTradeType());
            localShopCartButtomBean.setCartItemTradeTypeTitle(shopCartBean.getCartItemTradeTypeTitle());
            localShopCartButtomBean.setTaxTips(shopCartBean.getTaxTips());
            localShopCartButtomBean.setItemTotalNum(shopCartBean.getItemTotalNum());
            localShopCartButtomBean.setItemTotalAmount(shopCartBean.getItemTotalAmount());
            localShopCartButtomBean.setTaxAmount(shopCartBean.getTaxAmount());
            localShopCartButtomBean.setTotalAmount(shopCartBean.getTotalAmount());
            localShopCartButtomBean.setBuyMultiItemTips(shopCartBean.getBuyMultiItemTips());
            localShopCartButtomBean.setSaveAmount(shopCartBean.getSaveAmount());
            localBeanList.add(localShopCartButtomBean);
        }
        localData.setShoppingCartList(localBeanList);
        return localData;
    }

    @Override
    public void onDestroy() {

    }
}