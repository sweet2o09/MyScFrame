package com.caihan.scframe.widget.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者：caihan
 * 创建时间：2017/11/21
 * 邮箱：93234929@qq.com
 * 说明：
 * BaseRecyclerViewAdapterHelper
 */
public abstract class MyBRVAH<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private static final String TAG = "MyBRVAH";

    public MyBRVAH(@LayoutRes int layoutResId) {
        super(layoutResId, new ArrayList<T>());
    }

    public MyBRVAH(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public MyBRVAH(@Nullable List<T> data) {
        super(data);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        super.setNewData(data);
    }

    @Override
    public void addData(@NonNull T data) {
        super.addData(data);
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        super.addData(newData);
    }
}
