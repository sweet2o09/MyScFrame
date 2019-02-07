package com.caihan.scframe.utils.imageloader.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorRes;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


/**
 * 圆角
 * {@see https://github.com/wasabeef/glide-transformations}
 *
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RoundedCornersTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID = "com.caihan.scframe.utils.imageloader.glide.transformations.RoundedCornersTransformation." + VERSION;

    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    private int mRadius;
    private int mDiameter;
    private int mMargin;
    private CornerType mCornerType;

    private int mBorderWidth;
    private int mBorderColor;

    /**
     * 圆形
     *
     * @param context
     * @param radiusPx
     */
    public RoundedCornersTransformation(Context context, int radiusPx) {
        this(context, radiusPx, 0, 0, 0, CornerType.ALL);
    }

    /**
     * 圆形
     *
     * @param context
     * @param radiusPx
     * @param marginPx
     * @param borderWidthPx
     * @param borderColor
     */
    public RoundedCornersTransformation(Context context, int radiusPx, int marginPx, int borderWidthPx, @ColorRes int borderColor) {
        this(context, radiusPx, marginPx, borderWidthPx, borderColor, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radiusPx, int marginPx) {
        this(context, radiusPx, marginPx, 0, 0, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radiusPx, int marginPx, int borderWidthPx,
                                        @ColorRes int borderColor, CornerType cornerType) {
        super(context);
        this.mRadius = radiusPx;
        this.mDiameter = this.mRadius * 2;
        this.mMargin = marginPx;
        this.mCornerType = cornerType;
        if (borderWidthPx != 0 && borderColor != 0) {
            this.mBorderWidth = borderWidthPx;
            this.mBorderColor = context.getResources().getColor(borderColor);
        } else {
            this.mBorderWidth = 0;
            this.mBorderColor = 0;
        }
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);

        Canvas canvas = new Canvas(bitmap);
        if (mBorderWidth != 0 && mBorderColor != 0) {
            Paint borderPaint = new Paint();
            borderPaint.setDither(true);
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(mBorderColor);
            RectF rectf = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectf, mRadius, mRadius, borderPaint);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return bitmap;
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - mMargin;
        float bottom = height - mMargin;

        switch (mCornerType) {
            case ALL:
                RectF rectF = new RectF(mBorderWidth, mBorderWidth, width - mBorderWidth, height - mBorderWidth);
//                RectF rectF = new RectF(mMargin, mMargin, right, bottom);
                canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, right, bottom);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, right, bottom);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_LEFT:
                drawOtherTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_RIGHT:
                drawOtherTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_LEFT:
                drawOtherBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_RIGHT:
                drawOtherBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_LEFT:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_RIGHT:
                drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, mMargin + mRadius, bottom), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - mRadius, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom - mRadius), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - mRadius, mMargin, right, bottom - mRadius), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right, bottom - mRadius), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom - mRadius), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mRadius, bottom), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
                                               float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
                                                  float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mDiameter, bottom), paint);
        canvas.drawRect(new RectF(mMargin + mDiameter, mMargin, right, bottom - mRadius), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
                                                   float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
    }

    @Override
    public String toString() {
        return "RoundedTransformation(mRadius=" + mRadius + ", mMargin=" + mMargin + ", mDiameter="
                + mDiameter + ", mCornerType=" + mCornerType.name() + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RoundedCornersTransformation &&
                ((RoundedCornersTransformation) o).mRadius == mRadius &&
                ((RoundedCornersTransformation) o).mDiameter == mDiameter &&
                ((RoundedCornersTransformation) o).mMargin == mMargin &&
                ((RoundedCornersTransformation) o).mCornerType == mCornerType;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + mRadius * 10000 + mDiameter * 1000 + mMargin * 100 + mCornerType.ordinal() * 10;
    }

    @Override
    public String getId() {
        return toString();
    }
}
