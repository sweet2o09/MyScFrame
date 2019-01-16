package com.caihan.scframe.widget.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 拖拽与滑动删除Adapter封装,请使用RecyclerView
 * <p>
 * 拖拽支持{@link LinearLayoutManager#LinearLayoutManager(Context)}
 * 与{@link GridLayoutManager#GridLayoutManager(Context, int)}
 *
 * @author caihan
 * @date 2018/11/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class ItemDragAdapter<T, H extends BaseViewHolder>
        extends BaseItemDraggableAdapter<T, H> {

    private ItemTouchHelper mItemTouchHelper;
    private ItemDragCallback mItemDragCallback;
    private OnItemDragListener mOnItemDragListener;//拖拽监听
    private OnItemSwipeListener mOnItemSwipeListener;//滑动监听,滑动删除

    /**
     * layouts indexed with their types
     */
    private SparseIntArray layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;

    /**
     * 多布局样式实例化方法,DataBean需要实现MultiItemEntity接口
     * 通过{@link #addItemType(int, int)}方法设置布局
     */
    public ItemDragAdapter() {
        super(new ArrayList<T>());
    }

    /**
     * 单一布局样式实例化方法,DataBean千万不要实现MultiItemEntity接口
     *
     * @param layoutResId
     */
    public ItemDragAdapter(@LayoutRes int layoutResId) {
        super(new ArrayList<T>());
        setDefaultViewTypeLayout(layoutResId);
    }

    /**
     * 实例化拖拽监听并绑定
     *
     * @param recyclerView
     */
    public void setListener(RecyclerView recyclerView, OnItemDragListener onItemDragListener) {
        setListener(recyclerView, false);
        setItemDragListener(onItemDragListener);
    }

    /**
     * 实例化拖拽监听并绑定
     *
     * @param recyclerView
     * @param isMoreViewType     true = 要在多种布局之间进行拖拽
     * @param onItemDragListener
     */
    public void setListener(RecyclerView recyclerView, boolean isMoreViewType,
                            OnItemDragListener onItemDragListener) {
        setListener(recyclerView, isMoreViewType);
        setItemDragListener(onItemDragListener);
    }

    /**
     * 实例化拖拽监听并绑定
     *
     * @param recyclerView
     * @param isMoreViewType true = 要在多种布局之间进行拖拽
     */
    private void setListener(RecyclerView recyclerView, boolean isMoreViewType) {
        mItemDragCallback = new ItemDragCallback(this);
        mItemDragCallback.setMoveViewType(isMoreViewType);
        mItemTouchHelper = new ItemTouchHelper(mItemDragCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

//        mItemDragCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
//        mItemDragCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
    }

    private void setItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
        enableDragItem(mItemTouchHelper);
        setOnItemDragListener(mOnItemDragListener);
    }

    /**
     * 设置不允许拖拽的Item
     *
     * @param viewId 布局id
     * @param tag    标签
     */
    public void setNoMoveItem(@IdRes int viewId, String tag) {
        if (mItemDragCallback == null) {
            return;
        }
        mItemDragCallback.setNoMoveItem(viewId, tag);
    }

    /**
     * 设置滑动删除监听
     *
     * @param onItemSwipeListener
     */
    public void setItemSwipeListener(OnItemSwipeListener onItemSwipeListener, int swipeMoveFlags) {
        mOnItemSwipeListener = onItemSwipeListener;
        enableSwipeItem();
        setOnItemSwipeListener(mOnItemSwipeListener);
        mItemDragCallback.setSwipeMoveFlags(swipeMoveFlags);
//        mItemDragCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
    }

    /**
     * 拖拽功能监听
     */
    private void defaultItemDragListener() {
        mOnItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                //开始拖拽,这个时候可以对该Item做一些文字或者颜色的变更
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source,
                                         int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                //结束拖拽,这个时候可以对该Item做一些文字或者颜色的变更
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }
        };
    }

    /**
     * 滑动删除功能监听
     */
    private void defaultItemSwipeListener() {
        mOnItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                //开始滑动,这个时候可以对该Item做一些文字或者颜色的变更
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                //如果pos=-1表示删除成功,如果返回的是onItemSwipeStart的pos表示没删除,只是滑了一下
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                //真的要删除了才会调用,pos = onItemSwipeStart的pos
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                //滑动展现的画布,可以设置颜色跟文案
//                final Paint paint = new Paint();
//                paint.setAntiAlias(true);
//                paint.setTextSize(20);
//                paint.setColor(Color.BLACK);
//                canvas.drawColor(ContextCompat.getColor(mContext, R.color.color_333333));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };
    }


    @Override
    protected int getDefItemViewType(int position) {
        Object item = mData.get(position);
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity) item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected H onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }


    @Override
    public void remove(@IntRange(from = 0L) int position) {
        if (mData == null
                || position < 0
                || position >= mData.size()) return;

        T entity = mData.get(position);
        if (entity instanceof IExpandable) {
            removeAllChild((IExpandable) entity, position);
        }
        removeDataFromParent(entity);
        super.remove(position);
    }

    /**
     * 移除父控件时，若父控件处于展开状态，则先移除其所有的子控件
     *
     * @param parent         父控件实体
     * @param parentPosition 父控件位置
     */
    protected void removeAllChild(IExpandable parent, int parentPosition) {
        if (parent.isExpanded()) {
            List<MultiItemEntity> chidChilds = parent.getSubItems();
            if (chidChilds == null || chidChilds.size() == 0) return;

            int childSize = chidChilds.size();
            for (int i = 0; i < childSize; i++) {
                remove(parentPosition + 1);
            }
        }
    }

    /**
     * 移除子控件时，移除父控件实体类中相关子控件数据，避免关闭后再次展开数据重现
     *
     * @param child 子控件实体
     */
    protected void removeDataFromParent(T child) {
        int position = getParentPosition(child);
        if (position >= 0) {
            IExpandable parent = (IExpandable) mData.get(position);
            parent.getSubItems().remove(child);
        }
    }

    /**
     * Item点击移动
     *
     * @param fromPosition
     * @param toPosition
     */
    public void ItemClickMoved(int fromPosition, int toPosition) {
        int size = getData().size();
        if (fromPosition < 0 || fromPosition >= size) {
            return;
        }
        if (toPosition < 0 || toPosition >= size) {
            return;
        }
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition + getHeaderLayoutCount(), toPosition + getHeaderLayoutCount());
    }
}

