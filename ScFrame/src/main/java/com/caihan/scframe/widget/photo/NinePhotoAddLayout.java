package com.caihan.scframe.widget.photo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.caihan.scframe.R;
import com.caihan.scframe.widget.imageview.SquareImageView;
import com.caihan.scframe.widget.recyclerview.GridDividerItemDecoration;
import com.caihan.scframe.widget.recyclerview.ItemDragAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemDragListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格添加图片自定义布局
 *
 * @author caihan
 * @date 2019/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoAddLayout extends RecyclerView {

    private Context mContext;
    private GridLayoutManager mGridLayoutManager;
    private NinePhotoAddAdapter mAdapter;
    private onItemClickListener mListener;
    private NoneNinePhotoItem mNoneItem;
    private int mMaxSize = 9;//最大显示个数
    private int mItemSpanCount = 3;//单行显示个数
    private boolean mSortable = true;//是否可以拖动排序
    private boolean mPlusEnable = true;//是否显示加号
    private boolean mEditable = true;//是否可编辑
    private boolean mDefAdapter = true;//是否使用默认Adapter
    @DrawableRes
    private int mPlusDrawableResId = R.drawable.image_nine_photo_add;//添加
    @DrawableRes
    private int mDelDrawableResId = R.drawable.image_nine_photo_delete;//删除
    @DrawableRes
    private int mDefDrawableResId = R.drawable.image_nine_photo_def;//默认占位图
    @ColorInt
    private int mItemDecorationColor = Color.WHITE;//默认间隙颜色
    private int mItemWhiteSpacing = 10;//Item间隔默认10dp

    public interface onItemClickListener {

        /**
         * 点击了已选的图片
         *
         * @param view
         * @param ninePhotoItem
         * @param position
         */
        void onClickImage(View view, NinePhotoItem ninePhotoItem, int position);

        /**
         * 点击了添加图片
         *
         * @param view
         * @param ninePhotoItem
         * @param position
         */
        void onClickAddImage(View view, NinePhotoItem ninePhotoItem, int position);
    }

    public NinePhotoAddLayout(Context context) {
        this(context, null);
    }

    public NinePhotoAddLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoAddLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initDefaultAttrs();
        initCustomAttrs(attrs);
        initView();
    }

    /**
     * 加载默认参数
     */
    private void initDefaultAttrs() {
        mMaxSize = 9;
        mItemSpanCount = 3;
        mSortable = true;
        mPlusEnable = true;
        mEditable = true;
        mDefAdapter = true;
        mPlusDrawableResId = R.drawable.image_nine_photo_add;
        mDelDrawableResId = R.drawable.image_nine_photo_delete;
        mDefDrawableResId = R.drawable.image_nine_photo_def;
        mItemDecorationColor = Color.WHITE;
        mItemWhiteSpacing = SizeUtils.dp2px(10);
    }

    /**
     * 加载自定义参数
     *
     * @param attrs
     */
    private void initCustomAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NinePhotoAddLayout);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_maxSize) {
            mMaxSize = typedArray.getInteger(attr, mMaxSize);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_itemSpanCount) {
            mItemSpanCount = typedArray.getInteger(attr, mItemSpanCount);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_sortable) {
            mSortable = typedArray.getBoolean(attr, mSortable);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_plusEnable) {
            mPlusEnable = typedArray.getBoolean(attr, mPlusEnable);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_editable) {
            mEditable = typedArray.getBoolean(attr, mEditable);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_useDefaultAdapter) {
            mDefAdapter = typedArray.getBoolean(attr, mDefAdapter);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_plusDrawable) {
            mPlusDrawableResId = typedArray.getResourceId(attr, mPlusDrawableResId);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_deleteDrawable) {
            mDelDrawableResId = typedArray.getResourceId(attr, mDelDrawableResId);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_defDrawable) {
            mDefDrawableResId = typedArray.getResourceId(attr, mDefDrawableResId);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_itemDecorationColor) {
            mItemDecorationColor = typedArray.getColor(attr, mItemDecorationColor);
        } else if (attr == R.styleable.NinePhotoAddLayout_ninePhoto_itemWhiteSpacing) {
            mItemWhiteSpacing = typedArray.getDimensionPixelSize(attr, mItemWhiteSpacing);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        mNoneItem = new NoneNinePhotoItem();
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        setLayoutManager(mGridLayoutManager);
        addItemDecoration(new GridDividerItemDecoration(mContext,
                mItemWhiteSpacing, mItemDecorationColor));
        setNestedScrollingEnabled(false);
        if (mDefAdapter){
            setDefAddAdapter();
        }
    }

    /**
     * 回调监听,默认Adapter中使用
     *
     * @param listener
     */
    public void setListener(onItemClickListener listener) {
        testDefAdapter();
        mListener = listener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * 判断是否使用默认Adapter
     */
    private void testDefAdapter() {
        if (!mDefAdapter) {
            throw new ExceptionInInitializerError("该方法只在默认Adapter中使用");
        }
    }

    /**
     * 使用默认Adapter
     */
    private void setDefAddAdapter() {
        mAdapter = new NinePhotoAddAdapter();
        mAdapter.bindToRecyclerView(this);
        setNewData(new ArrayList<NinePhotoItem>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NinePhotoItem gridItem = mAdapter.getItem(position);
                int i = view.getId();
                if (i == R.id.nine_photo_del_iv) {
                    final int size = mAdapter.getData().size();
                    mAdapter.remove(position);
                    if (size == mMaxSize && mPlusEnable) {
                        mAdapter.addData(mNoneItem);
                    }
                    mAdapter.notifyDataSetChanged();
                } else if (i == R.id.nine_photo_image_iv) {
                    if (mListener != null) {
                        if (gridItem.isNoneNinePhotoItem()) {
                            //添加图片
                            mListener.onClickAddImage(view, gridItem, position);
                        } else {
                            //更换图片
                            mListener.onClickImage(view, gridItem, position);
                        }
                    }
                }
            }
        });
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {

            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                NinePhotoItem gridItem = mAdapter.getItem(position);
                int i = view.getId();
                if (i == R.id.nine_photo_image_iv && mSortable && !gridItem.isNoneNinePhotoItem()) {
                    //长时间点击
                    return true;
                }
                return false;
            }
        });

        mAdapter.setListener(this, new OnItemDragListener() {

            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.itemView.setScaleX(1.2f);
                holder.itemView.setScaleY(1.2f);
                ImageView imageView = holder.getView(R.id.nine_photo_image_iv);
                imageView.setColorFilter(mContext.getResources()
                        .getColor(R.color.color_black_70));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.itemView.setScaleX(1.0f);
                holder.itemView.setScaleY(1.0f);
                ImageView imageView = holder.getView(R.id.nine_photo_image_iv);
                imageView.setColorFilter(null);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setNoMoveItem(R.id.nine_photo_image_iv, "none");
    }

    /**
     * 保存数据,默认Adapter中使用
     * @param ninePhotoItems
     */
    public void setNewData(ArrayList<NinePhotoItem> ninePhotoItems) {
        testDefAdapter();
        if (mPlusEnable && ninePhotoItems.size() < mMaxSize) {
            ninePhotoItems.add(mNoneItem);
        }
        mAdapter.setNewData(ninePhotoItems);
    }

    /**
     * 只返回有效Item数据,默认Adapter中使用
     *
     * @return
     */
    public ArrayList<NinePhotoItem> getNormalData() {
        testDefAdapter();
        List<NinePhotoItem> data = mAdapter.getData();
        ArrayList<NinePhotoItem> gridItems = new ArrayList<>();
        for (NinePhotoItem gridItem : data) {
            if (!gridItem.isNoneNinePhotoItem()) {
                gridItems.add(gridItem);
            }
        }
        return gridItems;
    }

    private class NinePhotoAddAdapter extends ItemDragAdapter<NinePhotoItem, BaseViewHolder> {

        public NinePhotoAddAdapter() {
            super(R.layout.item_nine_photo_add_layout);
        }

        @Override
        public void setNewData(List<NinePhotoItem> data) {
            super.setNewData(data);
        }

        @Override
        public void addData(NinePhotoItem data) {
            super.addData(data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NinePhotoItem item) {
//            int position = helper.getAdapterPosition();
            helper.setVisible(R.id.nine_photo_del_iv, !item.isNoneNinePhotoItem())
                    .addOnClickListener(R.id.nine_photo_del_iv)
                    .addOnClickListener(R.id.nine_photo_image_iv)
                    .addOnLongClickListener(R.id.nine_photo_image_iv);
            SquareImageView squareImageView = helper.getView(R.id.nine_photo_image_iv);
            if (item.isNoneNinePhotoItem()) {
                squareImageView.setImageResource(mPlusDrawableResId);
                squareImageView.setTag(R.id.nine_photo_image_iv, "none");
            } else {
                Glide.with(mContext)
                        .load(item.getNinePhotoImageUrl())
                        .placeholder(mDefDrawableResId)
                        .fallback(mDefDrawableResId)
                        .dontAnimate()
                        .into(squareImageView);
                squareImageView.setTag(R.id.nine_photo_image_iv, "Normal");
            }
        }
    }
}
