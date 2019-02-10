package app.caihan.myscframe.ui.imageloader;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.caihan.scframe.utils.imageloader.ScImageLoader;
import com.caihan.scframe.utils.log.ScLog;
import com.caihan.scframe.widget.imageview.SquareImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;

public class ImageLoaderListActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ImageLoaderListAdapter mImageLoaderListAdapter;
    private int mOutPutSize = 0;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_image_loader_list;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("图像处理");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOutPutSize = SizeUtils.dp2px(120);
        ScLog.debug("ImageLoaderListActivity mOutPutSize = " + mOutPutSize);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        int decoration = SizeUtils.dp2px(10);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int p = parent.getChildAdapterPosition(view);
                int count = parent.getAdapter().getItemCount();
                if (p != 0) {
                    outRect.top = decoration;
                }
                if (p == count - 1) {
                    outRect.bottom = decoration;
                }
            }
        });
        mImageLoaderListAdapter = new ImageLoaderListAdapter();
        mImageLoaderListAdapter.bindToRecyclerView(mRecyclerView);
        mImageLoaderListAdapter.setNewData(getData());
    }

    private class ImageLoaderListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ImageLoaderListAdapter() {
            super(R.layout.item_image_loader_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            SquareImageView imageView = helper.getView(R.id.square_iv);
//            ImageView imageView = helper.getView(R.id.square_iv);

            ScImageLoader.getInstance().display(
                    item,
                    R.drawable.image_nine_photo_def,
                    R.drawable.image_nine_photo_def,
                    mOutPutSize,
                    mOutPutSize,
                    imageView);

//            Glide.with(imageView.getContext())
//                    .load(item)
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//只缓存最终的加载图
//                    .skipMemoryCache(true)//跳过内存缓存
//                    .dontAnimate()//关闭动画
//                    .placeholder(R.drawable.image_nine_photo_def)//图片加载出来前，显示的图片
//                    .error(R.drawable.image_nine_photo_def)//图片加载失败后，显示的图片
//                    .override(mOutPutSize, mOutPutSize)
//                    .into(imageView);
        }
    }

    private List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        int size = ImageLoaderActivity.IMAGE_URL.length;
        for (int i = 0; i < size; i++) {
            list.add(ImageLoaderActivity.IMAGE_URL[i]);
        }
        for (int i = size - 1; i > 0; i--) {
            list.add(ImageLoaderActivity.IMAGE_URL[i]);
        }
        for (int i = 0; i < size; i++) {
            list.add(ImageLoaderActivity.IMAGE_URL[i]);
        }
        return list;
    }
}
