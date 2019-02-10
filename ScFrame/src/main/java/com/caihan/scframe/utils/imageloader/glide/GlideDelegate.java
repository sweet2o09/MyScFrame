package com.caihan.scframe.utils.imageloader.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.caihan.scframe.utils.imageloader.DisplayBitmapDelegate;
import com.caihan.scframe.utils.imageloader.DisplayDrawableDelegate;
import com.caihan.scframe.utils.imageloader.ImageLoaderDelegate;
import com.caihan.scframe.utils.imageloader.glide.transformations.CircleTransformation;
import com.caihan.scframe.utils.imageloader.glide.transformations.RoundedCornersTransformation;

import java.io.File;

/**
 * DiskCacheStrategy.NONE 什么都不缓存
 * DiskCacheStrategy.SOURCE 只缓存全尺寸图
 * DiskCacheStrategy.RESULT 只缓存最终的加载图
 * DiskCacheStrategy.ALL 缓存所有版本图（默认行为）
 *
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class GlideDelegate implements ImageLoaderDelegate {


    private GifRequestBuilder getGifRequestB(Activity activity, String path, @DrawableRes int loadingResId,
                                             @DrawableRes int failResId) {
        return Glide.with(activity)
                .load(path)
                .asGif()
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);
    }

    private GifRequestBuilder getGifRequestB(Fragment fragment, String path, @DrawableRes int loadingResId,
                                             @DrawableRes int failResId) {
        return Glide.with(fragment)
                .load(path)
                .asGif()
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);
    }

    private GifRequestBuilder getGifRequestB(Context context, String path, @DrawableRes int loadingResId,
                                             @DrawableRes int failResId) {
        return Glide.with(context)
                .load(path)
                .asGif()
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);
    }

    private DrawableRequestBuilder getDrawableRequestB(Activity activity, String path, @DrawableRes int loadingResId,
                                                       @DrawableRes int failResId) {
        return Glide.with(activity)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//只缓存最终的加载图
                .skipMemoryCache(true)//跳过内存缓存
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);//图片加载失败后，显示的图片
    }

    private DrawableRequestBuilder getDrawableRequestB(Fragment fragment, String path, @DrawableRes int loadingResId,
                                                       @DrawableRes int failResId) {
        return Glide.with(fragment)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//只缓存最终的加载图
                .skipMemoryCache(true)//跳过内存缓存
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);//图片加载失败后，显示的图片
    }

    private DrawableRequestBuilder getDrawableRequestB(Context context, String path, @DrawableRes int loadingResId,
                                                       @DrawableRes int failResId) {
        return Glide.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//只缓存最终的加载图
                .skipMemoryCache(true)//跳过内存缓存
                .dontAnimate()//关闭动画
                .placeholder(loadingResId)//图片加载出来前，显示的图片
                .error(failResId);//图片加载失败后，显示的图片
    }


    @Override
    public void display(Activity activity, String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        if (isGif(path)) {
            //gif图
            if (widthPx == 0 || heightPx == 0) {
                getGifRequestB(activity, path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getGifRequestB(activity, path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        } else {
            if (widthPx == 0 || heightPx == 0) {
                getDrawableRequestB(activity, path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getDrawableRequestB(activity, path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        }
//                .animate(R.anim.glide_zoom_in)
    }

    @Override
    public void display(Fragment fragment, String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        if (isGif(path)) {
            //gif图
            if (widthPx == 0 || heightPx == 0) {
                getGifRequestB(fragment, path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getGifRequestB(fragment, path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        } else {
            if (widthPx == 0 || heightPx == 0) {
                getDrawableRequestB(fragment, path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getDrawableRequestB(fragment, path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void display(String path, @DrawableRes int loadingResId,
                        @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView) {

        if (isGif(path)) {
            //gif图
            if (widthPx == 0 || heightPx == 0) {
                getGifRequestB(imageView.getContext(), path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getGifRequestB(imageView.getContext(), path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        } else {
            if (widthPx == 0 || heightPx == 0) {
                getDrawableRequestB(imageView.getContext(), path, loadingResId, failResId)
                        .into(imageView);
            } else {
                getDrawableRequestB(imageView.getContext(), path, loadingResId, failResId)
                        .override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void displayCircularImage(Activity activity, String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {

        if (isGif(path)) {

        } else {
            CircleTransformation circular = new CircleTransformation(
                    activity, borderWidthPx, borderColor);

            DrawableRequestBuilder builder = Glide.with(activity)
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .bitmapTransform(circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }


    @Override
    public void displayCircularImage(Fragment fragment, String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {
        if (isGif(path)) {

        } else {
            CircleTransformation circular = new CircleTransformation(
                    fragment.getContext(), borderWidthPx, borderColor);

            DrawableRequestBuilder builder = Glide.with(fragment)
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .bitmapTransform(circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void displayCircularImage(String path, @DrawableRes int loadingResId,
                                     @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                     int borderWidthPx, @ColorRes int borderColor) {
        if (isGif(path)) {

        } else {
            CircleTransformation circular = new CircleTransformation(
                    imageView.getContext(), borderWidthPx, borderColor);

            DrawableRequestBuilder builder = Glide.with(imageView.getContext())
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .bitmapTransform(circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void displayRoundedCornersImage(
            Activity activity, String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {

        if (isGif(path)) {

        } else {
            RoundedCornersTransformation circular = new RoundedCornersTransformation(
                    activity, SizeUtils.dp2px(radiusPx), marginPx, borderWidthPx, borderColor, cornerType);

            DrawableRequestBuilder builder = Glide.with(activity)
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .transform(new CenterCrop(activity), circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void displayRoundedCornersImage(
            Fragment fragment, String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {
        if (isGif(path)) {

        } else {
            RoundedCornersTransformation circular = new RoundedCornersTransformation(
                    fragment.getContext(), SizeUtils.dp2px(radiusPx), marginPx, borderWidthPx, borderColor, cornerType);

            DrawableRequestBuilder builder = Glide.with(fragment)
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .transform(new CenterCrop(fragment.getContext()), circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public void displayRoundedCornersImage(
            String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
            int widthPx, int heightPx, ImageView imageView, int radiusPx, int marginPx,
            int borderWidthPx, @ColorRes int borderColor, RoundedCornersTransformation.CornerType cornerType) {
        if (isGif(path)) {

        } else {
            RoundedCornersTransformation circular = new RoundedCornersTransformation(
                    imageView.getContext(), SizeUtils.dp2px(radiusPx), marginPx, borderWidthPx, borderColor, cornerType);

            DrawableRequestBuilder builder = Glide.with(imageView.getContext())
                    .load(path)
                    .dontAnimate()//关闭动画
                    .placeholder(loadingResId)//图片加载出来前，显示的图片
                    .error(failResId)
                    .transform(new CenterCrop(imageView.getContext()), circular);

            if (widthPx == 0 || heightPx == 0) {
                builder.into(imageView);
            } else {
                builder.override(widthPx, heightPx)
                        .into(imageView);
            }
        }
    }

    @Override
    public <T extends View> void displayCustomView(
            Context context, final String path, T view, final DisplayDrawableDelegate drawableDelegate) {
        if (isGif(path)) {

        } else {

            Glide.with(context)
                    .load(path)
                    .into(new ViewTarget<T, GlideDrawable>(view) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            if (drawableDelegate != null) {
                                drawableDelegate.getDrawableSuccess(path, resource);
                            }
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            if (drawableDelegate != null) {
                                drawableDelegate.getDrawableError(path);
                            }
                        }
                    });
        }
    }

    @Override
    public void displayBitmap(Context context, final String path, int widthPx, int heightPx,
                              final DisplayBitmapDelegate bitmapDelegate) {
        //如果是gif图的话会显示第一帧
        Glide.with(context)
                .load(path)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(widthPx, heightPx) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        if (bitmapDelegate != null) {
                            bitmapDelegate.getBitmapSuccess(path, resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (bitmapDelegate != null) {
                            bitmapDelegate.getBitmapError(path);
                        }
                    }
                });
    }

    @Override
    public void displayVideo(File file, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(Uri.fromFile(file))
                .into(imageView);
    }

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void clearImageView(ImageView imageView) {
        Glide.clear(imageView);
    }

    /**
     * 判断是否是gif图
     *
     * @param path
     * @return
     */
    private boolean isGif(String path) {
        return getFileType(path).toLowerCase().equals("gif");
    }

    /**
     * 获取文件类型
     */
    private static String getFileType(String url) {
        if (url == null || url.equals("")) {
            return "";
        }
        int typeIndex = url.lastIndexOf(".");
        if (typeIndex != -1) {
            return url.substring(typeIndex + 1).toLowerCase();
        }
        return "";
    }

}
