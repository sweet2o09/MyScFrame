package com.caihan.scframe.widget.flowlayout;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author caihan
 * @date 2019/4/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class TagFlowLayout extends ScFlowLayout {

    private BaseTagFlowAdapter mAdapter;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseTagFlowAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseTagFlowAdapter adapter) {
        mAdapter = adapter;
    }
}
