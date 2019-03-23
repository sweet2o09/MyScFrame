package app.caihan.myscframe.api;

import com.zhouyou.http.func.ApiResultFunc;
import com.zhouyou.http.model.ApiResult;

/**
 * ApiResult中如果data的类型是String的话,默认返回所有api数据
 * 同时不做网络请求是否成功判断{@link ApiResultFunc}
 *
 * @author caihan
 * @date 2019/3/22
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class GankApiResult<T> extends ApiResult<T> {

    private boolean error;
    private T results;

    @Override
    public String getMsg() {
        return "我挂了";
    }

    @Override
    public int getCode() {
        return error ? 0 : 1;
    }

    @Override
    public boolean isOk() {
        return !error;
    }

    @Override
    public T getData() {
        return results;
    }
}
