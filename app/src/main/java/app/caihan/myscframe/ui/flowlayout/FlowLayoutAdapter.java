package app.caihan.myscframe.ui.flowlayout;

import android.support.annotation.Nullable;
import android.view.View;

import com.caihan.scframe.utils.log.ScLog;
import com.caihan.scframe.widget.flowlayout.BaseMultiItemTagFlowAdapter;
import com.caihan.scframe.widget.flowlayout.BaseTagFlowViewHolder;

import java.util.List;

import app.caihan.myscframe.R;

/**
 * @author caihan
 * @date 2019/4/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class FlowLayoutAdapter extends BaseMultiItemTagFlowAdapter<FlowLayoutItem, BaseTagFlowViewHolder> {


    public FlowLayoutAdapter() {
        super();
        addItemType(1, R.layout.item_flow_layout3);
        addItemType(2, R.layout.item_flow_layout4);
    }

    @Override
    public void setNewData(@Nullable List<FlowLayoutItem> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseTagFlowViewHolder helper, FlowLayoutItem item) {
        int type = helper.getViewType();
        switch (type) {
            case 1:
                helper.setText(R.id.flow_item_tv, item.getMsg())
                        .addOnClickListener(R.id.flow_item_tv);
                break;
            case 2:
                helper.setText(R.id.flow_item_tv, item.getMsg())
                        .addOnClickListener(R.id.flow_item_tv);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSelected(View view, int position) {
        ScLog.debug("onSelected position = " + position);
    }

    @Override
    protected void unSelected(View view, int position) {
        ScLog.debug("unSelected position = " + position);
    }
}
