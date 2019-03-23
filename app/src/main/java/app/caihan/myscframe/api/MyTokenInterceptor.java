package app.caihan.myscframe.api;

import com.google.gson.Gson;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;

import okhttp3.Response;

/**
 * @author caihan
 * @date 2019/3/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MyTokenInterceptor extends BaseExpiredInterceptor {

    private GankApiResult mGankApiResult;

    /**
     * 处理响应是否有效
     *
     * @param response
     * @param bodyString
     * @return true = 无效相应->responseExpired(Chain chain, String bodyString)
     */
    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        mGankApiResult = new Gson().fromJson(bodyString, GankApiResult.class);
        if (mGankApiResult != null) {
            int code = mGankApiResult.getCode();
            if (code != 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 无效响应处理
     *
     * @param chain
     * @param bodyString
     * @return
     */
    @Override
    public Response responseExpired(Chain chain, String bodyString) {
        try {
            switch (mGankApiResult.getCode()) {
                case 0:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
