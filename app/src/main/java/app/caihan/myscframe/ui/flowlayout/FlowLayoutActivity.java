package app.caihan.myscframe.ui.flowlayout;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.caihan.scframe.utils.log.ScLog;
import com.caihan.scframe.widget.flowlayout.BaseTagFlowAdapter;
import com.caihan.scframe.widget.flowlayout.ScFlowLayout;
import com.caihan.scframe.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 流式布局Adapter
 *
 * @author caihan
 * @date 2019/4/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class FlowLayoutActivity extends BaseScActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;

    private List<FlowLayoutItem> list;

    private FlowLayoutAdapter mFlowLayoutAdapter;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private String[] mVals2 = new String[]
            {"Android", "TextView", "Hello", "Helloworld", "Hello"};
    private int[] mTypes = new int[]{1, 2};

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_flow_layout;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        setBaseToolbarLayout(mToolbar, "流式布局Adapter");
        setList();
        mFlowLayoutAdapter = new FlowLayoutAdapter();
        mFlowLayoutAdapter.bindToFlowLayout(mFlowLayout);
        mFlowLayoutAdapter.setNewData(list);
        mFlowLayoutAdapter.notifyDataSetChanged();
        mFlowLayoutAdapter.setOnItemClickListener(new BaseTagFlowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseTagFlowAdapter adapter, View view, int position) {
                ScLog.debug("onItemClick position = " + position);
            }
        });
        mFlowLayoutAdapter.setOnItemChildClickListener(new BaseTagFlowAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseTagFlowAdapter adapter, View view, int position) {
                ScLog.debug("onItemChildClick position = " + position);
            }
        });
    }

    private void setList() {
        list = new ArrayList<>();
        list.clear();
        FlowLayoutItem flowLayoutItem;
        int index;
        int indexType;
        for (int i = 0; i < 15; i++) {
            index = (int) (Math.random() * mVals.length);
            indexType = (int) (Math.random() * mTypes.length);
            flowLayoutItem = new FlowLayoutItem();
            flowLayoutItem.setType(mTypes[indexType]);
            flowLayoutItem.setMsg(mVals[index]);
            list.add(flowLayoutItem);
        }
    }

    private void setList2() {
        list = new ArrayList<>();
        list.clear();
        FlowLayoutItem flowLayoutItem;
        int index;
        int indexType;
        for (int i = 0; i < 13; i++) {
            index = (int) (Math.random() * mVals.length);
            indexType = (int) (Math.random() * mTypes.length);
            flowLayoutItem = new FlowLayoutItem();
            flowLayoutItem.setType(mTypes[i % 3 == 0 ? 0 : 1]);
            flowLayoutItem.setMsg(mVals[i]);
            list.add(flowLayoutItem);
        }
    }

    private void setList3() {
        list = new ArrayList<>();
        list.clear();
        FlowLayoutItem flowLayoutItem;
        int index;
        int indexType;
        for (int i = 0; i < 5; i++) {
            flowLayoutItem = new FlowLayoutItem();
            flowLayoutItem.setType(mTypes[0]);
            flowLayoutItem.setMsg(mVals2[i]);
            list.add(flowLayoutItem);
        }
        for (int i = 0; i < 10; i++) {
            index = (int) (Math.random() * mVals.length);
            indexType = (int) (Math.random() * mTypes.length);
            flowLayoutItem = new FlowLayoutItem();
            flowLayoutItem.setType(mTypes[indexType]);
            flowLayoutItem.setMsg(mVals[index]);
            list.add(flowLayoutItem);
        }
    }

    @OnClick({R.id.line_top_btn, R.id.line_center_btn, R.id.line_bottom_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_top_btn:
                mFlowLayout.setLineGravity(ScFlowLayout.LINE_GRAVITY_TOP);
                break;
            case R.id.line_center_btn:
                mFlowLayout.setLineGravity(ScFlowLayout.LINE_GRAVITY_CENTER);
                break;
            case R.id.line_bottom_btn:
                mFlowLayout.setLineGravity(ScFlowLayout.LINE_GRAVITY_BOTTOM);
                break;
        }
        setList();
        mFlowLayoutAdapter.setNewData(list);
        mFlowLayoutAdapter.notifyDataSetChanged();
    }
}
