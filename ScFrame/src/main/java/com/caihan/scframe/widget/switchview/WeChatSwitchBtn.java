package com.caihan.scframe.widget.switchview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.blankj.utilcode.util.LogUtils;
import com.caihan.scframe.R;
import com.caihan.scframe.utils.motionevent.GestureUtils;


/**
 * Created by caihan on 2017/4/6.
 * 仿微信SwitchBtn/仿iOS的UiSwitch控件
 * <p>
 * * Paint类介绍
 * <p>
 * Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色，
 * 样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
 * 大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关。
 * <p>
 * 1.图形绘制
 * setARGB(int a,int r,int g,int b);
 * 设置绘制的颜色，a代表透明度，r，g，b代表颜色值。
 * <p>
 * setAlpha(int a);
 * 设置绘制图形的透明度。
 * <p>
 * setColor(int color);
 * 设置绘制的颜色，使用颜色值来表示，该颜色值包括透明度和RGB颜色。
 * <p>
 * setAntiAlias(boolean aa);
 * 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
 * <p>
 * setDither(boolean dither);
 * 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
 * <p>
 * setFilterBitmap(boolean filter);
 * 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
 * 速度，本设置项依赖于dither和xfermode的设置
 * <p>
 * setMaskFilter(MaskFilter maskfilter);
 * 设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等       *
 * setColorFilter(ColorFilter colorfilter);
 * 设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
 * <p>
 * setPathEffect(PathEffect effect);
 * 设置绘制路径的效果，如点画线等
 * <p>
 * setShader(Shader shader);
 * 设置图像效果，使用Shader可以绘制出各种渐变效果
 * <p>
 * setShadowLayer(float radius ,float dx,float dy,int color);
 * 在图形下面设置阴影层，产生阴影效果，radius为阴影的角度，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
 * <p>
 * setStyle(Paint.Style style);
 * 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
 * <p>
 * setStrokeCap(Paint.Cap cap);
 * 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
 * Cap.ROUND,或方形样式Cap.SQUARE
 * <p>
 * setSrokeJoin(Paint.Join join);
 * 设置绘制时各图形的结合方式，如平滑效果等
 * <p>
 * setStrokeWidth(float width);
 * 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度
 * <p>
 * setXfermode(Xfermode xfermode);
 * 设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
 * <p>
 * 2.文本绘制
 * setFakeBoldText(boolean fakeBoldText);
 * 模拟实现粗体文字，设置在小字体上效果会非常差
 * <p>
 * setSubpixelText(boolean subpixelText);
 * 设置该项为true，将有助于文本在LCD屏幕上的显示效果
 * <p>
 * setTextAlign(Paint.Align align);
 * 设置绘制文字的对齐方向
 */
public class WeChatSwitchBtn extends View implements View.OnClickListener {
    private static final String TAG = "WeChatSwitchBtn";

    /**
     * 底板参数
     */
    private float mBgSideWidth = 0;//底板描边参数
    private boolean mIsBgSide = false;//是否需要底板描边,默认false
    private int mBgSideColor = Color.parseColor("#EEEEEE");//底板的描边颜色
    private int mOffBgColor = Color.parseColor("#EEEEEE");//关闭时底板的颜色
    private int mOpenBgColor = Color.parseColor("#4ebb7f");//开启时底板的颜色
    private int mRealBgColor;//底板真实颜色,状态切换会因此该参数变化
    private float mBgRadius = 0;//底板半径

    /**
     * 圆球参数
     */
    private float mBallSideWidth = 12;//圆球描边高度
    private boolean mIsBallSide = false;//是否需要圆球描边,默认false
    private int mBallSideColor = Color.parseColor("#F0F0F0");//圆球的描边颜色
    private int mBallColor = Color.WHITE;//圆球颜色
    private float mBallRadius = 0;//圆球半径

    /**
     * 其他参数
     */
    private RectF mRect = new RectF();//画布
    private Paint mBgStrokePaint = new Paint();//底板描边画笔
    private Paint mBgPaint = new Paint();//底板画笔
    private Paint mBallStrokePaint = new Paint();//圆球描边画笔
    private Paint mBallPaint = new Paint();//圆球画笔
    private static final int DEFAULT_STROKE_WIDTH = 2;//默认描边宽度
    private static final float DEF_H = 30;//控件默认高度
    private static final float DEF_W = 60;//控件默认宽度DEF_H * 2
    private float ballStartX = 0;//圆球的起始X位置,切换时平移改变它
    private float mBallY = 0;//圆球的起始X位置，不变
    private float mOffBallX = 0;//关闭时,圆球的水平位置
    private float mAllSideWidth = 0;//所有边线间距总和,用于圆球绘制与动效

    private boolean mIsToggleOn = false;//当前开关标记
    private boolean isTouchEvent = false;//是否由滑动事件消费掉
    private boolean isMoveing = false;//是否还在Touch相应中

    private OnToggleListener mOnToggleListener;//toggle事件监听
    private GestureUtils mGestureUtils;//手势工具类

    public WeChatSwitchBtn(Context context) {
        this(context, null);
    }

    public WeChatSwitchBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatSwitchBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 添加监听
     *
     * @param listener
     */
    public void setOnToggleListener(OnToggleListener listener) {
        mOnToggleListener = listener;
    }

    /**
     * 获取控件状态
     *
     * @return
     */
    public boolean getSwitchState() {
        return mIsToggleOn;
    }

    /**
     * 界面上设置开关初始状态
     *
     * @param open
     */
    public void setChecked(final boolean open) {
        this.post(new Runnable() {
            @Override
            public void run() {
                touchToggle(open);
            }
        });
    }

    public float getBallStartX() {
        return ballStartX;
    }

    public void setBallStartX(float ballStartX) {
        this.ballStartX = ballStartX;
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) return;
        initTypedArray(context, attrs, defStyleAttr);
        initPaint();
        setOnClickListener(this);
        setEnabled(true);
        mGestureUtils = new GestureUtils();
        mRealBgColor = mOffBgColor;//默认关闭,因此底板颜色默认使用关闭状态的颜色
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initTypedArray(Context context, AttributeSet attrs, int defStyleAttr) {
        LogUtils.d(TAG, "initTypedArray");
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.WeChatSwitchBtn, defStyleAttr, 0);
        //底板
        mOffBgColor = typedArray.getColor(
                R.styleable.WeChatSwitchBtn_switch_bg_color_closed,
                Color.parseColor("#EEEEEE"));

        mOpenBgColor = typedArray.getColor(
                R.styleable.WeChatSwitchBtn_switch_bg_color_open,
                Color.parseColor("#4ebb7f"));

        mIsBgSide = typedArray.getBoolean(
                R.styleable.WeChatSwitchBtn_switch_bg_side_status, false);

        mBgSideWidth = typedArray.getDimension(
                R.styleable.WeChatSwitchBtn_switch_bg_side_size,
                mIsBgSide ? DEFAULT_STROKE_WIDTH : 0);

        mBgSideColor = typedArray.getColor(
                R.styleable.WeChatSwitchBtn_switch_bg_side_color,
                mOffBgColor);
        //圆球
        mBallColor = typedArray.getColor(
                R.styleable.WeChatSwitchBtn_switch_ball_color, Color.WHITE);

        mIsBallSide = typedArray.getBoolean(
                R.styleable.WeChatSwitchBtn_switch_ball_side_status, false);

        mBallSideWidth = typedArray.getDimension(
                R.styleable.WeChatSwitchBtn_switch_ball_side_size,
                mIsBallSide ? DEFAULT_STROKE_WIDTH : 0);

        mBallSideColor = typedArray.getColor(
                R.styleable.WeChatSwitchBtn_switch_ball_side_color, Color.parseColor("#F0F0F0"));

        typedArray.recycle();  //注意回收
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mBgStrokePaint = createPaint(mBgSideColor, mBgSideWidth, Paint.Style.STROKE);
        mBgPaint = createPaint(mOffBgColor, 0, Paint.Style.FILL);
        mBallStrokePaint = createPaint(mBallSideColor, mBallSideWidth, Paint.Style.STROKE);
        mBallPaint = createPaint(mBallColor, 0, Paint.Style.FILL);
    }

    /**
     * @param paintColor
     * @param lineWidth
     * @param style      Paint.Style.FILL //填充
     *                   Paint.Style.FILL_AND_STROKE //填充加描边
     *                   Paint.Style.STROKE //描边
     * @return
     */
    private Paint createPaint(@ColorInt int paintColor, float lineWidth, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(paintColor);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);//设置防抖动
        paint.setStyle(style);
        /**
         * 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
         */
        paint.setAntiAlias(true);
        /**
         * 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，
         * 如圆形样式Cap.ROUND,或方形样式Cap.SQUARE
         */
        paint.setStrokeCap(Paint.Cap.ROUND);
        /**
         * 设置绘制时各图形的结合方式，如平滑效果等
         */
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int resultWidth = wSize;
        int resultHeight = hSize;
        Resources r = Resources.getSystem();
        //lp = wrapcontent时 指定默认值
        if (wMode == MeasureSpec.AT_MOST) {
            resultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_W, r.getDisplayMetrics());
        }
        if (hMode == MeasureSpec.AT_MOST) {
            resultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_H, r.getDisplayMetrics());
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mBgRadius = Math.min(getWidth(), getHeight()) * 0.5f;
        if (mIsBallSide && mBallSideWidth == DEFAULT_STROKE_WIDTH) {
            //	默认描边宽度是控件宽度的1/30
            mBallSideWidth = getWidth() * 1.0f / 30;
        }
        mAllSideWidth = mBgSideWidth + mBallSideWidth;
        mBallRadius = mBgRadius - mAllSideWidth;
        ballStartX = mAllSideWidth;
        mBallY = mAllSideWidth;
        mOffBallX = getMeasuredWidth() - mBallRadius * 2 - ballStartX;
        mRealBgColor = mOffBgColor;//默认关闭,因此底板颜色默认使用关闭状态的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint)
         * 在rx为宽度的一半，ry为高度的一半时，刚好是一个椭圆
         */
        drawBgStroke(canvas);
        drawBg(canvas);
//        drawBallStroke(canvas);
        drawBall(canvas);
    }

    /**
     * 底板描边
     * 考虑到描边线宽,因此实际画布大小会略小于控件大小,相差描边线宽,
     * 这样描边才会平滑不至于因控件高,宽受限制导致四个角的线比较粗
     * http://blog.csdn.net/zhjali123/article/details/49806533
     *
     * @param canvas
     */
    private void drawBgStroke(Canvas canvas) {
        if (mIsBgSide) {
            mRect.set(mBgSideWidth,
                    mBgSideWidth,
                    getMeasuredWidth() - mBgSideWidth,
                    getMeasuredHeight() - mBgSideWidth);
            mBgStrokePaint.setColor(mRealBgColor);
            canvas.drawRoundRect(mRect, mBgRadius, mBgRadius, mBgStrokePaint);
        }
    }

    /**
     * 底板着色,rx&ry采用圆球的半径
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        mRect.set(mBgSideWidth,
                mBgSideWidth,
                getMeasuredWidth() - mBgSideWidth,
                getMeasuredHeight() - mBgSideWidth);

        mBgPaint.setColor(mRealBgColor);
        canvas.drawRoundRect(mRect, mBgRadius, mBgRadius, mBgPaint);
    }

    /**
     * 圆球描边
     * <p>
     * 暂时废弃,用drawBall实现
     *
     * @param canvas
     */
    private void drawBallStroke(Canvas canvas) {
        if (mIsBallSide) {
            mRect.set(mBgSideWidth,
                    mBgSideWidth,
                    mBgSideWidth + mBgRadius * 2,
                    mBgSideWidth + mBgRadius * 2);
            mBallStrokePaint.setColor(mBallSideColor);
            canvas.drawRoundRect(mRect, mBgRadius, mBgRadius, mBallStrokePaint);
        }
    }

    /**
     * 圆球着色
     *
     * @param canvas
     */
    private void drawBall(Canvas canvas) {
        mRect.set(ballStartX,
                mBallY,
                mBallRadius * 2 + ballStartX,
                mBallRadius * 2 + mBallY);

        mBallPaint.setColor(mBallColor);
        canvas.drawRoundRect(mRect, mBallRadius, mBallRadius, mBallPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGestureUtils.actionDown(event);
                isTouchEvent = false;
                isMoveing = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureUtils.actionMove(event);
                if (mGestureUtils.getGesture(GestureUtils.Gesture.PullLeft)) {
                    //左滑,关闭
                    isTouchEvent = true;
                    touchToggle(false);
                    return true;
                } else if (mGestureUtils.getGesture(GestureUtils.Gesture.PullRight)) {
                    //右滑,开启
                    isTouchEvent = true;
                    touchToggle(true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                isMoveing = false;
                if (isTouchEvent) {
                    //不会触发Onclick事件了
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        onClickToggle();
    }

    /**
     * Touch事件触发
     * mIsToggleOn是当前状态,当mIsToggleOn != open时做出相应
     *
     * @param open 是否打开
     */
    private void touchToggle(boolean open) {
        if (!isMoveing) {
            isMoveing = true;
            if (mIsToggleOn != open) {
                if (mIsToggleOn) {
                    toggleOff();
                } else {
                    toggleOn();
                }
                mIsToggleOn = !mIsToggleOn;
                if (mOnToggleListener != null) {
                    mOnToggleListener.onSwitchChangeListener(mIsToggleOn);
                }
            }
        }
    }

    /**
     * Onclick事件触发
     */
    private void onClickToggle() {
        if (mIsToggleOn) {
            toggleOff();
        } else {
            toggleOn();
        }
        mIsToggleOn = !mIsToggleOn;
        if (mOnToggleListener != null) {
            mOnToggleListener.onSwitchChangeListener(mIsToggleOn);
        }
    }

    /**
     * 打开开关
     */
    public void toggleOn() {
        //底板颜色渐变和圆球滑动通过属性动画来实现
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "ballStartX", mAllSideWidth, mOffBallX);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOffBgColor, mOpenBgColor);
                invalidate();
            }
        });
    }

    /**
     * 关闭开关
     */
    public void toggleOff() {
        //底板颜色渐变和圆球滑动通过属性动画来实现
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "ballStartX", mOffBallX, mAllSideWidth);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOpenBgColor, mOffBgColor);
                invalidate();
            }
        });
    }

    /**
     * 计算切换时的手柄槽的颜色
     *
     * @param fraction   动画播放进度
     * @param startColor 起始颜色
     * @param endColor   终止颜色
     */
    public void calculateColor(float fraction, int startColor, int endColor) {
        final int fb = Color.blue(startColor);
        final int fr = Color.red(startColor);
        final int fg = Color.green(startColor);

        final int tb = Color.blue(endColor);
        final int tr = Color.red(endColor);
        final int tg = Color.green(endColor);

        //RGB三通道线性渐变
        int sr = (int) (fr + fraction * (tr - fr));
        int sg = (int) (fg + fraction * (tg - fg));
        int sb = (int) (fb + fraction * (tb - fb));
        //范围限定
        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        mRealBgColor = Color.rgb(sr, sg, sb);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

}
