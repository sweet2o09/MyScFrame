package app.caihan.myscframe.ui.coordinator;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2019/3/24
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CoordinatorPresenter extends MvpBasePresenter<CoordinatorContract.View> {


    public CoordinatorPresenter(Context context) {
        super(context);
        setPageSize(50);
    }

    public void getData(boolean isRefresh) {
        getView().getDataFinish(isRefresh, getList(), 20);
    }

    private List<String> getList() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("测试数据" + (i + 1));
        }
        return strings;
    }

    @Override
    public void destroy() {

    }
}