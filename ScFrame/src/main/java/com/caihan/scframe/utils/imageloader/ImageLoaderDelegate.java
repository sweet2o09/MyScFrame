package com.caihan.scframe.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.caihan.scframe.utils.imageloader.glide.transformations.RoundedCornersTransformation;

import java.io.File;

/**
 * 图像处理代理接口
 *
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface ImageLoaderDelegate {


    /**
     * 加载图片
     *
     * @param path         本地或者服务器路径
     * @param loadingResId 加载前占位符
     * @param failResId    错误加载占位符
     * @param widthPx      压缩参数-宽度
     * @param heightPx     压缩参数-高度
     */
    void display(Activity activity, String path, @DrawableRes int loadingResId,
                 @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView);

    /**
     * 加载图片
     *
     * @param path         本地或者服务器路径
     * @param loadingResId 加载前占位符
     * @param failResId    错误加载占位符
     * @param widthPx      压缩参数-宽度
     * @param heightPx     压缩参数-高度
     */
    void display(Fragment fragment, String path, @DrawableRes int loadingResId,
                 @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView);

    /**
     * 加载图片
     *
     * @param path         本地或者服务器路径
     * @param loadingResId 加载前占位符
     * @param failResId    错误加载占位符
     * @param widthPx      压缩参数-宽度
     * @param heightPx     压缩参数-高度
     */
    void display(String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
                 int widthPx, int heightPx, ImageView imageView);

    /**
     * 加载圆形图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param borderWidthPx 圆形参数-边框宽度
     * @param borderColor   圆形参数-边框颜色
     */
    void displayCircularImage(Activity activity, String path, @DrawableRes int loadingResId,
                              @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                              int borderWidthPx, @ColorRes int borderColor);

    /**
     * 加载圆形图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param borderWidthPx 圆形参数-边框宽度
     * @param borderColor   圆形参数-边框颜色
     */
    void displayCircularImage(Fragment fragment, String path, @DrawableRes int loadingResId,
                              @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                              int borderWidthPx, @ColorRes int borderColor);

    /**
     * 加载圆形图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param borderWidthPx 圆形参数-边框宽度
     * @param borderColor   圆形参数-边框颜色
     */
    void displayCircularImage(String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
                              int widthPx, int heightPx, ImageView imageView,
                              int borderWidthPx, @ColorRes int borderColor);

    /**
     * 加载圆角图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param radiusPx      圆角参数-圆角大小
     * @param marginPx      圆角参数-间距
     * @param borderWidthPx 圆角参数-边框宽度
     * @param borderColor   圆角参数-边框颜色
     * @param cornerType    圆角参数-圆角类型
     */
    void displayRoundedCornersImage(Activity activity, String path, @DrawableRes int loadingResId,
                                    @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                    int radiusPx, int marginPx, int borderWidthPx, @ColorRes int borderColor,
                                    RoundedCornersTransformation.CornerType cornerType);

    /**
     * 加载圆角图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param radiusPx      圆角参数-圆角大小
     * @param marginPx      圆角参数-间距
     * @param borderWidthPx 圆角参数-边框宽度
     * @param borderColor   圆角参数-边框颜色
     * @param cornerType    圆角参数-圆角类型
     */
    void displayRoundedCornersImage(Fragment fragment, String path, @DrawableRes int loadingResId,
                                    @DrawableRes int failResId, int widthPx, int heightPx, ImageView imageView,
                                    int radiusPx, int marginPx, int borderWidthPx, @ColorRes int borderColor,
                                    RoundedCornersTransformation.CornerType cornerType);

    /**
     * 加载圆角图片
     *
     * @param path          本地或者服务器路径
     * @param loadingResId  加载前占位符
     * @param failResId     错误加载占位符
     * @param widthPx       压缩参数-宽度
     * @param heightPx      压缩参数-高度
     * @param radiusPx      圆角参数-圆角大小
     * @param marginPx      圆角参数-间距
     * @param borderWidthPx 圆角参数-边框宽度
     * @param borderColor   圆角参数-边框颜色
     * @param cornerType    圆角参数-圆角类型
     */
    void displayRoundedCornersImage(String path, @DrawableRes int loadingResId, @DrawableRes int failResId,
                                    int widthPx, int heightPx, ImageView imageView, int radiusPx,
                                    int marginPx, int borderWidthPx, @ColorRes int borderColor,
                                    RoundedCornersTransformation.CornerType cornerType);

    /**
     * 向自定义布局中加入图片
     *
     * @param path             本地或者服务器路径
     * @param drawableDelegate 监听器
     * @param <T>
     */
    <T extends View> void displayCustomView(Context context, final String path, T view,
                                            final DisplayDrawableDelegate drawableDelegate);

    /**
     * 只加载图片
     *
     * @param path           本地或者服务器路径
     * @param widthPx        压缩参数-宽度
     * @param heightPx       压缩参数-高度
     * @param bitmapDelegate 监听器
     */
    void displayBitmap(Context context, final String path, int widthPx, int heightPx,
                       final DisplayBitmapDelegate bitmapDelegate);

    /**
     * 加载视频
     *
     * @param file 本地视频路径
     */
    void displayVideo(File file, ImageView imageView);


    void pause(Context context);

    void resume(Context context);

    void clearImageView(ImageView imageView);
}
