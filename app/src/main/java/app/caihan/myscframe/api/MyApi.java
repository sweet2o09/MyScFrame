package app.caihan.myscframe.api;

import com.caihan.scframe.api.ScHttpManager;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.callback.CallClazzProxy;

import java.io.File;
import java.util.List;

import app.caihan.myscframe.config.Constant;
import app.caihan.myscframe.model.ResultsBean;
import io.reactivex.Observable;

/**
 * @author caihan
 * @date 2019/3/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class MyApi {

    public static Observable<List<ResultsBean>> getGankApi(int pageSize, int indexPage) {
        return ScHttpManager.get(Constant.WELFARE + File.separator + pageSize + File.separator + indexPage,
                new CallClazzProxy<GankApiResult<List<ResultsBean>>, List<ResultsBean>>(new TypeToken<List<ResultsBean>>() {
                }.getType()) {
                });
    }
}
