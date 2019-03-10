package app.caihan.myscframe.ui.leakcanary;

import com.blankj.utilcode.util.FragmentUtils;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;

public class LeakCanaryActivity2 extends BaseScActivity {

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_leak_canary2;
    }

    @Override
    protected void onCreate() {
        getImmersion();
        FragmentUtils.add(getSupportFragmentManager(),
                LeakCanaryFragment.newInstance(),
                R.id.fragments,
                "LeakCanaryFragment");
    }
}
