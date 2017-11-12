package com.caihan.myscframe.demo.mvp.model;

import com.caihan.myscframe.demo.mvp.contract.MvpDemoContract;

import java.util.Random;

/**
 * 作者：caihan
 * 创建时间：2017/11/12
 * 邮箱：93234929@qq.com
 * 说明：
 */

public class MvpDemoModel extends MvpDemoContract.Model {

    MvpDemoContract.Presenter mPresenter;
    int count = 1;
    Random rand;

    public MvpDemoModel(MvpDemoContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public void sendMsg() {
        if (rand == null) {
            rand = new Random();
        }
        //0-2随机数
//        int randNum = rand.nextInt(3);
        //40-56随机数
        int randNum = rand.nextInt(17) + 40;
        mPresenter.changeView("第" + count + "次数据:" + randNum);
        count++;
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
    }
}
