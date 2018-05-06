package com.caihan.myscframe.ui.multistoreylist;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.scframe.framework.v1.support.MvpView;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface MultiStoreyListContract {

    interface View extends MvpView {

        void requestDataFinish(LocalData requestData);

    }
}