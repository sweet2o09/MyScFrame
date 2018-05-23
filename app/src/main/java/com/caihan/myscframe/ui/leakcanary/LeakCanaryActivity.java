package com.caihan.myscframe.ui.leakcanary;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caihan.myscframe.MyApp;
import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * LeakCanary测试
 *
 * @author caihan
 * @date 2018/5/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LeakCanaryActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_iv)
    ImageView mToolbarRightIv;
    @BindView(R.id.toolbar_right_tv)
    TextView mToolbarRightTv;
    @BindView(R.id.toolbar_right_layout)
    LinearLayout mToolbarRightLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.closed_btn)
    Button mClosedBtn;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_leak_canary;
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("LeakCanary测试");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick(R.id.closed_btn)
    public void onViewClicked() {
        Box box = new Box();//来自官方的例子
        Cat schrodingerCat = new Cat();
        box.hiddenCat = schrodingerCat;
        Docker.container = box;
        MyApp.getRefWatcher(this).watch(schrodingerCat);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    class Cat {
    }

    class Box {
        Cat hiddenCat;
    }

    static class Docker {
        static Box container;
    }
}
