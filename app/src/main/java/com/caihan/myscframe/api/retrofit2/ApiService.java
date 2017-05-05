package com.caihan.myscframe.api.retrofit2;


import com.caihan.myscframe.api.retrofit2.databean.ResultData;
import com.caihan.myscframe.api.retrofit2.resmodel.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by caihan on 2017/1/11.
 *
 * @GET 表明这是get请求
 * @POST 表明这是post请求
 * @PUT 表明这是put请求
 * @DELETE 表明这是delete请求
 * @PATCH 表明这是一个patch请求，该请求是对put请求的补充，用于更新局部资源
 * @HEAD 表明这是一个head请求
 * @OPTIONS 表明这是一个option请求
 * @HTTP 通用注解, 可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
 * @Headers 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
 * @Header 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
 * @Body 多用于post请求发送非表单数据, 比如想要以post方式传递json格式数据
 * @Filed 多用于post请求中表单字段, Filed和FieldMap需要FormUrlEncoded结合使用
 * @FiledMap 和@Filed作用一致，用于不确定表单参数
 * @Part 用于表单字段, Part和PartMap与Multipart注解结合使用, 适合文件上传的情况
 * @PartMap 用于表单字段, 默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
 * <p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * </p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * @Path 用于url中的占位符,{占位符}和PATH只用在URL的path部分，url中的参数使用Query和QueryMap代替，保证接口定义的简洁
 * @Query 用于Get中指定参数
 * @QueryMap 和Query使用类似
 * @Url 指定请求路径
 */
public interface ApiService {

    //使用@Headers添加多个请求头
    @Headers({
            "User-Agent:android",
            "apikey:123456789",
    })
    @POST("{url}")
    Call<HttpResult<ResultData>> post(@Path(value = "url", encoded = true) String url, @QueryMap Map<String, String> map);

    @GET("mobile/active")
    Call<HttpResult<ResultData>> get(@Header("token") String token, @Query("id") int activeId);

    @GET("active/list")
    Call<HttpResult<ResultData>> ActiveList();

    @POST("api/news/newsList")
    Call<HttpResult<ResultData>> post2(@QueryMap Map<String, String> map);

    @POST()
    Observable<HttpResult<ResultData>> rxPost(@Url String url, @QueryMap Map<String, String> map);

    @POST()
    Call<HttpResult<ResultData>> rxPost2(@Url String url, @QueryMap Map<String, String> map);
    /**
     * 很多情况下，我们需要上传json格式的数据。比如当我们注册新用户的时候，因为用户注册时的数据相对较多，
     * 并可能以后会变化，这时候，服务端可能要求我们上传json格式的数据。此时就要@Body注解来实现。
     * 直接传入实体,它会自行转化成Json
     *
     * @param url
     * @param post
     * @return
     */
    @POST("api/{url}/newsList")
    Call<HttpResult<ResultData>> login(@Path("url") String url, @Body ResultData post);

    /**
     * 单张图片上传
     * retrofit 2.0的上传和以前略有不同，需要借助@Multipart注解、@Part和MultipartBody实现。
     *
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST("{url}")
    Call<HttpResult<ResultData>> upload(@Path("url") String url, @Part MultipartBody.Part file);

    /**
     * 多张图片上传
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("upload/upload")
    Call<HttpResult<ResultData>> upload(@PartMap Map<String, MultipartBody.Part> map);

    /**
     * 图文混传
     *
     * @param post
     * @param map
     * @return
     */
    @Multipart
    @POST("")
    Call<HttpResult<ResultData>> register(@Body ResultData post, @PartMap Map<String, MultipartBody.Part> map);

    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<HttpResult<ResultData>> downloadPicture(@Url String fileUrl);

    /**
     * 这里需要注意的是如果下载的文件较大，比如在10m以上，那么强烈建议你使用@Streaming进行注解，否则将会出现IO异常.
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<HttpResult<ResultData>> downloadPicture2(@Url String fileUrl);

    @POST()
    @FormUrlEncoded
    Observable<HttpResult<ResultData>> executePost(@FieldMap Map<String, Object> maps);
}
