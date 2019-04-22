package com.caihan.scframe.widget.flowlayout;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caihan
 * @date 2019/4/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class BaseTagFlowAdapter<T, K extends BaseTagFlowViewHolder> {

    public static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    protected Context mContext;
    protected SparseIntArray mLayoutResIds;
    protected SparseArray<ArrayList<Integer>> mCheckedStateViewResIds;
    private HashMap<Integer, TagView> mCheckedPosList = new HashMap<>();
    private LayoutInflater mLayoutInflater;
    protected List<T> mData;
    private ScFlowLayout mFlowLayout;
    private int leftMargin;
    private int topMargin;
    private int rightMargin;
    private int bottomMargin;
    private int mSelectedMax = -1;//-1为不限制数量

    /**
     * MultiItemEntity模式
     */
    public BaseTagFlowAdapter() {
        leftMargin = SizeUtils.dp2px(5);
        topMargin = SizeUtils.dp2px(5);
        rightMargin = SizeUtils.dp2px(5);
        bottomMargin = SizeUtils.dp2px(5);
    }

    /**
     * 普通模式
     *
     * @param layoutResId
     */
    public BaseTagFlowAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    private BaseTagFlowAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        setDefaultViewTypeLayout(layoutResId);
        leftMargin = SizeUtils.dp2px(5);
        topMargin = SizeUtils.dp2px(5);
        rightMargin = SizeUtils.dp2px(5);
        bottomMargin = SizeUtils.dp2px(5);
    }

    private void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        if (mLayoutResIds == null) {
            mLayoutResIds = new SparseIntArray();
        }
        mLayoutResIds.put(DEFAULT_VIEW_TYPE, layoutResId);
    }

    /**
     * 对子View绑定TagView的选中状态
     *
     * @param viewResId
     */
    private void setCheckedStateViewId(@IdRes int... viewResId) {
        if (mCheckedStateViewResIds == null) {
            mCheckedStateViewResIds = new SparseArray();
        }
        ArrayList<Integer> viewResIds = mCheckedStateViewResIds.get(DEFAULT_VIEW_TYPE, new ArrayList<Integer>());
        for (int viewId : viewResId) {
            viewResIds.add(viewId);
        }
        mCheckedStateViewResIds.put(DEFAULT_VIEW_TYPE, viewResIds);
    }

    private int getLayoutId(int viewType) {
        return mLayoutResIds.get(viewType, TYPE_NOT_FOUND);
    }

    protected ScFlowLayout getFlowLayout() {
        return mFlowLayout;
    }

    private void setFlowLayout(ScFlowLayout flowLayout) {
        mFlowLayout = flowLayout;
    }

    private void checkNotNull() {
        if (getFlowLayout() == null) {
            throw new RuntimeException("please bind ScFlowLayout first!");
        }
    }

    public void bindToFlowLayout(ScFlowLayout flowLayout) {
        if (getFlowLayout() != null) {
            throw new RuntimeException("Don't bind twice");
        }
        setFlowLayout(flowLayout);
        getFlowLayout().setAdapter(this);
        this.mContext = mFlowLayout.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        mSelectedMax = mFlowLayout.getSelectedMax();
    }

    public int getSelectedMax() {
        return mSelectedMax;
    }

    public void setSelectedMax(int selectedMax) {
        mSelectedMax = selectedMax;
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    /**
     * 重绘
     */
    public void requestLayout() {
        if (mFlowLayout != null) {
            mFlowLayout.requestLayout();
            mFlowLayout.invalidate();
        }
    }

    public void notifyDataSetChanged() {
        if (mFlowLayout != null) {
            mFlowLayout.post(new Runnable() {
                @Override
                public void run() {
                    addNewView();
                }
            });
        }
    }

    private void addNewView() {
        mFlowLayout.removeAllViews();
        mCheckedPosList.clear();
        TagView tagViewContainer = null;
        K baseViewHolder = null;
        T data = null;
        int viewType = DEFAULT_VIEW_TYPE;
        for (int i = 0; i < getCount(); i++) {
            data = getItem(i);
            viewType = getDefItemViewType(data);
            baseViewHolder = onCreateViewHolder(mFlowLayout, viewType, i);
            tagViewContainer = new TagView(mContext);
            //关键代码,使得内部View可以使用TagView的状态
            if (mCheckedStateViewResIds != null) {
                ArrayList<Integer> viewResId = mCheckedStateViewResIds.get(viewType, new ArrayList<Integer>());
                for (Integer stateViewId : viewResId) {
                    View stateView = baseViewHolder.getView(stateViewId.intValue());
                    if (stateView != null) {
                        stateView.setDuplicateParentStateEnabled(true);
                    }
                }
            }
            baseViewHolder.itemView.setDuplicateParentStateEnabled(true);
            if (baseViewHolder.itemView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(baseViewHolder.itemView.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                tagViewContainer.setLayoutParams(lp);
            }
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            baseViewHolder.itemView.setLayoutParams(lp);
            tagViewContainer.addView(baseViewHolder.itemView);

            //处理选中与非选中逻辑
            if (setDefSelected(data, i)) {
                if (mSelectedMax == 1 && mCheckedPosList.size() > 0) {
                    int oldSelected = 0;
                    TagView oldTagView;
                    for (Map.Entry<Integer, TagView> entry : mCheckedPosList.entrySet()) {
                        oldSelected = entry.getKey();
                        oldTagView = entry.getValue();
                        setChildUnChecked(oldSelected, oldTagView);
                    }
                    mCheckedPosList.clear();
                }
                mCheckedPosList.put(i, tagViewContainer);
                setChildChecked(i, tagViewContainer);
            }

            mFlowLayout.addView(tagViewContainer);
            convert(baseViewHolder, data);
            bindViewClickListener(tagViewContainer, baseViewHolder);
        }
    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    protected int getDefItemViewType(T data) {
        return DEFAULT_VIEW_TYPE;
    }

    private K onCreateViewHolder(ViewGroup parent, int viewType, int position) {
        K baseViewHolder = null;
        baseViewHolder = onCreateDefViewHolder(parent, viewType);
        baseViewHolder.setAdapter(this);
        baseViewHolder.setPosition(position);
        baseViewHolder.setViewType(viewType);
        return baseViewHolder;
    }

    private K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        return createBaseViewHolder(parent, layoutId);
    }

    private K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return (K) new BaseTagFlowViewHolder(getItemView(layoutResId, parent));
    }


    private View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    private void bindViewClickListener(final TagView tagView, final BaseTagFlowViewHolder baseViewHolder) {
        if (baseViewHolder == null || tagView == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            tagView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelect(tagView, baseViewHolder.getPosition());
                    setOnItemClick(v, baseViewHolder.getPosition());
                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            tagView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return setOnItemLongClick(v, baseViewHolder.getPosition());
                }
            });
        }
    }

    private void doSelect(TagView tagView, int position) {
        if (!tagView.isChecked()) {
            if (mSelectedMax == 1) {
                //处理max_select=1的情况
                int oldSelected = 0;
                TagView oldTagView;
                for (Map.Entry<Integer, TagView> entry : mCheckedPosList.entrySet()) {
                    oldSelected = entry.getKey();
                    oldTagView = entry.getValue();
                    setChildUnChecked(oldSelected, oldTagView);
                }
                setChildChecked(position, tagView);
                mCheckedPosList.clear();
                mCheckedPosList.put(position, tagView);
            } else {
                if (mSelectedMax > 1 && mCheckedPosList.size() >= mSelectedMax) {
                    return;
                }
                //多选
                setChildChecked(position, tagView);
                mCheckedPosList.put(position, tagView);
            }
        } else {
            setChildUnChecked(position, tagView);
            mCheckedPosList.remove(position);
        }
    }

    private void setChildChecked(int position, TagView view) {
        view.setChecked(true);
        onSelected(view, position);
    }

    private void setChildUnChecked(int position, TagView view) {
        view.setChecked(false);
        unSelected(view, position);
    }

    /**
     * MultiItemEntity模式,通过{@link BaseTagFlowViewHolder#getViewType()}获取布局的type
     *
     * @param helper
     * @param item
     */
    protected abstract void convert(K helper, T item);

    protected abstract void onSelected(View view, int position);

    protected abstract void unSelected(View view, int position);

    /**
     * 默认选中的
     *
     * @param position
     * @param t
     * @return
     */
    protected boolean setDefSelected(T t, int position) {
        return false;
    }

    public void setOnItemClick(View v, int position) {
        getOnItemClickListener().onItemClick(BaseTagFlowAdapter.this, v, position);
    }

    public boolean setOnItemLongClick(View v, int position) {
        return getOnItemLongClickListener().onItemLongClick(BaseTagFlowAdapter.this, v, position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseTagFlowAdapter adapter, View view, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(BaseTagFlowAdapter adapter, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseTagFlowAdapter adapter, View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseTagFlowAdapter adapter, View view, int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        mOnItemChildLongClickListener = listener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }
}
