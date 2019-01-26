package app.caihan.myscframe.ui.ninephoto;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.caihan.scframe.widget.photo.NinePhotoLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import app.caihan.myscframe.R;

/**
 * @author caihan
 * @date 2019/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoShowAdapter extends BaseQuickAdapter<NinePhotoShowItem, BaseViewHolder> {

    private NinePhotoLayout.onItemClickListener mListener;
    private int mHeadSize = 0;

    public NinePhotoShowAdapter(NinePhotoLayout.onItemClickListener listener) {
        super(R.layout.item_nine_photo_show_layout);
        mListener = listener;
        mHeadSize = SizeUtils.dp2px(40);
    }

    @Override
    public void setNewData(@Nullable List<NinePhotoShowItem> data) {
        super.setNewData(data);
    }

    @Override
    public void addData(@NonNull NinePhotoShowItem data) {
        super.addData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NinePhotoShowItem item) {
        Glide.with(mContext)
                .load(item.getHeadImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fallback(R.mipmap.ic_launcher_round)
                .override(mHeadSize, mHeadSize)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.head_iv));

        helper.setText(R.id.title_tv, item.getTitle());
        if (StringUtils.isEmpty(item.getContent())) {
            helper.setGone(R.id.content_tv, false);
        } else {
            helper.setGone(R.id.content_tv, true)
                    .setText(R.id.content_tv, item.getContent());
        }

        NinePhotoLayout ninePhotoLayout = helper.getView(R.id.nine_photo_layout);
        ninePhotoLayout.setListener(mListener);
        ninePhotoLayout.setNewData(item.getImageList(), item.getShowAsLargeWhenOnlyOne(), item.getShowTwoItemSpanCount());
    }
}
