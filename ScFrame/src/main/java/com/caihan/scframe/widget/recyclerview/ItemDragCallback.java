package com.caihan.scframe.widget.recyclerview;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

/**
 * 拖拽监听
 * <p>
 * 默认不支持多个不同的 ViewType 之间进行拖拽,
 * 如果需要在不同ViewType之间拖拽,请设置
 *
 * @author caihan
 * @date 2018/11/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ItemDragCallback extends ItemDragAndSwipeCallback {

    private boolean mMoveViewType = false;//是否允许不同类型type之间切换位置

    private @IdRes
    int mNoMoveViewId;//不允许移动的布局id
    private String mNoMoveViewTag;//不允许移动的布局tag

    public ItemDragCallback(BaseItemDraggableAdapter adapter) {
        super(adapter);
    }

    public void setMoveViewType(boolean moveViewType) {
        mMoveViewType = moveViewType;
    }

    public void setNoMoveItem(@IdRes int viewId, String tag) {
        mNoMoveViewId = viewId;
        mNoMoveViewTag = tag;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                          RecyclerView.ViewHolder target) {
        boolean isNoMove = isNoMove(target);
        if (isNoMove) {
            return false;
        }
        return mMoveViewType ? true : super.onMove(recyclerView, source, target);
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        boolean isNoMove = isNoMove(viewHolder);
        if (isNoMove) {
            return makeMovementFlags(0, 0);
        }
        return super.getMovementFlags(recyclerView, viewHolder);
    }

    private boolean isNoMove(RecyclerView.ViewHolder viewHolder) {
        if (mNoMoveViewId != 0 && !StringUtils.isEmpty(mNoMoveViewTag)) {
            //当设置了不允许移动的布局参数后,会调用这里的逻辑
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            View view = holder.itemView.findViewById(mNoMoveViewId);
            if (view != null) {
                String tag = (String) view.getTag(mNoMoveViewId);
                boolean isNoMove = mNoMoveViewTag.equals(tag);
                if (isNoMove) {
                    return true;
                }
            }
        }
        return false;
    }
}
