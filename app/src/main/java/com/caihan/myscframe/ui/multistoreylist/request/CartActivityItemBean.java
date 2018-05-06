package com.caihan.myscframe.ui.multistoreylist.request;

import java.util.List;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CartActivityItemBean {

    private String cartActivityItemType;//商品活动分类类型 -1-失效商品  0-普通商品  1-满减活动商品

    /**
     * 当cartActivityItemType=-1时，cartActivityItemTypeId =0
     * 当cartActivityItemType=0时，cartActivityItemTypeId =0
     * 当cartActivityItemType=1时，cartActivityItemTypeId=具体有生成满减活动页的ID
     */
    private String cartActivityItemTypeId;//商品活动分类类型ID

    /**
     * 当cartActivityItemType=-1时，cartActivityItemTitle =””
     * 当cartActivityItemType=0时，cartActivityItemTitle =””
     * 当cartActivityItemType=1时，cartActivityItemTitle=满减
     */
    private String cartActivityItemTitle;//商品活动分类类型

    /**
     * 当cartActivityItemType=-1时，cartActivityItemSubTitle =””
     * 当cartActivityItemType=0时，cartActivityItemSubTitle =””
     * 当cartActivityItemType=1时并且加入购物车商品未达到最低满减金额时cartActivityItemSubTitle=满¥ N减¥ n，还差¥ x
     * 当cartActivityItemType=1时并且加入购物车商品可享受满减金额cartActivityItemSubTitle =已购满¥ N，优惠¥ x
     */
    private String cartActivityItemSubTitle;//商品活动分类类型标题

    /**
     * 当cartActivityItemType=0时，cartActivityItemTips =””
     * 当cartActivityItemType=1时并且加入购物车商品未达到最低满减金额时cartActivityItemTips =去凑单
     * 当cartActivityItemType=1时并且加入购物车商品可享受满减金额cartActivityItemTips =去逛逛
     */
    private String cartActivityItemTips;//商品活动分类类型提示文字

    private List<CartItemBean> cartItemList;//购物车内容列表

    public String getCartActivityItemType() {
        return cartActivityItemType;
    }

    public void setCartActivityItemType(String cartActivityItemType) {
        this.cartActivityItemType = cartActivityItemType;
    }

    public String getCartActivityItemTypeId() {
        return cartActivityItemTypeId;
    }

    public void setCartActivityItemTypeId(String cartActivityItemTypeId) {
        this.cartActivityItemTypeId = cartActivityItemTypeId;
    }

    public String getCartActivityItemTitle() {
        return cartActivityItemTitle;
    }

    public void setCartActivityItemTitle(String cartActivityItemTitle) {
        this.cartActivityItemTitle = cartActivityItemTitle;
    }

    public String getCartActivityItemSubTitle() {
        return cartActivityItemSubTitle;
    }

    public void setCartActivityItemSubTitle(String cartActivityItemSubTitle) {
        this.cartActivityItemSubTitle = cartActivityItemSubTitle;
    }

    public String getCartActivityItemTips() {
        return cartActivityItemTips;
    }

    public void setCartActivityItemTips(String cartActivityItemTips) {
        this.cartActivityItemTips = cartActivityItemTips;
    }

    public List<CartItemBean> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemBean> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
