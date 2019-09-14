package app.caihan.myscframe.ui.imageloader;

import android.content.Intent;
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
    @BindView(R.id.toolbar_right_tv)
    TextView mToolbarRightTv;

    private int mOutPutSize = 0;

    public static final String[] IMAGE_URL = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724835470&di=ab204e0ef0220ea64dbcfa3ec217a465&imgtype=0&src=http%3A%2F%2Fandroid-screenimgs.25pp.com%2F87%2F312407_137894815202.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724795769&di=4d7c0d847b3d248581a57ad7c027e554&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201407%2F06%2F20140706211323_2SBXv.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724889240&di=28c23c01aceda3a1f9d0d0e9b9a0c5e6&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F3725cde1a652ccbe5b6c0964aaae069154aa9759.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724889236&di=beb334ce1770080b152fe5d6983a2ed6&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2Fdd0bf5cac4d5e55951b5264d06bbcb1893b2fa6b.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724835470&di=ab204e0ef0220ea64dbcfa3ec217a465&imgtype=0&src=http%3A%2F%2Fandroid-screenimgs.25pp.com%2F87%2F312407_137894815202.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725040245&di=c0423f633ae178cc56642b59c9528c90&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F35a85edf8db1cb13b43c9eabd654564e92584b35.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724819404&di=5439eb3a2f1d91e1c55261b875e6bb33&imgtype=0&src=http%3A%2F%2Fclubimg.dbankcdn.com%2Fdata%2Fattachment%2Fforum%2F201411%2F18%2F163244ds6zl26zxqxax04x.jpg",
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
        mToolbarRightTv.setText("列表");
        mToolbarRightTv.setVisibility(View.VISIBLE);
        mToolbarRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,ImageLoaderListActivity.class));
            }
        });
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
