package com.caihan.myscframe.demo.mvp.model;

import com.caihan.myscframe.demo.mvp.contract.EBDemoContract;
import com.caihan.scframe.utils.evenbus.Event;
import com.caihan.scframe.utils.evenbus.EventBusUtils;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public class EBDemoModel extends EBDemoContract.Model {

    public void eventBtn() {
        EventBusUtils.post(new Event("now"));
    }
    @Override
    public void onDestroy() {

    }
}
