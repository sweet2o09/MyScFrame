package com.caihan.myscframe.demo.mvp.contract;

import com.caihan.scframe.framework.base.impl.MvpBaseModel;
import com.caihan.scframe.framework.support.lce.activity.MvpLceView;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public interface LCEDemoContract {
    abstract class Model extends MvpBaseModel {

    }

    interface View<D> extends MvpLceView<D> {
    }

    interface Presenter {
        void changeView(String msg);
    }
}
