package com.caihan.myscframe.base;import com.caihan.scframe.framework.v1.support.MvpView;import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;import com.caihan.scframe.framework.v1.support.mvp.activity.ScMvpActivity;import butterknife.ButterKnife;/** * @author caihan * @date 2018/4/22 * @e-mail 93234929@qq.com * 维护者 */public abstract class BaseScMvpActivity        <V extends MvpView, P extends MvpBasePresenter<V>>        extends ScMvpActivity<V,P> {    @Override    protected void butterKnifebind() {        super.butterKnifebind();        ButterKnife.bind(this);    }    @Override    public void setImmersion() {        super.setImmersion();    }}