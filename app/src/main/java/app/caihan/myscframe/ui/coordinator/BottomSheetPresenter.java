package app.caihan.myscframe.ui.coordinator;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.rxjava.RxSubscriber;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import app.caihan.myscframe.api.MyApi;
import app.caihan.myscframe.model.ResultsBean;

/**
 * @author caihan
 * @date 2019/3/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class BottomSheetPresenter extends MvpBasePresenter<BottomSheetContract.View> {


    public BottomSheetPresenter(Context context) {
        super(context);
    }


    public void getData(boolean isRefresh) {
        if (isRefresh) {
            resetPage();
        }
        MyApi.getGankApi(getPageSize(), getIndexPage())
                .compose(RxSchedulers.request((RxAppCompatActivity) getView()))
                .subscribe(new RxSubscriber<List<ResultsBean>>(getView()) {
                    @Override
                    public void onRxNext(List<ResultsBean> resultsBeans) {
                        getView().getDataFinish(isRefresh, resultsBeans, 78);
                        addPage();
                    }

                    @Override
                    public void onRxError(Throwable error) {
                        getView().getDataError(isRefresh, error.getMessage(), 78);
                    }
                });
    }

    @Override
    public void destroy() {

    }
}