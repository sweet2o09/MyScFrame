package com.caihan.scframe.refresh;

/**
 * 智能刷新数据接口
 * <p>
 * 为了得到更好的体验以及帮助用户节约流量并预防同一时间发送过多的网络请求做了如下设计<br/>
 * 当发送EventBus的时候可能一些后台界面也收到刷新数据的消息,这个时候该接口就会开始工作<br/>
 * 等到界面呈现的时候才做网络请求
 *
 * @author caihan
 * @date 2018/1/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface AutoRefreshListener {

    /**
     * 智能刷新数据完成,在网络请求回调成功的时候调用该方法
     */
    void autoRefreshSuccess();

    /**
     * 需要智能刷新数据
     */
    void autoRefresh();

    /**
     * 智能刷新数据所需的网络请求
     */
    void autoRequest();
}
