package com.caihan.myscframe;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.myscframe.demo.EventBusAct;
import com.caihan.scframe.framework.base.ScActivity;
import com.caihan.scframe.utils.evenbus.EventBusUtils;
import com.caihan.scframe.utils.evenbus.EventSticky;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.caihan.myscframe.config.ConstValue.ACTIVITY;
import static com.caihan.myscframe.config.ConstValue.TITLE;

public class MainActivity extends ScActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private ArrayList<HomeItem> mDataList;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initActionBar() {

    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
        initAdapter();
    }

    @Override
    public void request() {

    }

    private void initAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(mDataList);
        homeAdapter.openLoadAnimation();
        View top = getLayoutInflater().inflate(R.layout.top_view,
                (ViewGroup) mRecyclerView.getParent(), false);
        homeAdapter.addHeaderView(top);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (EventBusAct.class == ACTIVITY[position]) {
                    EventBusUtils.postSticky(new EventSticky("Sticky"));
                }
                Intent intent = new Intent(MainActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(homeAdapter);
    }
}
