package app.caihan.myscframe.ui.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.caihan.scframe.widget.recyclerview.TopFloatDecoration;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.ArrayList;
import java.util.List;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import app.caihan.myscframe.ui.recyclerview.provider.Item0Provider;
import app.caihan.myscframe.ui.recyclerview.provider.Item1Provider;
import app.caihan.myscframe.ui.recyclerview.provider.Item2Provider;
import butterknife.BindView;


/**
 * 顶部吸附
 *
 * @author caihan
 * @date 2019/3/30
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class TopAdsorptionActivity extends BaseScActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private TopAdsorptionAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_top_adsorption;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        setBaseToolbarLayout(mToolbar, "顶部吸附");
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.addItemDecoration(new TopFloatDecoration(NormalMultipleEntity.TYPE_0));
        mAdapter = new TopAdsorptionAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.openLoadAnimation();
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showToast("点击了第" + position + "张图片");
//            }
//        });
        mAdapter.setNewData(getNormalMultipleEntities());
    }

    private List<NormalMultipleEntity> getNormalMultipleEntities() {
        List<NormalMultipleEntity> list = new ArrayList<>();
        int year = 2019;
        int month = 12;
        int content = 3;

        String yearS = "";
        for (int i = 0; i <= 2; i++) {
            yearS = (year - i) + "年";
            list.add(new NormalMultipleEntity(NormalMultipleEntity.TYPE_0, yearS));
            for (int month1 = month; month1 > 0; month1--) {
                list.add(new NormalMultipleEntity(NormalMultipleEntity.TYPE_1, month1 + "月"));
                for (int i1 = 0; i1 < content; i1++) {
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.TYPE_2, month1 + "月" + i1 + "个正文"));
                }
            }
        }
        return list;
    }


    class TopAdsorptionAdapter extends MultipleItemRvAdapter<NormalMultipleEntity, BaseViewHolder> {

        public TopAdsorptionAdapter() {
            super(null);
            //构造函数若有传其他参数可以在调用finishInitialize()之前进行赋值，赋值给全局变量
            //这样getViewType()和registerItemProvider()方法中可以获取到传过来的值
            //getViewType()中可能因为某些业务逻辑，需要将某个值传递过来进行判断，返回对应的viewType
            //registerItemProvider()中可以将值传递给ItemProvider

            //If the constructor has other parameters, it needs to be assigned before calling finishInitialize() and assigned to the global variable
            // This getViewType () and registerItemProvider () method can get the value passed over
            // getViewType () may be due to some business logic, you need to pass a value to judge, return the corresponding viewType
            //RegisterItemProvider() can pass value to ItemProvider

            finishInitialize();
        }

        @Override
        protected int getViewType(NormalMultipleEntity entity) {
            //根据实体类判断并返回对应的viewType，具体判断逻辑因业务不同，这里这是简单通过判断type属性
            //According to the entity class to determine and return the corresponding viewType,
            //the specific judgment logic is different because of the business, here is simply by judging the type attribute
            return entity.type;
        }

        @Override
        public void registerItemProvider() {
            mProviderDelegate.registerProvider(new Item0Provider());
            mProviderDelegate.registerProvider(new Item1Provider());
            mProviderDelegate.registerProvider(new Item2Provider());
        }

    }
}
