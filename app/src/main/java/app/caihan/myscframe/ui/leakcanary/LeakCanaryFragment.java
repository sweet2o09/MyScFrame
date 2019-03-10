package app.caihan.myscframe.ui.leakcanary;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScFragment;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author caihan
 * @date 2019/3/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LeakCanaryFragment extends BaseScFragment {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.async_work_btn)
    Button mAsyncWorkBtn;

    private HttpRequestHelper httpRequestHelper;


    public static LeakCanaryFragment newInstance() {
        LeakCanaryFragment fragment = new LeakCanaryFragment();
        return fragment;
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected void onViewCreated() {
        setImmersion();
        mToolbarTitle.setText("内存泄漏");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        httpRequestHelper = new HttpRequestHelper(mAsyncWorkBtn);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_leak_canary;
    }

    @Override
    protected void lazyLoadData() {

    }

    @OnClick(R.id.async_work_btn)
    public void onViewClicked() {
        startAsyncWork();
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    void startAsyncWork() {
        Runnable work = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
            }
        };
        new Thread(work).start();
    }
}
