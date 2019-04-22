package com.caihan.scframe.widget.flowlayout;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.ArrayList;

/**
 * MultiItemEntity模式
 *
 * @author caihan
 * @date 2019/4/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class BaseMultiItemTagFlowAdapter<T extends TagFlowItemEntity, K extends BaseTagFlowViewHolder>
        extends BaseTagFlowAdapter<T,K> {

    /**
     * MultiItemEntity模式,需要通过{@link #addItemType(int, int)}传入相对应的布局与type
     */
    public BaseMultiItemTagFlowAdapter() {
        super();
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (mLayoutResIds == null) {
            mLayoutResIds = new SparseIntArray();
        }
        mLayoutResIds.put(type, layoutResId);
    }

    /**
     * 对子View绑定TagView的选中状态
     *
     * @param viewResId
     */
    protected void setCheckedStateViewId(int type, @IdRes int... viewResId){
        if (mCheckedStateViewResIds == null) {
            mCheckedStateViewResIds = new SparseArray();
        }
        ArrayList<Integer> viewResIds = mCheckedStateViewResIds.get(type,new ArrayList<Integer>());
        for (int viewId : viewResId) {
            viewResIds.add(viewId);
        }
        mCheckedStateViewResIds.put(type, viewResIds);
    }

    /**
     * 覆写该方法实现MultiItemEntity模式
     *
     * @param data
     * @return
     */
    @Override
    protected int getDefItemViewType(T data) {
        return data.getTagFlowItemType();
    }
}
