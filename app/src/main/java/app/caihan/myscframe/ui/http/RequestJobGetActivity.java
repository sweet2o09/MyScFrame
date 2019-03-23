package app.caihan.myscframe.ui.http;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * GET请求
 *
 * @author caihan
 * @date 2019/3/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RequestJobGetActivity
        extends BaseScMvpActivity<RequestJobGetContract.View, RequestJobGetPresenter>
        implements RequestJobGetContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int type = 0;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_request_job_get;
    }

    @NonNull
    @Override
    public RequestJobGetPresenter createPresenter() {
        return new RequestJobGetPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        setBaseToolbarLayout(mToolbar, "GET请求");
    }

    @OnClick(R.id.get_btn)
    public void onViewClicked() {
        getPresenter().onObservable(type);
    }

    @Override
    public void nexObservable(int i) {
        type = ++i;
        if (type == 3) {
            type = 0;
        }
    }
}
