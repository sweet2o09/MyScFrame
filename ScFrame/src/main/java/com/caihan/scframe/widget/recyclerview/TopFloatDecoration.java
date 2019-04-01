package com.caihan.scframe.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * ItemDecoration实现顶部悬浮
 *
 * @author caihan
 * @date 2019/3/31
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class TopFloatDecoration extends RecyclerView.ItemDecoration {

    private ArrayMap<Integer, Integer> mHeightCache = new ArrayMap<>();
    private ArrayMap<Integer, Integer> mTypeCache = new ArrayMap<>();
    private ArrayMap<Integer, RecyclerView.ViewHolder> mHolderCache = new ArrayMap<>();

    private int[] mViewTypes;
    private View mFloatView;//悬浮布局
    private int mFloatPosition = -1;//悬浮布局在RecyclerView中的位置
    private int mFloatBottom;
    private int lastLayoutCount;
    private boolean hasInit = false;
    private int mRecyclerViewPaddingLeft;
    private int mRecyclerViewPaddingRight;
    private int mRecyclerViewPaddingTop;
    private int mRecyclerViewPaddingBottom;
    private int mHeaderLeftMargin;
    private int mHeaderTopMargin;
    private int mHeaderRightMargin;
    private Rect mClipBounds = new Rect();

    /**
     * 动效策略
     */
    public enum Animator {
        ANIMATOR_SCROLL,//滚动动效
        ANIMATOR_GONE,//直接隐藏动效
    }

    /**
     * 动效策略
     */
    private Animator mAnimator = Animator.ANIMATOR_SCROLL;

    /**
     * 传入需要悬浮的type
     *
     * @param viewType
     */
    public TopFloatDecoration(int... viewType) {
        mViewTypes = viewType;
    }

    /**
     * 传入动效类型与悬浮type
     *
     * @param animatorStrategy
     * @param viewType
     */
    public TopFloatDecoration(Animator animatorStrategy, int... viewType) {
        mViewTypes = viewType;
        mAnimator = animatorStrategy;
    }


    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn after the item views are drawn
     * and will thus appear over the views.
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView.
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        View firstView = parent.findChildViewUnder(mClipBounds.left, mRecyclerViewPaddingTop + mHeaderTopMargin);
        if (firstView == null) {
            firstView = layoutManager.getChildAt(0);
        }
        View secondView = parent.findChildViewUnder(mClipBounds.left, mFloatBottom);
        if (firstView == null || secondView == null) {
            return;
        }
        RecyclerView.Adapter adapter = parent.getAdapter();

        int firstViewPosition = parent.getChildAdapterPosition(firstView);
        int secondViewPosition = parent.getChildAdapterPosition(secondView);

        for (int i = secondViewPosition - 1; i > firstViewPosition; i--) {
            int itemViewType = adapter.getItemViewType(i);

            if (isFloatHolder(itemViewType)) {
                //是需要悬浮的布局
                View view = layoutManager.findViewByPosition(i);
                if (view.getLeft() == firstView.getLeft()) {
                    secondView = view;
                    secondViewPosition = i;
                }
                break;
            }
        }
        int firstItemType = adapter.getItemViewType(firstViewPosition);
        int secondItemType = adapter.getItemViewType(secondViewPosition);
        if (!hasInit) {
            touch(parent);
        }
        if (isFloatHolder(firstItemType)) {
            //是需要悬浮的布局
            if (firstViewPosition != mFloatPosition) {
                //新的悬浮布局
                mFloatPosition = firstViewPosition;
                mFloatView = getFloatView(parent, firstView);
            }
            int top = 0;
            if (isFloatHolder(secondItemType)) {
                //是需要悬浮的布局
                if (mFloatView == null || secondView == null) {
                    return;
                }
                top = secondView.getTop() - mFloatView.getHeight() - mRecyclerViewPaddingTop;
            }
            //1.列表firstItem是吸附布局并且secondItem不是吸附布局的时候top = 0
            //2.当secondItem是吸附布局的时候,这边会根据top绘制把原本的吸附布局顶出去
            drawFloatView(mFloatView, c, top);
            return;
        }
        if (isFloatHolder(secondItemType)) {
            //是需要悬浮的布局
            if (mFloatPosition > firstViewPosition) {
                mFloatPosition = findPreFloatPosition(parent);
                mFloatView = getFloatView(parent, null);
            }
            if (mFloatView == null || secondView == null) {
                return;
            }
            int top = secondView.getTop() - mFloatView.getHeight() - mRecyclerViewPaddingTop;
            //当secondItem是吸附布局的时候,这边会根据top绘制与原本的吸附布局做动画交互
            drawFloatView(mFloatView, c, top);
            return;
        }
        if (mFloatView == null || lastLayoutCount != layoutManager.getChildCount()) {
            mFloatPosition = findPreFloatPosition(parent);
            mFloatView = getFloatView(parent, null);
        }
        lastLayoutCount = layoutManager.getChildCount();
        //列表firstItem与secondItem都不是吸附布局的时候,直接绘制原有的吸附布局
        drawFloatView(mFloatView, c, 0);
    }

    /**
     * 绘制悬浮条目
     *
     * @param v
     * @param c
     * @param top
     */
    private void drawFloatView(View v, Canvas c, int top) {
        if (v == null) {
            return;
        }
        if (mAnimator == Animator.ANIMATOR_GONE) {
            top = 0;
        }
        mClipBounds.top = mRecyclerViewPaddingTop + mHeaderTopMargin;
        mClipBounds.bottom = top + mClipBounds.top + v.getHeight();
        c.save();
        c.clipRect(mClipBounds, Region.Op.REPLACE);
        c.translate(mRecyclerViewPaddingLeft + mHeaderLeftMargin,
                top + mRecyclerViewPaddingTop + mHeaderTopMargin);
        v.draw(c);
        c.restore();
    }

    /**
     * 处理悬浮条目触摸事件
     *
     * @param parent
     */
    private void touch(final RecyclerView parent) {
        if (hasInit) {
            return;
        }
        hasInit = true;
        parent.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            private GestureDetectorCompat mGestureDetectorCompat =
                    new GestureDetectorCompat(parent.getContext(), new MyGestureListener());

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return isContains(e);
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                isContains(e);
            }

            private boolean isContains(MotionEvent e) {
                boolean contains = mClipBounds.contains((int) e.getX(), (int) e.getY());
                if (contains) {
                    mGestureDetectorCompat.onTouchEvent(e);
                    mFloatView.onTouchEvent(e);
                }
                Rect drawRect = new Rect();
                parent.getDrawingRect(drawRect);
                drawRect.top = mClipBounds.bottom;
                drawRect.left = mRecyclerViewPaddingLeft;
                drawRect.right -= mRecyclerViewPaddingRight;
                drawRect.bottom -= mRecyclerViewPaddingBottom;
                contains = !drawRect.contains((int) e.getX(), (int) e.getY());
                return contains;
            }

        });
    }

    /**
     * 手势监听
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mClipBounds.contains((int) e.getX(), (int) e.getY())) {
                childClick(mFloatView,
                        e.getX() - mRecyclerViewPaddingLeft,
                        e.getY() - mRecyclerViewPaddingTop);
            }
            return true;
        }

        /**
         * 遍历容器和它的子view，传递点击事件
         */
        private void childClick(View v, float x, float y) {
            Rect rect = new Rect();
            v.getGlobalVisibleRect(rect);
            if (rect.contains((int) x, (int) y)) {
                v.performClick();
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                int childCount = ((ViewGroup) v).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = viewGroup.getChildAt(i);
                    childClick(view, x, y);
                }
            }
        }
    }

    /**
     * 判断条目类型是否需要悬浮
     *
     * @param type
     * @return
     */
    private boolean isFloatHolder(int type) {
        for (int viewType : mViewTypes) {
            if (type == viewType) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找之前的悬浮标题position
     *
     * @param recyclerView
     * @return
     */
    private int findPreFloatPosition(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        View firstVisibleView = recyclerView.getLayoutManager().getChildAt(0);
        int childAdapterPosition = recyclerView.getChildAdapterPosition(firstVisibleView);
        for (int i = childAdapterPosition; i >= 0; i--) {
            if (isFloatHolder(adapter.getItemViewType(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取要悬浮的itemView
     *
     * @param parent
     * @param view
     * @return
     */
    private View getFloatView(RecyclerView parent, View view) {
        if (mFloatPosition < 0) {
            return null;
        }
        if (view != null && view.getHeight() > 0) {
            mHeightCache.put(mFloatPosition, view.getHeight());
            mTypeCache.put(parent.getAdapter().getItemViewType(mFloatPosition), view.getHeight());
        }
        return getHolder(parent).itemView;
    }

    /**
     * 获取之前要悬浮的holder
     *
     * @param recyclerView
     * @return
     */
    private RecyclerView.ViewHolder getHolder(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int viewType = adapter.getItemViewType(mFloatPosition);
        RecyclerView.ViewHolder holder = mHolderCache.get(viewType);
        if (holder == null) {
            holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(mFloatPosition));
            mHolderCache.put(viewType, holder);
        }
        adapter.bindViewHolder(holder, mFloatPosition);
        layoutView(holder.itemView, recyclerView);
        return holder;
    }

    /**
     * 测量悬浮布局
     *
     * @param v
     * @param parent
     */
    private void layoutView(View v, RecyclerView parent) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp == null) {
            // 标签默认宽度占满parent
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(lp);
        }

        // 对高度进行处理
        int heightMode = View.MeasureSpec.EXACTLY;
        Integer height = mHeightCache.get(mFloatPosition);
        if (height == null) {
            height = mTypeCache.get(parent.getAdapter().getItemViewType(mFloatPosition));
        }
        int heightSize = height == null ? mClipBounds.height() : height;

        mRecyclerViewPaddingLeft = parent.getPaddingLeft();
        mRecyclerViewPaddingRight = parent.getPaddingRight();
        mRecyclerViewPaddingTop = parent.getPaddingTop();
        mRecyclerViewPaddingBottom = parent.getPaddingBottom();

        if (lp instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            mHeaderLeftMargin = mlp.leftMargin;
            mHeaderTopMargin = mlp.topMargin;
            mHeaderRightMargin = mlp.rightMargin;
        }

        // 最大高度为RecyclerView的高度减去padding
        final int maxHeight = parent.getHeight() - mRecyclerViewPaddingTop - mRecyclerViewPaddingBottom;
        // 不能超过maxHeight
        heightSize = Math.min(heightSize, maxHeight);

        // 因为标签默认宽度占满parent，所以宽度强制为RecyclerView的宽度减去padding
        int widthSize = parent.getWidth() - mRecyclerViewPaddingLeft -
                mRecyclerViewPaddingRight - mHeaderLeftMargin - mHeaderRightMargin;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            widthSize /= spanCount;
        }
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        // 强制测量
        v.measure(widthSpec, heightSpec);

        int left = mRecyclerViewPaddingLeft + mHeaderLeftMargin;
        int right = v.getMeasuredWidth() + left;
        int top = mRecyclerViewPaddingTop + mHeaderTopMargin;
        int bottom = v.getMeasuredHeight() + top;

        // 位置强制布局在顶部
        v.layout(left, top, right, bottom);

        mClipBounds.top = top;
        mClipBounds.bottom = bottom;
        mClipBounds.left = left;
        mClipBounds.right = right;
        mFloatBottom = bottom;
    }
}
