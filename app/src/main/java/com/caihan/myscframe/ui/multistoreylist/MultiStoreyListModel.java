package com.caihan.myscframe.ui.multistoreylist;

import android.support.annotation.IntRange;
import android.util.SparseBooleanArray;

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
 * 新购物车数据处理M层
 * <p>
 * 1.只有有效商品(cartItemTradeType<=3)才能被选中
 * 2.单选商品联动同业务类型的头部全选状态与业务底部结算按钮状态,同时判断界面底部全选按钮状态
 * 3.头部全选状态联动同业务类型下所有商品的选中状态与业务底部结算按钮状态,同时判断界面底部全选按钮状态
 * 4.界面底部全选按钮联动所有有效商品以及其头部的选中状态(cartItemTradeType<=3)与业务底部结算按钮状态
 * <p>
 * 也就是说:单选,全选,界面底部全选,业务底部结算,都需要联动处理
 * <p>
 * 编辑模式下:单选,全选,都不发送网络请求,使用本地数据判断
 * 正常模式下:单选,全选,直接发送网络请求,刷新数据
 * <p>
 * 从编辑模式切换回正常模式的时候,要把选中项发送网络请求,刷新数据
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListModel implements MvpModel {

    /**
     * 0-普通商品（包括完税商品）
     * 1-海外直邮（BC）
     * 2-海外直邮（个人）
     * 3-保税商品
     * 4-失效购物车商品（*排在最后、对应原来的storeCount字段与onSale字段，删除，下架，无货，关闭或者开启SKU
     * 5-不支持快速配送/到店自提/快速送/次日达/扫码购）
     */
    private static final int NORMAL_CART_TYPE_BEFORE = 3;//cartItemTradeType>3的是不支持与无效商品,不加入遍历与计算行列

    private IShopCartSelectedListener mListener;

    public interface IShopCartSelectedListener {

        /**
         * 所有有效业务类型的商品的选中状态
         *
         * @param isAllSelected true-全选,内部商品状态也要切换
         */
        void normalAllSelectedBtnStatus(boolean isAllSelected);

        /**
         * 指定有效业务类型的商品的选中状态
         *
         * @param cartItemTradeType
         * @param isAllSelected     true-全选,内部商品状态也要切换
         */
        void normalAllSelectedBtnStatus(@IntRange(from = 0, to = 3) int cartItemTradeType, boolean isAllSelected);

        /**
         * 底部全选按钮的选中状态
         *
         * @param isAllSelected true-全选,所有有效业务类型下的商品都要选中
         */
        void UiButtomAllSelectedBtnStatus(boolean isAllSelected);
    }

    public MultiStoreyListModel(IShopCartSelectedListener listener) {
        mListener = listener;
    }

    /**
     * 改变数据结构
     */
    public LocalData changeDataStructure(RequestData requestData) {
        LocalData localData = new LocalData();
        localData.setBusinessName(requestData.getBusinessName());
        localData.setIsCrossBorderBusiness(requestData.getIsCrossBorderBusiness());
        localData.setExemptionAmount(requestData.getExemptionAmount());
        localData.setMaxCrossBorderProductAmount(requestData.getMaxCrossBorderProductAmount());
        localData.setBusinessId(requestData.getBusinessId());
        List<LocalBean> localBeanList = new ArrayList<>();
        int cartItemTradeType;
        boolean isAllSelected = true;//业务是否全选
        boolean isCanClick = false;//结算是否可点击
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

            cartItemTradeType = localShopCartBean.getCartItemTradeType();

            for (CartActivityItemBean activityItemBean : shopCartBean.getCartActivityItemList()) {
                if ("1".equals(activityItemBean.getCartActivityItemType())) {
                    LocalActivityBean localActivityBean = new LocalActivityBean();
                    localActivityBean.setCartActivityItemType(activityItemBean.getCartActivityItemType());
                    localActivityBean.setCartActivityItemTypeId(activityItemBean.getCartActivityItemTypeId());
                    localActivityBean.setCartActivityItemTitle(activityItemBean.getCartActivityItemTitle());
                    localActivityBean.setCartActivityItemSubTitle(activityItemBean.getCartActivityItemSubTitle());
                    localActivityBean.setCartActivityItemTips(activityItemBean.getCartActivityItemTips());
                    localBeanList.add(localActivityBean);
                }

                for (CartItemBean cartItemBean : activityItemBean.getCartItemList()) {
                    cartItemBean.setCartItemTradeType(cartItemTradeType);
                    if ("0".equals(cartItemBean.getIsSelected())) {
                        //只要有一个未选中,就不是全选状态
                        isAllSelected = false;
                    } else if ("1".equals(cartItemBean.getIsSelected())) {
                        //有一个被选中就可以点击结算
                        isCanClick = true;
                    }
                    localBeanList.add(cartItemBean);
                }
            }
            //保存全选状态
            localShopCartBean.setAllSelected(isAllSelected);

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
            localShopCartButtomBean.setCanClick(isCanClick);
            localBeanList.add(localShopCartButtomBean);
        }
        localData.setShoppingCartList(localBeanList);
        return localData;
    }


    /**
     * 有效商品选中状态变更,需要联动变更其业务类型的头部全选状态与UI底部全选按钮状态
     *
     * @param data
     */
    public void goodsSelectedChange(List<LocalBean> data, CartItemBean cartItemBean) {
        int cartItemTradeType = cartItemBean.getCartItemTradeType();//获取商品的业务类型
        if (cartItemTradeType > NORMAL_CART_TYPE_BEFORE) {
            //不支持,无效商品
            return;
        }

        String isSelected = cartItemBean.getIsSelected();//先获取商品原有的选中状态
        cartItemBean.setIsSelected("1".equals(isSelected) ? "0" : "1");//写入新状态

        SparseBooleanArray cartItemTradeArray = new SparseBooleanArray();//为了给Ui底部按钮提供判断
        LocalShopCartBean localShopCartBean = null;
        CartItemBean cartItem = null;
        LocalShopCartButtomBean buttomBean = null;
        boolean isCanClick = false;//结算是否可点击


        for (LocalBean bean : data) {
            if (bean instanceof LocalShopCartBean) {
                //有效商品header与无效商品header都是这个类
                if (((LocalShopCartBean) bean).getCartItemTradeType() > NORMAL_CART_TYPE_BEFORE) {
                    //不支持,无效商品可以跳出循环了
                    break;
                }
                localShopCartBean = (LocalShopCartBean) bean;
                if (localShopCartBean.getCartItemTradeType() == cartItemTradeType) {
                    //同一个业务类型下,先假设他被全选了
                    localShopCartBean.setAllSelected(true);
                }
                cartItemTradeArray.put(localShopCartBean.getCartItemTradeType(), localShopCartBean.isAllSelected());

            } else if (bean instanceof CartItemBean) {
                cartItem = (CartItemBean) bean;
                if (cartItem.getCartItemTradeType() == cartItemTradeType) {
                    //同一个业务类型下
                    if ("0".equals(cartItem.getIsSelected())) {
                        //有一个商品是没被选中
                        localShopCartBean.setAllSelected(false);
                        cartItemTradeArray.put(localShopCartBean.getCartItemTradeType(), localShopCartBean.isAllSelected());
                    } else if ("1".equals(cartItem.getIsSelected())) {
                        isCanClick = true;
                    }
                }
                //不同业务下的不用理会
            } else if (bean instanceof LocalShopCartButtomBean) {
                buttomBean = (LocalShopCartButtomBean) bean;
                if (buttomBean.getCartItemTradeType() == cartItemTradeType) {
                    //同一个业务类型下
                    buttomBean.setCanClick(isCanClick);
                }
                //不同业务下的不用理会
            }
        }

        if (mListener != null) {
            boolean buttomSelected = true;
            for (int i = 0; i < cartItemTradeArray.size(); i++) {
                buttomSelected = cartItemTradeArray.get(cartItemTradeArray.keyAt(i), false);
            }
            mListener.UiButtomAllSelectedBtnStatus(buttomSelected);
        }
    }


    /**
     * 全选与非全选状态切换
     *
     * @param data
     * @param isSelected        是否全选
     * @param cartItemTradeType 业务类型
     */
    public void changeAllSelected(List<LocalBean> data, boolean isSelected, int cartItemTradeType) {
        /**
         * 0-普通商品（包括完税商品）
         * 1-海外直邮（BC）
         * 2-海外直邮（个人）
         * 3-保税商品
         * 4-失效购物车商品（*排在最后、对应原来的storeCount字段与onSale字段，删除，下架，无货，关闭或者开启SKU
         * 5-不支持快速配送/到店自提/快速送/次日达/扫码购）
         */
        if (cartItemTradeType > NORMAL_CART_TYPE_BEFORE) {
            //不支持与失效商品
            return;
        }
        boolean isAllItemTradeSelected = cartItemTradeType == -1;//是否所有业务类型全选
        SparseBooleanArray cartItemTradeArray = new SparseBooleanArray();//为了给Ui底部按钮提供判断
        LocalShopCartBean localShopCartBean = null;
        String isAllSelected = isSelected ? "1" : "0";
        for (LocalBean bean : data) {
            //如果数据返回无误的话,列表顺序是:1.有效商品,2.不支持商品,3.失效商品
            if (bean instanceof LocalShopCartBean) {
                if (cartItemTradeType > NORMAL_CART_TYPE_BEFORE) {
                    //不支持与失效商品
                    break;
                }
                if (isAllItemTradeSelected) {
                    cartItemTradeType = ((LocalShopCartBean) bean).getCartItemTradeType();
                    localShopCartBean = (LocalShopCartBean) bean;
                } else {
                    if (cartItemTradeType == ((LocalShopCartBean) bean).getCartItemTradeType()) {
                        localShopCartBean = (LocalShopCartBean) bean;
                    }
                }
                cartItemTradeArray.put(localShopCartBean.getCartItemTradeType(),localShopCartBean.isAllSelected());
            } else if (bean instanceof CartItemBean) {
                //找到同业务类型的商品,设置选中状态,并且更新业务全选/反选状态
                if (((CartItemBean) bean).getCartItemTradeType() == cartItemTradeType) {
                    ((CartItemBean) bean).setIsSelected(isAllSelected);
                    if (localShopCartBean != null) {
                        localShopCartBean.setAllSelected(isSelected);
                        cartItemTradeArray.put(localShopCartBean.getCartItemTradeType(),localShopCartBean.isAllSelected());
                    }
                }
            } else if (bean instanceof LocalShopCartButtomBean) {
                if (((LocalShopCartButtomBean) bean).getCartItemTradeType() == cartItemTradeType) {
                    ((LocalShopCartButtomBean) bean).setCanClick(isSelected);
                }
            }
        }

        if (mListener != null) {
            if (isAllItemTradeSelected){
                //UI底部全选按钮过来的
                mListener.UiButtomAllSelectedBtnStatus(isSelected);
            }else {
                boolean buttomSelected = true;
                for (int i = 0; i < cartItemTradeArray.size(); i++) {
                    buttomSelected = cartItemTradeArray.get(cartItemTradeArray.keyAt(i), false);
                }
                mListener.UiButtomAllSelectedBtnStatus(buttomSelected);
            }
        }
    }


    /**
     * 生成选中商品id列表
     *
     * @param data
     */
    public ArrayList<String> setSelectedGoodsList(List<LocalBean> data) {
        ArrayList<String> selectedGoodsList = new ArrayList<>();
        CartItemBean cartItemBean;
        for (LocalBean bean : data) {
            if (bean instanceof CartItemBean) {
                cartItemBean = (CartItemBean) bean;
                if (cartItemBean.getCartItemTradeType() > 3) {
                    return selectedGoodsList;
                }
                if ("1".equals(cartItemBean.getIsSelected())) {
                    selectedGoodsList.add(cartItemBean.getLocalItemId());
                }
            }
        }
        return selectedGoodsList;
    }


    /**
     * 删除商品
     *
     * @param requestData
     * @param cartItemBean
     * @return
     */
    public LocalData delGoods(RequestData requestData, CartItemBean cartItemBean) {
        String itemCartId = cartItemBean.getItemCartId();
        int goodsSize;
        List<CartItemBean> cartItemBeanList;
        CartItemBean itemBean;
        for (ShopCartBean shopCartBean : requestData.getShoppingCartList()) {
            for (CartActivityItemBean activityItemBean : shopCartBean.getCartActivityItemList()) {
                cartItemBeanList = activityItemBean.getCartItemList();
                if (cartItemBeanList != null && cartItemBeanList.size() > 0) {
                    goodsSize = cartItemBeanList.size();
                    for (int i = 0; i < goodsSize; i++) {
                        itemBean = cartItemBeanList.get(i);
                        if (itemCartId.equals(itemBean.getItemCartId())) {
                            cartItemBeanList.remove(i);
                            return changeDataStructure(requestData);
                        }
                    }
                }
            }
        }
        return changeDataStructure(requestData);
    }


    @Override
    public void onDestroy() {

    }
}