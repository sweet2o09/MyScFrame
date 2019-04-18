package com.caihan.scframe.widget.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.scframe.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流式布局2
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

    //用来存储每一行的高度，主要让子类去实现几行的流式布局
    protected SparseArray<Integer> mHeightLines = new SparseArray<>();
    protected int mMeasuredWidth;
    protected int mMeasuredHeight;
    private volatile SparseArray<LineDes> mLineDesArray = new SparseArray();
    private int mMaxShowRow = 0;
    private int mLineGravity = LINE_GRAVITY_TOP;

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
        //对齐方式
        mMaxShowRow = typedArray.getInt(R.styleable.ScFlowLayout_max_show_row, 0);
        mLineGravity = typedArray.getInt(R.styleable.ScFlowLayout_line_gravity, LINE_GRAVITY_TOP);
        typedArray.recycle();  //注意回收
    }

    public int getLineGravity() {
        return mLineGravity;
    }

    public void setLineGravity(int mlineGravity) {
        this.mLineGravity = mlineGravity;
    }

    public int getMaxShowRow() {
        return mMaxShowRow;
    }

    public void setMaxShowRow(int maxShowRow) {
        mMaxShowRow = maxShowRow;
        requestLayout();
        invalidate();
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
        //由于计算子view所占宽度，这里传值需要自身减去PaddingRight宽度，PaddingLeft会在接下来计算子元素位置时加上
        Map<String, Integer> compute = compute(widthSize - getPaddingRight(), widthMeasureSpec, heightMeasureSpec);

        mMeasuredWidth = widthSize;
        mMeasuredHeight = heightSize;
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mMeasuredWidth = compute.get(ALL_CHILD_WIDTH);
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mMeasuredHeight = compute.get(ALL_CHILD_HEIGHT);
        }
        if (mMaxShowRow != 0) {
            mMeasuredHeight = 0;
            int lineCount = Math.min(mHeightLines.size(), mMaxShowRow);
            for (int i = 0; i < lineCount; i++) {
                mMeasuredHeight += mHeightLines.get(i);
            }
            mMeasuredHeight += getPaddingTop() + getPaddingBottom();
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
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = (Rect) getChildAt(i).getTag();
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
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
        getLineDesArray().clear();
        int lineIndex = 0;//行数
        int rowsMaxHeight = 0;
        int rowsWidth = getPaddingLeft();//当前行已占宽度(注意需要加上paddingLeft)
        MarginLayoutParams marginParams;//子元素margin
        LineDes lineDes = new LineDes();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //获取元素测量宽度和高度
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            //获取元素的margin
            marginParams = (MarginLayoutParams) child.getLayoutParams();
            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            int childWidth = marginParams.leftMargin + marginParams.rightMargin + measuredWidth;
            //该布局添加进去后会超过总宽度->换行
            if (rowsWidth + childWidth > mMeasuredWidth) {
                getLineDesArray().put(lineIndex, lineDes);
                lineDes = new LineDes();
                lineIndex++;
                //formatAboveLine中已经清空,算是新的一行
                //重置该行最大高度
                rowsMaxHeight = measuredHeight;
                //重置行宽度
                rowsWidth = getPaddingLeft() + getPaddingRight();
            } else {
                //子布局高度与最大行高作比较
                rowsMaxHeight = Math.max(rowsMaxHeight, measuredHeight);
            }
            //累加上该行子元素宽度
            rowsWidth += childWidth;
            lineDes.rowsMaxHeight = rowsMaxHeight;
            lineDes.views.add(child);
            if (i == getChildCount() - 1) {
                getLineDesArray().put(lineIndex, lineDes);
            }
        }
        formatAboveLine(lineGravity);
    }

    /**
     * 测量过程
     *
     * @param flowWidth 该view的宽度
     * @return 返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
     */
    private Map<String, Integer> compute(int flowWidth, int widthMeasureSpec, int heightMeasureSpec) {
        mHeightLines.clear();
        int lineIndex = 0;//行数
        MarginLayoutParams marginParams;//子元素margin
        int rowsWidth = getPaddingLeft();//当前行已占宽度(注意需要加上paddingLeft)
        int columnHeight = getPaddingTop();//当前行顶部已占高度(注意需要加上paddingTop)
        int rowsMaxHeight = 0;//当前行所有子元素的最大高度（用于换行累加高度）
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
            if (rowsWidth + childWidth > flowWidth) {
                lineIndex++;
                //重置行宽度
                rowsWidth = getPaddingLeft() + getPaddingRight();
                //累加上该行子元素最大高度
                columnHeight += rowsMaxHeight;
                //重置该行最大高度
                rowsMaxHeight = childHeight;
                mHeightLines.put(lineIndex, rowsMaxHeight);
            } else {
                rowsMaxHeight = Math.max(rowsMaxHeight, childHeight);
                mHeightLines.put(lineIndex, rowsMaxHeight);
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
        }

        //返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
        Map<String, Integer> flowMap = new HashMap<>();
        //单行
        if (lineIndex == 0) {
            flowMap.put(ALL_CHILD_WIDTH, rowsWidth);
        } else {
            //多行
            flowMap.put(ALL_CHILD_WIDTH, flowWidth);
        }
        //FlowLayout测量高度 = 当前行顶部已占高度 +当前行内子元素最大高度+FlowLayout的PaddingBottom
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
                if (childWidth < lineDes.rowsMaxHeight) {
                    int diffvalue = 0;
                    if (lineGravity == LINE_GRAVITY_CENTER) {
                        diffvalue = (lineDes.rowsMaxHeight - childWidth) / 2;
                    } else if (lineGravity == LINE_GRAVITY_BOTTOM) {
                        diffvalue = lineDes.rowsMaxHeight - childWidth;
                    }
                    child.layout(rect.left, rect.top + diffvalue, rect.right, rect.bottom + diffvalue);
                } else {
                    child.layout(rect.left, rect.top, rect.right, rect.bottom);
                }
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
        mHeightLines.clear();
        mHeightLines = null;
        mLineDesArray.clear();
        mLineDesArray = null;
        super.onDetachedFromWindow();
    }
}
