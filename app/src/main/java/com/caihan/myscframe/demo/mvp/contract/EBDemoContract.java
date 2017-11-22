package com.caihan.myscframe.demo.mvp.contract;

import com.caihan.scframe.framework.base.MvpView;
import com.caihan.scframe.framework.base.impl.MvpBaseModel;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public interface EBDemoContract {
    abstract class Model extends MvpBaseModel {

    }

    interface View extends MvpView {
        void changeView(String msg);
    }

    interface Presenter {
        void changeView(String msg);
    }
}
