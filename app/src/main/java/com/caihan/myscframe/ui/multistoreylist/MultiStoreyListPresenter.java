package com.caihan.myscframe.ui.multistoreylist;

import android.content.Context;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.RequestData;
import com.caihan.myscframe.ui.multistoreylist.request.data;
import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.utils.json.JsonAnalysis;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListPresenter extends MvpBasePresenter<MultiStoreyListContract.View> {


    private MultiStoreyListModel mModel;

    public MultiStoreyListPresenter(Context context) {
        super(context);
        mModel = new MultiStoreyListModel();
    }

    public void requestData() {
        Observable.create(new ObservableOnSubscribe<LocalData>() {

            @Override
            public void subscribe(ObservableEmitter<LocalData> emitter) throws Exception {
                RequestData requestData = JsonAnalysis.getInstance().fromJson(data.JSON_S, RequestData.class);
                LocalData localData = mModel.changeDataStructure(requestData);
                emitter.onNext(localData);
            }
        }).compose(RxSchedulers.<LocalData>request((RxAppCompatActivity) mContext))
                .subscribe(new Consumer<LocalData>() {
                    @Override
                    public void accept(LocalData requestData) throws Exception {
                        getView().requestDataFinish(requestData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onRequestError(throwable.getMessage().toString());
                    }
                });
    }

    /**
     * 全选与非全选状态切换
     *
     * @param data
     * @param isSelected 是否全选
     * @return true = 需要刷新数据,false = 无需刷新数据
     */
    public boolean changeAllSelected(List<LocalBean> data, boolean isSelected) {
        return mModel.changeAllSelected(data, isSelected);
    }

    /**
     * 当选中一个商品的时候,判断是否需要联动头部全选按钮
     *
     * @return true = 全部刷新, false = 只刷新item
     */
    public boolean checkHaveAllSelected(List<LocalBean> data, CartItemBean cartItemBean) {
        return mModel.checkHaveAllSelected(data, cartItemBean);
    }

    @Override
    public void destroy() {

    }
}