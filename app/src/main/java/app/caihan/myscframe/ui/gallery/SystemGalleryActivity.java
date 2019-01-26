package app.caihan.myscframe.ui.gallery;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.caihan.scframe.permission.PermissionGroup;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.utils.PhotoUtils;
import com.caihan.scframe.utils.log.ScLog;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 系统相机调用
 *
 * @author caihan
 * @date 2019/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class SystemGalleryActivity extends BaseScActivity implements PhotoUtils.onResultListener {

    private static final int CHOOSE_PHOTO = 0;
    private static final int TAKE_PHOTO = 1;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.system_gallery_show_iv)
    ImageView mSystemGalleryShowIv;
    @BindView(R.id.system_gallery_choose_btn)
    Button mSystemGalleryChooseBtn;
    @BindView(R.id.system_gallery_take_photo_btn)
    Button mSystemGalleryTakePhotoBtn;

    private PhotoUtils mPhotoUtils;

    private int mOutPutSize = 0;

    @Override
    protected void onDestroy() {
        if (mPhotoUtils != null) {
            mPhotoUtils.onDestroy();
            mPhotoUtils = null;
        }
        super.onDestroy();
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_system_gallery;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("九宫格展示");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOutPutSize = SizeUtils.dp2px(200);
    }

    @OnClick({R.id.system_gallery_show_iv, R.id.system_gallery_choose_btn, R.id.system_gallery_take_photo_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.system_gallery_show_iv:
                //图片
                showToast("点击了图片");
                break;
            case R.id.system_gallery_choose_btn:
                //系统相册
                getPermission(CHOOSE_PHOTO);
                break;
            case R.id.system_gallery_take_photo_btn:
                //系统拍照
                getPermission(TAKE_PHOTO);
                break;
        }
    }

    private void getPermission(int type) {
        requestPermission(new OnPermissionListener() {
            @Override
            public void onPermissionSuccessful() {
                initPhotoUtils();
                switch (type) {
                    case CHOOSE_PHOTO:
                        choosePhoto();
                        break;
                    case TAKE_PHOTO:
                        takePhoto();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPermissionFailure() {

            }
        }, PermissionGroup.CAMERA, PermissionGroup.STORAGE);
    }

    private void initPhotoUtils(){
        if (mPhotoUtils == null){
            mPhotoUtils = new PhotoUtils.Builder(this)
                    .setEnableCrop(true)
                    .setOutputX(mOutPutSize)
                    .setOutputY(mOutPutSize)
//                    .setFileOptionsUtils(new FileOptionsUtils(mContext))
                    .setOnResultListener(this)
                    .build();
        }
    }

    /**
     * 系统相册
     */
    private void choosePhoto() {
        mPhotoUtils.toSystemGallery();
    }

    /**
     * 系统拍照
     */
    private void takePhoto() {
        mPhotoUtils.toSystemCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPhotoUtils != null && mPhotoUtils.isPhotoRequest(requestCode)) {
            mPhotoUtils.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void resultCanceled() {
        showToast("取消");
    }

    @Override
    public void resultTakeCamera(String imagePath, boolean isDelCameraFile) {
        showToast("相机返回");
        ScLog.debug("相机返回 imagePath = " + imagePath);
        Glide.with(mContext)
                .load(imagePath)
                .placeholder(R.drawable.image_nine_photo_def)
                .fallback(R.drawable.image_nine_photo_def)
                .override(SizeUtils.dp2px(200), SizeUtils.dp2px(200))
                .dontAnimate()
                .into(mSystemGalleryShowIv);
    }

    @Override
    public void resultTakeGallery(String imagePath) {
        showToast("相册返回");
        ScLog.debug("相册返回 imagePath = " + imagePath);
        Glide.with(mContext)
                .load(imagePath)
                .placeholder(R.drawable.image_nine_photo_def)
                .fallback(R.drawable.image_nine_photo_def)
                .override(SizeUtils.dp2px(200), SizeUtils.dp2px(200))
                .dontAnimate()
                .into(mSystemGalleryShowIv);
    }

    @Override
    public void resultTakeCrop(String imagePath, boolean isDelCameraFile, boolean isDelCropFile) {
        showToast("裁剪返回");
        ScLog.debug("裁剪返回 imagePath = " + imagePath);
        Glide.with(mContext)
                .load(imagePath)
                .placeholder(R.drawable.image_nine_photo_def)
                .fallback(R.drawable.image_nine_photo_def)
                .override(mOutPutSize, mOutPutSize)
                .dontAnimate()
                .into(mSystemGalleryShowIv);
    }
}
