package com.caihan.myscframe.ui.immersion;

import com.caihan.myscframe.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author caihan
 * @date 2018/5/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionAdapter extends BaseQuickAdapter<ImmersionItem,BaseViewHolder> {


    public ImmersionAdapter() {
        super(R.layout.immersion_item_view);
    }

    @Override
    public void setNewData(List<ImmersionItem> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImmersionItem item) {
        helper.setText(R.id.text, item.getTitle());
    }
}
