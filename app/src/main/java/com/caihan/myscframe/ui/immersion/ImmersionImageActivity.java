package com.caihan.myscframe.ui.immersion;

import android.widget.Button;
import android.widget.ImageView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片沉浸式效果
 *
 * @author caihan
 * @date 2018/5/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionImageActivity extends BaseScActivity {

    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.change)
    Button mChange;

    int position = 0;
    boolean isDarkFont = false;
    int[] imageRes = {R.drawable.bg_flight_info_activity_m, R.drawable.image_1,
            R.drawable.image_2, R.drawable.image_3};
    boolean[] dark = {false, true, false, true};

    @Override
    public void setImmersion() {
        getImmersion().setImmersionTransparentDarkFont(isDarkFont);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_immersion_image;
    }

    @Override
    protected void onCreate() {
        mChange.callOnClick();

    }

    @OnClick(R.id.change)
    public void onViewClicked() {
        if (position > 3) {
            position = 0;
        }
        mImageView.setImageResource(imageRes[position]);
        isDarkFont = dark[position];
        setImmersion();
        position++;
    }
}
