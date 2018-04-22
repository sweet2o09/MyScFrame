package com.caihan.myscframe.ui.qrcode;

import android.os.Bundle;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;

/**
 * 二维码扫描
 *
 * @author caihan
 * @date 2018/4/22
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ZxingQrcodeActivity extends BaseScActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_qrcode);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_zxing_qrcode;
    }

    @Override
    protected void onCreate() {

    }
}
