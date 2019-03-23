package app.caihan.myscframe.ui.http;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import app.caihan.myscframe.config.ConstValue;
import app.caihan.myscframe.ui.home.HomeAdapter;
import app.caihan.myscframe.ui.home.HomeItem;
import butterknife.BindView;


/**
 * 网络请求场景
 *
 * @author caihan
 * @date 2019/3/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RequestJobActivity extends BaseScActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ArrayList<HomeItem> mDataList;
    private HomeAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, false);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_request_job;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("网络请求场景");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataList = new ArrayList<>();
        for (int i = 0; i < ConstValue.REQUEST_JOB_TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(ConstValue.REQUEST_JOB_TITLE[i]);
            item.setActivity(ConstValue.REQUEST_JOB_ACTIVITY[i]);
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
                Intent intent = new Intent(mContext, ConstValue.REQUEST_JOB_ACTIVITY[position]);
                startActivity(intent);
            }
        });
    }
}
