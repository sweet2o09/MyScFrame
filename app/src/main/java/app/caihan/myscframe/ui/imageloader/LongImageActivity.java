package app.caihan.myscframe.ui.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.caihan.scframe.utils.log.ScLog;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;

public class LongImageActivity extends BaseScActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.long_image_view)
    SubsamplingScaleImageView mImageView;
    @BindView(R.id.long_image_view2)
    ImageView mLongImageView;

    private static final int MAX_SIZE = 4096;
    private static final int MAX_SCALE = 8;

    private String mPicUrl = "http://yycmedia.image.alimmdn.com/ldy/2017/231a1b7e-2de0-48ce-b4c7-82e7c82a4ea7.jpg";

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_long_image;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        setBaseToolbarLayout(mToolbar, "长图处理");
        mLongImageView.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(mPicUrl)
                .asBitmap()
                .dontTransform()
                .dontAnimate()//关闭动画
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageHeight = resource.getHeight();
                        int imageWidth = resource.getWidth();
                        int screenHeight = ScreenUtils.getScreenHeight();
                        int screenWidth = ScreenUtils.getScreenWidth();
                        ScLog.debug("resource.getHeight() = " + imageHeight);
                        ScLog.debug("resource.getWidth() = " + imageWidth);
                        ScLog.debug("ScreenUtils.getScreenHeight() = " + screenHeight);
                        ScLog.debug("ScreenUtils.getScreenWidth() = " + screenWidth);
                        int realH = imageHeight * screenWidth / imageWidth;

                        if (realH >= screenHeight) {
                            ScLog.debug("long image = " + realH);

//                            Glide.with(mContext)
//                                    .load(mPicUrl)
//                                    .downloadOnly(new SimpleTarget<File>(){
//
//                                        @Override
//                                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                                            String path = resource.getAbsolutePath();
//                                            float scale = getImageScale(path);
//                                            ScLog.debug("File resource = " + path);
//                                            ImageViewState imageViewState = new ImageViewState(scale, new PointF(0, 0),0);
//                                            mImageView.setImage(ImageSource.uri(resource.getAbsolutePath()),imageViewState);
//                                        }
//                                    });

                            ViewGroup.LayoutParams layoutParams = mLongImageView.getLayoutParams();
                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            layoutParams.height = realH;
                            mLongImageView.setLayoutParams(layoutParams);
                            Glide.with(mContext)
                                    .load(mPicUrl)
                                    .dontAnimate()
                                    .override(screenWidth, realH)
                                    .into(mLongImageView);
                        }
                    }
                });
    }

    /**
     * 计算出图片初次显示需要放大倍数
     *
     * @param imagePath 图片的绝对路径
     */
    private float getImageScale(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return 2.0f;
        }

        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeFile(imagePath);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }

        if (bitmap == null) {
            return 2.0f;
        }

        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();

        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        bitmap.recycle();
        return scale;
    }
}
