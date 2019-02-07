package com.caihan.scframe.utils.imageloader.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CircleTransformation extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth;

    public CircleTransformation(Context context) {
        this(context, 0, 0);
    }

    public CircleTransformation(Context context, int borderWidthPx, @ColorRes int borderColor) {
        super(context);
        if (borderWidthPx != 0 && borderColor != 0) {
            mBorderWidth = borderWidthPx;
            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(context.getResources().getColor(borderColor));
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return toString();
    }

    @Override
    public String toString() {
        return "CircleTransformation(mBorderWidth=" + mBorderWidth + ")";
    }
}
