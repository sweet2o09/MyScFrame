package com.caihan.scframe.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntRange;

import com.blankj.utilcode.util.ImageUtils;
import com.caihan.scframe.utils.log.ScLog;


/**
 * @author caihan
 * @date 2018/3/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ScaleImage {
    private static final String TAG = "ScaleImage";
    private static final int MAX_WIDTH = 480;//xhdpi

    /**
     * 等比缩放
     * 根据宽度比,缩放高度
     *
     * @param imagePath
     * @return
     */
    public static Bitmap scale(String imagePath) {
        try {
            int degree = ImageUtils.getRotateDegree(imagePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(imagePath, options);
            //图片压缩
            ScLog.debug("scale before -- outWidth:" + options.outWidth
                    + " -- outHeight:" + options.outHeight + " -- degree = " + degree);
            int width = options.outWidth;
            int height = options.outHeight;
            if (options.outWidth == -1 && options.outHeight == -1) {
                return null;
            }
            float scaleWidth = 1;
            if (options.outWidth > MAX_WIDTH) {
                scaleWidth = (float) MAX_WIDTH / (float) options.outWidth;
            }
            int maxWidth = (int) (width * scaleWidth);
            int maxHeight = (int) (height * scaleWidth);
            int inSampleSize = 1;
            while ((options.outWidth >>= 1) >= maxWidth && (options.outHeight >>= 1) >= maxHeight) {
                inSampleSize <<= 1;
            }
            options.outWidth = maxWidth;
            options.outHeight = maxHeight;
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;

            options.inPurgeable = true;//内存不足时候可以被回收
            options.inInputShareable = true;//设置可以深拷贝

            ScLog.debug("scale -- outWidth:" + options.outWidth
                    + " -- outHeight:" + options.outHeight + " -- inSampleSize:" + inSampleSize);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            //如果图片有旋转要更正过来
            if (degree > 0) {
                bitmap = ImageUtils.rotate(bitmap, degree,0,0);
            }
            return bitmap;
//        return ImageUtils.scale(ImageUtils.getBitmap(imagePath),scaleWidth,scaleWidth);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap scale(String imagePath, @IntRange(from = 480, to = 720) int width,
                               @IntRange(from = 800, to = 1280) int height) {
        int degree = ImageUtils.getRotateDegree(imagePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePath, options);
        //图片压缩
        ScLog.d(TAG, "scale -- outWidth:" + options.outWidth
                + " -- outHeight:" + options.outHeight);
        float scaleWidth = 1;
        float scaleHeight = 1;
        if (options.outWidth > width) {
            scaleWidth = width / options.outWidth;
        }
        if (options.outHeight > height) {
            scaleHeight = height / options.outHeight;
        }
        options.outWidth = (int) (width * scaleWidth);
        options.outHeight = (int) (height * scaleHeight);
        options.inJustDecodeBounds = false;

        options.inPurgeable = true;//内存不足时候可以被回收
        options.inInputShareable = true;//设置可以深拷贝

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        //如果图片有旋转要更正过来
        if (degree > 0) {
            bitmap = ImageUtils.rotate(bitmap, degree,0,0);
        }
        return bitmap;
//        return ImageUtils.scale(ImageUtils.getBitmap(imagePath),scaleWidth,scaleWidth);
    }
}
