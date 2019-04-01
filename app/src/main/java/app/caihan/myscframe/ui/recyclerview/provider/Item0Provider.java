package app.caihan.myscframe.ui.recyclerview.provider;

import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import app.caihan.myscframe.R;
import app.caihan.myscframe.ui.recyclerview.NormalMultipleEntity;

/**
 * @author caihan
 * @date 2019/3/31
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class Item0Provider extends BaseItemProvider<NormalMultipleEntity, BaseViewHolder> {

    @Override
    public int viewType() {
        return NormalMultipleEntity.TYPE_0;
    }

    @Override
    public int layout() {
        return R.layout.item_top_adsorption_0;
    }

    @Override
    public void convert(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        helper.setText(R.id.year_tv, data.content);
    }

    @Override
    public void onClick(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        Toast.makeText(mContext, "Item0Provider " + data.content, Toast.LENGTH_SHORT).show();
    }
}
