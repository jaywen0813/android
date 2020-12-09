package com.dpad.telematicsclientapp.netlibrary.net;


import com.dpad.telematicsclientapp.netlibrary.entity.LoginModel;
import com.dpad.telematicsclientapp.netlibrary.entity.LogoutModel;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/15  16:33.
 * @描述 ${Retrofit接口管理}.
 */
public interface EventsService {


    /**
     * @return
     */
    @POST("users/user-logout")
    Flowable<LogoutModel> logout(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * @return
     */
    @POST("users/user-pwd-update")
    @FormUrlEncoded
    Flowable<LogoutModel> updatePwd(@FieldMap Map<String, String> map);


    /**
     * 用户登录
     *
     * @param client
     * @param mobile   账号(手机号)
     * @param password 密码
     * @param sign
     * @return
     */
    @POST("login")
    @Headers("application/json;charset=UTF-8")
    @FormUrlEncoded
    Flowable<LoginModel> login(@Field("client") String client,
                               @Field("mobile") String mobile,
                               @Field("password") String password,
                               @Field("sign") String sign);
}
