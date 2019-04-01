package app.caihan.myscframe.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import app.caihan.myscframe.config.ConstValue;
import butterknife.BindView;

public class MainActivity extends BaseScActivity {

    @BindView(R.id.content_layout)
    LinearLayout mContentLayout;
    @BindView(R.id.banner_iv)
    ImageView mImageView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ArrayList<HomeItem> mDataList;
    private HomeAdapter mAdapter;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionTransparentStatusBar();
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataList = new ArrayList<>();
        for (int i = 0; i < ConstValue.TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(ConstValue.TITLE[i]);
            item.setActivity(ConstValue.ACTIVITY[i]);
            mDataList.add(item);
        }
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new HomeAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setNewData(mDataList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ConstValue.ACTIVITY[position]);
                startActivity(intent);
            }
        });
    }
}
