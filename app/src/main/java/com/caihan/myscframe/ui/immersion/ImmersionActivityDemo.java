package com.caihan.myscframe.ui.immersion;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.caihan.myscframe.config.ConstValue.IMMERSION_ACTIVITY;
import static com.caihan.myscframe.config.ConstValue.IMMERSION_TITLE;

/**
 * 沉浸式效果Demo
 *
 * @author caihan
 * @date 2018/5/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionActivityDemo extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ArrayList<ImmersionItem> mDataList;
    ImmersionAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_immersion_demo;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("沉浸式效果");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataList = new ArrayList<>();
        for (int i = 0; i < IMMERSION_TITLE.length; i++) {
            ImmersionItem item = new ImmersionItem();
            item.setTitle(IMMERSION_TITLE[i]);
            item.setActivity(IMMERSION_ACTIVITY[i]);
            mDataList.add(item);
        }
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new ImmersionAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setNewData(mDataList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    showToast("当前界面就是");
                    return;
                }
                Intent intent = new Intent(mContext, IMMERSION_ACTIVITY[position]);
                startActivity(intent);
            }
        });
    }
}
