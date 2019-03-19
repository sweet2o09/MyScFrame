package app.caihan.myscframe.ui.coordinator;

import android.os.Bundle;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;

/**
 * CoordinatorLayout实现复杂联动
 *
 * @author caihan
 * @date 2019/3/18
 * @e-mail 93234929@qq.com
 * 维护者 
 */
public class CoordinatorActivity extends BaseScActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
    }

    @Override
    public void setImmersion() {
//        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_coordinator;
    }

    @Override
    protected void onCreate() {
        setImmersion();
//        mToolbarTitle.setText("联动效果");
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}
