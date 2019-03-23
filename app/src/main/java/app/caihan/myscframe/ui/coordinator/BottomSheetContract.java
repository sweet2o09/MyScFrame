package app.caihan.myscframe.ui.coordinator;

import com.caihan.scframe.framework.v1.support.MvpView;

import java.util.List;

import app.caihan.myscframe.model.ResultsBean;

/**
 * @author caihan
 * @date 2019/3/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface BottomSheetContract {

    interface View extends MvpView {

        void getDataFinish(boolean isRefresh, List<ResultsBean> beans, int count);

        void getDataError(boolean isRefresh, String msg, int count);
    }
}