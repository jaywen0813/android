package com.dpad.telematicsclientapp.netlibrary.newapp.http;

import com.dpad.telematicsclientapp.netlibrary.entity.CuscResult;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.CheckUpdateAppVO;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UrlService {
    //版本更新
//    @POST("version/select-verscode")
    @POST("version/select-t-verscode")
    Observable<CuscResult<CheckUpdateAppVO>> updateApp(@Body RequestBody body);

    /**
     * 绑定cid
     * @param body
     * @return
     */
    @POST("app-users/cid-set")
    Observable<CuscResult<String>> bindingClientId(@Body RequestBody body);
}
