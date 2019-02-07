package app.caihan.myscframe.ui.imageloader;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.caihan.scframe.utils.imageloader.ScImageLoader;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图像处理
 *
 * @author caihan
 * @date 2019/2/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImageLoaderActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.image2)
    ImageView mImage2;
    @BindView(R.id.image3)
    ImageView mImage3;
    @BindView(R.id.image4)
    ImageView mImage4;

    private int mOutPutSize = 0;

    private static final String[] IMAGE_URL = {
            "http://qnimg.xingqiuxiuchang.cn/2379d373-20d1-4cd9-8589-89667af08ebb.jpg",
            "http://qnimg.xingqiuxiuchang.cn/02645d2e-aaf2-485a-9bda-40c3ce5cfba2.jpg",
            "http://qnimg.xingqiuxiuchang.cn/927dc809-797a-4d23-95d2-1b774d38f71f.jpg",
            "http://qnimg.xingqiuxiuchang.cn/7c2b179a-2e1b-41ce-adc1-4aaaaba83693.jpg",
            "http://qnimg.xingqiuxiuchang.cn/2c62706b-ac84-4cb8-baa3-ffd69e0961e9.jpg",
            "http://qnimg.xingqiuxiuchang.cn/0dc5a76b-3238-4c25-9b9b-30d94ed19dae.jpg",
            "http://qnimg.xingqiuxiuchang.cn/2379d373-20d1-4cd9-8589-89667af08ebb.jpg",
            "http://qnimg.xingqiuxiuchang.cn/02645d2e-aaf2-485a-9bda-40c3ce5cfba2.jpg",
            "http://qnimg.xingqiuxiuchang.cn/927dc809-797a-4d23-95d2-1b774d38f71f.jpg",
    };

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_image_loader;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("图像处理");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOutPutSize = SizeUtils.dp2px(120);
    }

    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image1:
                ScImageLoader.getInstance().display(IMAGE_URL[0], mImage1);
                break;
            case R.id.image2:
                ScImageLoader.getInstance().displayOverride(IMAGE_URL[1], mOutPutSize, mOutPutSize, mImage2);
                break;
            case R.id.image3:
                ScImageLoader.getInstance().display(IMAGE_URL[2], R.drawable.image_nine_photo_def, R.drawable.image_nine_photo_def, mImage3);
                break;
            case R.id.image4:
                ScImageLoader.getInstance().displayCircularImage(IMAGE_URL[3],
                        R.drawable.image_nine_photo_def, R.drawable.image_nine_photo_def, mImage4, SizeUtils.dp2px(4), R.color.colorMain);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScImageLoader.getInstance().resume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScImageLoader.getInstance().pause(this);
    }
}
