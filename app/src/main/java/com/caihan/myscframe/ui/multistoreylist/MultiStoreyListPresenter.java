package com.caihan.myscframe.ui.multistoreylist;

import android.content.Context;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.RequestData;
import com.caihan.myscframe.ui.multistoreylist.request.data;
import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.rxjava.RxSubscriber;
import com.caihan.scframe.utils.json.JsonAnalysis;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 新购物车P层
 *
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListPresenter
        extends MvpBasePresenter<MultiStoreyListContract.View> {


    private MultiStoreyListModel mModel;

    private RequestData mRequestData;

    public MultiStoreyListPresenter(Context context) {
        super(context);
        mModel = new MultiStoreyListModel(new MultiStoreyListModel.IShopCartSelectedListener() {

            @Override
            public void normalAllSelectedBtnStatus(boolean isAllSelected) {
            }

            @Override
            public void normalAllSelectedBtnStatus(int cartItemTradeType, boolean isAllSelected) {
            }

            @Override
            public void UiButtomAllSelectedBtnStatus(boolean isAllSelected) {
                getView().UiButtomAllSelectedBtnStatus(isAllSelected);
            }
        });
    }

    public void requestData() {
        Observable.create(new ObservableOnSubscribe<LocalData>() {

            @Override
            public void subscribe(ObservableEmitter<LocalData> emitter) throws Exception {
                mRequestData = JsonAnalysis.getInstance().fromJson(data.JSON_S, RequestData.class);
                LocalData localData = mModel.changeDataStructure(mRequestData);
                emitter.onNext(localData);
            }
        }).compose(RxSchedulers.<LocalData>request((RxAppCompatActivity) mContext, getView()))
                .subscribe(new RxSubscriber<LocalData>(getView()) {
                    @Override
                    public void _onNext(LocalData localData) {
                        getView().requestDataFinish(localData);
                    }

                    @Override
                    public void _onError(Throwable error) {

                    }
                });

    }

    /**
     * 删除商品
     *
     * @param cartItemBean
     */
    public void delGoods(final CartItemBean cartItemBean) {
        Observable.create(new ObservableOnSubscribe<LocalData>() {

            @Override
            public void subscribe(ObservableEmitter<LocalData> emitter) throws Exception {
                LocalData localData = mModel.delGoods(mRequestData, cartItemBean);
                emitter.onNext(localData);
            }
        }).compose(RxSchedulers.<LocalData>request((RxAppCompatActivity) mContext, getView()))
                .subscribe(new RxSubscriber<LocalData>(getView()) {
                    @Override
                    public void _onNext(LocalData localData) {
                        getView().delGoodsFinish(localData);
                    }

                    @Override
                    public void _onError(Throwable error) {

                    }
                });
    }

    /**
     * 全选与非全选状态切换
     *
     * @param data
     * @param isSelected        是否全选
     * @param cartItemTradeType 业务类型
     * @return true = 需要刷新数据,false = 无需刷新数据
     */
    public void changeAllSelected(List<LocalBean> data, boolean isSelected, int cartItemTradeType) {
        mModel.changeAllSelected(data, isSelected, cartItemTradeType);
    }

    /**
     * 当选中一个商品的时候,联动刷新底部全选按钮
     *
     * @param data
     * @param cartItemBean
     */
    public void goodsSelectedChange(List<LocalBean> data, CartItemBean cartItemBean) {
        mModel.goodsSelectedChange(data, cartItemBean);
    }

    /**
     * 生成选中商品id列表
     *
     * @param data
     * @return
     */
    public ArrayList<String> setSelectedGoodsList(List<LocalBean> data) {
        return mModel.setSelectedGoodsList(data);
    }

    @Override
    public void destroy() {
        mModel = null;
    }
}