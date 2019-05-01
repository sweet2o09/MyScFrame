package com.caihan.scframe.widget.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 流式布局
 * <p>
 * 使用adapter的形式绑定并处理数据
 * 支持多种布局一同展示
 * 支持多行,单行,指定显示行数
 * 支持Item左对齐,居中对齐,右对齐
 * 支持行布局顶部对齐,居中对齐,底部对齐
 * 支持选中状态
 * 支持设置行间距
 * 支持设置item间距
 *
 * @author caihan
 * @date 2019/4/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ScFlowLayout extends ViewGroup {

    private static final String ALL_CHILD_WIDTH = "allChildWidth";
    private static final String ALL_CHILD_HEIGHT = "allChildHeight";

    public static final int LINE_GRAVITY_TOP = -1;
    public static final int LINE_GRAVITY_CENTER = 0;
    public static final int LINE_GRAVITY_BOTTOM = 1;

    public static final int TAG_GRAVITY_LEFT = -1;
    public static final int TAG_GRAVITY_CENTER = 0;
    public static final int TAG_GRAVITY_RIGHT = 1;

    protected int mMeasuredWidth;
    protected int mMeasuredHeight;
    //用来存储每一行的宽和高还有子View，主要让子类去实现几行的流式布局
    private volatile SparseArray<LineDes> mLineDesArray = new SparseArray();
    private int mLayoutDirection;
    private int mMaxShowRow = 0;
    private int mLineGravity = LINE_GRAVITY_TOP;
    private int mTagGravity = TAG_GRAVITY_LEFT;
    private float mItemSpace = 0;
    private float mLineSpace = 0;
    protected int mSelectedMax = -1;//-1为不限制数量
    private BaseTagFlowAdapter mAdapter;


    public ScFlowLayout(Context context) {
        this(context, null);
    }

    public ScFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initTypedArray(context, attrs, defStyleAttr);
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initTypedArray(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.ScFlowLayout, defStyleAttr, 0);

        mMaxShowRow = typedArray.getInt(R.styleable.ScFlowLayout_max_show_row, 0);
        mLineGravity = typedArray.getInt(R.styleable.ScFlowLayout_line_gravity, LINE_GRAVITY_TOP);
        mTagGravity = typedArray.getInt(R.styleable.ScFlowLayout_tag_gravity, TAG_GRAVITY_LEFT);
        mSelectedMax = typedArray.getInt(R.styleable.ScFlowLayout_max_select, -1);
        mItemSpace = typedArray.getDimension(R.styleable.ScFlowLayout_item_space, 0);
        mLineSpace = typedArray.getDimension(R.styleable.ScFlowLayout_line_space, 0);

        mLayoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        if (mLayoutDirection == LayoutDirection.RTL) {
            if (mTagGravity == TAG_GRAVITY_LEFT) {
                mTagGravity = TAG_GRAVITY_RIGHT;
            } else {
                mTagGravity = TAG_GRAVITY_LEFT;
            }
        }
        typedArray.recycle();  //注意回收
    }

    public int getLineGravity() {
        return mLineGravity;
    }

    public void setLineGravity(int mlineGravity) {
        this.mLineGravity = mlineGravity;
    }

    public int getTagGravity() {
        return mTagGravity;
    }

    public void setTagGravity(int tagGravity) {
        mTagGravity = tagGravity;
        if (mLayoutDirection == LayoutDirection.RTL) {
            if (mTagGravity == TAG_GRAVITY_LEFT) {
                mTagGravity = TAG_GRAVITY_RIGHT;
            } else {
                mTagGravity = TAG_GRAVITY_LEFT;
            }
        }
    }

    public int getMaxShowRow() {
        return mMaxShowRow;
    }

    public void setMaxShowRow(int maxShowRow) {
        mMaxShowRow = maxShowRow;
    }

    public int getSelectedMax() {
        return mSelectedMax;
    }

    public void setSelectedMax(int selectedMax) {
        mSelectedMax = selectedMax;
        if (mAdapter != null) {
            mAdapter.setSelectedMax(mSelectedMax);
        }
    }

    public BaseTagFlowAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseTagFlowAdapter adapter) {
        mAdapter = adapter;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widtMode = MeasureSpec.getMode(widthMeasureSpec);
        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //由于计算子view所占宽度
        Map<String, Integer> compute = compute(widthSize, widthMeasureSpec, heightMeasureSpec);

        mMeasuredWidth = widthSize;
        mMeasuredHeight = heightSize;
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mMeasuredWidth = compute.get(ALL_CHILD_WIDTH);
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mMeasuredHeight = compute.get(ALL_CHILD_HEIGHT);
            if (mLineDesArray.size() > 1) {
                //加上行间距
                mMeasuredHeight += mLineSpace * (mLineDesArray.size() - 1);
            }
        }
        if (mMaxShowRow != 0) {
            mMeasuredHeight = 0;
            int lineCount = Math.min(mLineDesArray.size(), mMaxShowRow);
            for (int i = 0; i < lineCount; i++) {
                mMeasuredHeight += mLineDesArray.get(i).rowsMaxHeight;
            }
            mMeasuredHeight += getPaddingBottom();
            if (lineCount > 1) {
                //加上行间距
                mMeasuredHeight += mLineSpace * (lineCount - 1);
            }
        }
        setMeasuredDimension(mMeasuredWidth, mMeasuredHeight);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        switch (mLineGravity) {
            case LINE_GRAVITY_TOP:
                onLayoutTop();
                break;
            case LINE_GRAVITY_CENTER:
                onLayoutCenter();
                break;
            case LINE_GRAVITY_BOTTOM:
                onLayoutBottom();
                break;
            default:
                onLayoutTop();
                break;
        }
    }

    private void onLayoutTop() {
        onChildLayout(LINE_GRAVITY_TOP);
    }

    private void onLayoutCenter() {
        onChildLayout(LINE_GRAVITY_CENTER);
    }

    private void onLayoutBottom() {
        onChildLayout(LINE_GRAVITY_BOTTOM);
    }

    public synchronized SparseArray<LineDes> getLineDesArray() {
        return mLineDesArray;
    }

    private void onChildLayout(int lineGravity) {
        formatAboveLine(lineGravity);
    }

    /**
     * 测量过程
     *
     * @param flowWidth 该view的宽度
     * @return 返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
     */
    private Map<String, Integer> compute(int flowWidth, int widthMeasureSpec, int heightMeasureSpec) {
        getLineDesArray().clear();
        int lineIndex = 0;//行数
        MarginLayoutParams marginParams;//子元素margin
        int rowsWidth = getPaddingLeft();//当前行已占宽度(注意需要加上paddingLeft)
        int columnHeight = getPaddingTop();//当前行顶部已占高度(注意需要加上paddingTop)
        int rowsMaxHeight = 0;//当前行所有子元素的最大高度（用于换行累加高度）
        LineDes lineDes = new LineDes();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //遍历去调用所有子元素的measure方法（child.getMeasuredHeight()才能获取到值，否则为0）
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获取元素测量宽度和高度
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            //获取元素的margin
            marginParams = (MarginLayoutParams) child.getLayoutParams();
            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            int childWidth = marginParams.leftMargin + marginParams.rightMargin + measuredWidth;
            int childHeight = marginParams.topMargin + marginParams.bottomMargin + measuredHeight;
            //该布局添加进去后会超过总宽度->换行
            if (rowsWidth + childWidth > flowWidth - getPaddingRight()) {
                getLineDesArray().put(lineIndex, lineDes);
                lineDes = new LineDes();
                lineIndex++;
                //重置行宽度
                rowsWidth = getPaddingLeft();
                //累加上该行子元素最大高度
                columnHeight += rowsMaxHeight;
                //重置该行最大高度
                rowsMaxHeight = childHeight;
            } else {
                rowsMaxHeight = Math.max(rowsMaxHeight, childHeight);
            }
            //累加上该行子元素宽度
            rowsWidth += childWidth;
            // 判断时占的宽段时加上margin计算，设置顶点位置时不包括margin位置，
            // 不然margin会不起作用，这是给View设置tag,在onlayout给子元素设置位置再遍历取出
            Rect rect = new Rect(
                    rowsWidth - childWidth + marginParams.leftMargin,
                    columnHeight + marginParams.topMargin,
                    rowsWidth - marginParams.rightMargin,
                    columnHeight + childHeight - marginParams.bottomMargin);
            child.setTag(rect);
            lineDes.rowsMaxHeight = rowsMaxHeight;
            lineDes.rowsMaxWidth = rowsWidth;
            lineDes.views.add(child);
            //累加上item间距
            rowsWidth += mItemSpace;
            if (i == getChildCount() - 1) {
                getLineDesArray().put(lineIndex, lineDes);
            }
        }

        //返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
        Map<String, Integer> flowMap = new HashMap<>();
        //单行
        if (lineIndex == 0) {
            flowMap.put(ALL_CHILD_WIDTH, (int) (rowsWidth - mItemSpace));
        } else {
            //多行
            flowMap.put(ALL_CHILD_WIDTH, flowWidth);
        }
        //FlowLayout测量高度 = 当前顶部已占高度 +当前行内子元素最大高度+FlowLayout的PaddingBottom
        int flowHeight = columnHeight + rowsMaxHeight + getPaddingBottom();
        flowMap.put(ALL_CHILD_HEIGHT, flowHeight);
        return flowMap;
    }

    private synchronized void formatAboveLine(int lineGravity) {
        int lineIndex = getLineDesArray().size();
        for (int i = 0; i < lineIndex; i++) {
            LineDes lineDes = getLineDesArray().get(i);
            List<View> views = lineDes.views;
            int viewIndex = views.size();
            for (int j = 0; j < viewIndex; j++) {
                View child = views.get(j);
                Rect rect = (Rect) child.getTag();
                int childWidth = (rect.bottom - rect.top);
                //如果是当前行的高度大于了该view的高度话，此时需要重新放该view了
                int diffvalue = 0;
                if (childWidth < lineDes.rowsMaxHeight) {
                    switch (lineGravity) {
                        case LINE_GRAVITY_TOP:
                            break;
                        case LINE_GRAVITY_CENTER:
                            diffvalue = (lineDes.rowsMaxHeight - childWidth) / 2;
                            rect.top += diffvalue;
                            rect.bottom += diffvalue;
                            break;
                        case LINE_GRAVITY_BOTTOM:
                            diffvalue = lineDes.rowsMaxHeight - childWidth;
                            rect.top += diffvalue;
                            rect.bottom += diffvalue;
                            break;
                        default:
                            break;
                    }
                }
                switch (mTagGravity) {
                    case TAG_GRAVITY_LEFT:
                        break;
                    case TAG_GRAVITY_CENTER:
                        diffvalue = (mMeasuredWidth - getPaddingRight() - lineDes.rowsMaxWidth) / 2;
                        if (diffvalue > 0) {
                            rect.left += diffvalue;
                            rect.right += diffvalue;
                        }
                        break;
                    case TAG_GRAVITY_RIGHT:
                        diffvalue = mMeasuredWidth - lineDes.rowsMaxWidth - getPaddingRight();
                        rect.left += diffvalue;
                        rect.right += diffvalue;
                        break;
                    default:
                        break;
                }
                //加上行间距
                rect.top += mLineSpace * i;
                rect.bottom += mLineSpace * i;
                child.layout(rect.left, rect.top, rect.right, rect.bottom);
            }
        }
        getLineDesArray().clear();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onDetachedFromWindow() {
        mLineDesArray.clear();
        mLineDesArray = null;
        super.onDetachedFromWindow();
    }
}
