package app.caihan.myscframe.ui.ninephoto;

import com.caihan.scframe.framework.v1.support.MvpView;

/**
 * @author caihan
 * @date 2019/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface NinePhotoShowContract {

    interface View extends MvpView {

        void getDataFinish(boolean isRefresh, NinePhotoShowBean ninePhotoShowBean);

        void getDataError();
    }

}