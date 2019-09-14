package com.caihan.scframe.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.caihan.scframe.utils.imageloader.glide.GlideDelegate;
import com.caihan.scframe.utils.imageloader.glide.transformations.RoundedCornersTransformation;
import com.caihan.scframe.utils.log.ScLog;

import java.io.File;

/**
 * 图像处理
 *
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class ScImageLoader implements ImageLoaderDelegate {
    //单例模式->双重检查模式
    private static final String TAG = "ScImageLoader";
    //volatile表示去掉虚拟机优化代码,但是会消耗少许性能,可忽略
    private volatile static ScImageLoader sInstance = null;

    private ImageLoaderDelegate mDelegate = null;

    public static ScImageLoader getInstance() {
        if (sInstance == null) {
            //同步代码块
            synchronized (ScImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ScImageLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 自定义代理
     *
     * @param delegate
     * @return
     */
    public static ScImageLoader getInstance(@NonNull ImageLoaderDelegate delegate) {
        if (sInstance == null) {
            //同步代码块
            synchronized (ScImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ScImageLoader(delegate);
                }
            }
        }
        return sInstance;
    }

    private ScImageLoader() {
        mDelegate = new GlideDelegate();
        ScLog.debug(TAG, "ScImageLoader 初始化成功!");
    }

    private ScImageLoader(ImageLoaderDelegate delegate) {
        mDelegate = delegate;
        ScLog.debug(TAG, "ScImageLoader 初始化成功! 自定义图片处理接口!");
    }

    public void display(String path, ImageView imageView) {

        display(path, 0, 0, 0, 0, imageView);
    }

    public void displayOverride(String path, int widthPx, int heightPx, ImageView imageView) {

        display(path, 0, 0, widthPx, heightPx, imageView);
    }

    public void display(String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, ImageView imageView) {

        display(path, loadingResId, failResId, 0, 0, imageView);
    }

    public void displayCircularImage(String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {

        displayCircularImage(path, loadingResId, failResId, 0, 0,
                imageView, borderWidthPx, borderColor);
    }

    @Override
    public void display(Activity activity, String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        mDelegate.display(activity, path, loadingResId, failResId, widthPx, heightPx, imageView);
    }

    @Override
    public void display(Fragment fragment, String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        mDelegate.display(fragment, path, loadingResId, failResId, widthPx, heightPx, imageView);
    }

    @Override
    public void display(String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        mDelegate.display(path, loadingResId, failResId, widthPx, heightPx, imageView);
    }

    @Override
    public void displayCircularImage(Activity activity, String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {

        mDelegate.displayCircularImage(activity, path, loadingResId, failResId, widthPx, heightPx,
                imageView, borderWidthPx, borderColor);
    }


    @Override
    public void displayCircularImage(Fragment fragment, String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {

        mDelegate.displayCircularImage(fragment, path, loadingResId, failResId, widthPx, heightPx,
                imageView, borderWidthPx, borderColor);
    }

    @Override
    public void displayCircularImage(String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {

        mDelegate.displayCircularImage(path, loadingResId, failResId, widthPx, heightPx,
                imageView, borderWidthPx, borderColor);
    }

    @Override
    public void displayRoundedCornersImage(
            Activity activity, String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {

        mDelegate.displayRoundedCornersImage(activity, path, loadingResId, failResId, widthPx, heightPx,
                imageView, radiusPx, marginPx, borderWidthPx, borderColor, cornerType);
    }

    @Override
    public void displayRoundedCornersImage(
            Fragment fragment, String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {

        mDelegate.displayRoundedCornersImage(fragment, path, loadingResId, failResId, widthPx, heightPx,
                imageView, radiusPx, marginPx, borderWidthPx, borderColor, cornerType);
    }

    @Override
    public void displayRoundedCornersImage(
            String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {

        mDelegate.displayRoundedCornersImage(path, loadingResId, failResId, widthPx, heightPx,
                imageView, radiusPx, marginPx, borderWidthPx, borderColor, cornerType);
    }

    @Override
    public <T extends View> void displayCustomView(
            Context context, final String path, T view, final DisplayDrawableDelegate drawableDelegate) {

        mDelegate.displayCustomView(context, path, view, drawableDelegate);
    }

    @Override
    public void displayBitmap(Context context, final String path, int widthPx, int heightPx,
                              final DisplayBitmapDelegate bitmapDelegate) {

        mDelegate.displayBitmap(context, path, widthPx, heightPx, bitmapDelegate);
    }

    @Override
    public void displayVideo(File file, ImageView imageView) {
        mDelegate.displayVideo(file, imageView);
    }

    @Override
    public void pause(Context context) {
        mDelegate.pause(context);
    }

    @Override
    public void resume(Context context) {
        mDelegate.resume(context);
    }

    @Override
    public void clearImageView(ImageView imageView) {
        mDelegate.clearImageView(imageView);
    }
}