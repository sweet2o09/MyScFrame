package com.caihan.scframe.dialog.request;

/**
 * 网络请求Loading接口
 * @author caihan
 * @date 2018/2/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IRequestLoad {

    void show();

    void dismiss();

    boolean isShowing();

    void onDestroy();
}
