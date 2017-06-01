package com.caihan.scframe.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by caihan on 2017/5/2.
 * ViewHolder 简化创建工具类
 */
public class ScViewHolderUtil {

    private ScViewHolderUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View convertView, int id) {

        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
