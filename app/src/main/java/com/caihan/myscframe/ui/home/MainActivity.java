package com.caihan.myscframe.ui.home;import android.content.Intent;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.View;import com.caihan.myscframe.R;import com.caihan.myscframe.base.BaseScActivity;import com.chad.library.adapter.base.BaseQuickAdapter;import java.util.ArrayList;import butterknife.BindView;import static com.caihan.myscframe.config.ConstValue.ACTIVITY;import static com.caihan.myscframe.config.ConstValue.TITLE;/** * 首页 * * @author caihan * @date 2018/4/22 * @e-mail 93234929@qq.com * 维护者 */public class MainActivity extends BaseScActivity {    @BindView(R.id.recycler_view)    RecyclerView mRecyclerView;    private ArrayList<HomeItem> mDataList;    private HomeAdapter mAdapter;    @Override    public int setLayoutResId() {        return R.layout.activity_main;    }    @Override    public boolean openImmersion() {        return false;    }    @Override    protected void onCreate() {        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));        mDataList = new ArrayList<>();        for (int i = 0; i < TITLE.length; i++) {            HomeItem item = new HomeItem();            item.setTitle(TITLE[i]);            item.setActivity(ACTIVITY[i]);            mDataList.add(item);        }        initAdapter();    }    private void initAdapter() {        mAdapter = new HomeAdapter();        mAdapter.bindToRecyclerView(mRecyclerView);        mAdapter.setNewData(mDataList);        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {            @Override            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {                Intent intent = new Intent(mContext, ACTIVITY[position]);                startActivity(intent);            }        });    }}