package com.dpad.telematicsclientapp.netlibrary.newapp.repository;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



import com.dpad.telematicsclientapp.netlibrary.entity.CuscResult;
import com.dpad.telematicsclientapp.netlibrary.http.ApiClient;
import com.dpad.telematicsclientapp.netlibrary.http.ApiConstants;
import com.dpad.telematicsclientapp.netlibrary.net.HttpResponseFunc;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.CheckUpdateAppVO;
import com.dpad.telematicsclientapp.netlibrary.newapp.http.UrlService;

import java.util.SortedMap;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by vigss on 2018/3/26.
 */

public class UpdateAppepository implements VoSource<CheckUpdateAppVO> {
    private static volatile UpdateAppepository INSTANCE;

    private UpdateAppepository() {
    }

    public static UpdateAppepository getInstance() {
        if (INSTANCE == null) {
            synchronized (UpdateAppepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UpdateAppepository();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Observable<CuscResult<CheckUpdateAppVO>> TService(SortedMap<String, String> sortedMap) {
        String strEntity = JSONObject.parseObject(JSON.toJSONString(sortedMap)).toJSONString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        return ApiClient.initService(ApiConstants.APP_API_HOST, UrlService.class, sortedMap).updateApp(body).onErrorResumeNext(new HttpResponseFunc());
    }
}
