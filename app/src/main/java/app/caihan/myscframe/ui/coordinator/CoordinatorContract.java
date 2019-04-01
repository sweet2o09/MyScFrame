package app.caihan.myscframe.ui.coordinator;

import com.caihan.scframe.framework.v1.support.MvpView;

import java.util.List;

/**
 * @author caihan
 * @date 2019/3/24
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface CoordinatorContract {

    interface View extends MvpView {
        void getDataFinish(boolean isRefresh, List<String> beans, int count);
        void getDataError(boolean isRefresh, String msg, int count);
    }
}