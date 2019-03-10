package app.caihan.myscframe.ui.leakcanary;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 内存泄漏-Act
 *
 * @author caihan
 * @date 2019/3/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LeakCanaryActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.async_work_btn)
    Button mAsyncWorkBtn;

    private HttpRequestHelper httpRequestHelper;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_leak_canary;
    }

    @Override
    protected void onCreate() {
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

    @OnClick(R.id.async_work_btn)
    public void onViewClicked() {
        startAsyncWork();
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    void startAsyncWork() {
        Runnable work = new Runnable() {
            @Override public void run() {
                SystemClock.sleep(10000);
            }
        };
        new Thread(work).start();
    }
}
