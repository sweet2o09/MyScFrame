package com.caihan.myscframe.ui.multistoreylist;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.request.DeliveryTypeItemBean;
import com.caihan.scframe.utils.ScOutdatedUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;


/**
 * 配送类型adapter
 *
 * @author caihan
 * @date 2018/5/19
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DeliveryTypeAdapter extends BaseQuickAdapter<DeliveryTypeItemBean, DeliveryTypeAdapter.DeliveryItemHolder> {

    private Drawable mDrawable = null;


    public DeliveryTypeAdapter() {
        super(R.layout.item_shop_cart_delivery_type, new ArrayList<DeliveryTypeItemBean>());
        mDrawable = ScOutdatedUtils.getDrawable(R.drawable.ic_gouxuan);
    }

    @Override
    protected void convert(DeliveryItemHolder helper, DeliveryTypeItemBean item) {
        helper.mDeliveryTypeTextTv.setText(item.getDeliveryTypeName());
        toggleCheck(helper,item.isCheck());

    }

    public void toggleCheck(DeliveryItemHolder helper,boolean isCheck) {
        if (isCheck) {
            helper.mDeliveryTypeTextTv.setCompoundDrawables(mDrawable,null,null,null);
            helper.mDeliveryTypeTextTv.setTextColor(
                    mContext.getResources().getColor(R.color.color_FF5252));
            helper.mDeliveryTypeLayout.setBackground(
                    mContext.getResources().getDrawable(R.drawable.bg_solid_ffffff_border_ff5252_corners_3));
        } else {
            helper.mDeliveryTypeTextTv.setCompoundDrawables(null,null,null,null);
            helper.mDeliveryTypeTextTv.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
            helper.mDeliveryTypeLayout.setBackground(
                    mContext.getResources().getDrawable(R.drawable.bg_solid_ffffff_border_dddddd_corners_3));
        }
    }

    public class DeliveryItemHolder extends BaseViewHolder {

        private final RelativeLayout mDeliveryTypeLayout;//佣金
        private final TextView mDeliveryTypeTextTv;//分享按钮


        public DeliveryItemHolder(View convertView) {
            super(convertView);
            mDeliveryTypeLayout = getView(R.id.delivery_type_layout);
            mDeliveryTypeTextTv = getView(R.id.delivery_type_text_tv);
        }
    }

}
