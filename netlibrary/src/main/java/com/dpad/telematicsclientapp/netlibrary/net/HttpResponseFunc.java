package com.dpad.telematicsclientapp.netlibrary.net;


import com.dpad.telematicsclientapp.util.utils.T;
import com.socks.library.KLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Observable;
import rx.functions.Func1;

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
//        if(throwable.getLocalizedMessage().contains("500")){
//            //com.dpad.crmclientapp.android.util.utils.T.showToastSafe("服务数据异常");
//        }else if(throwable.getLocalizedMessage().contains("Failed to connect to")){
//            com.dpad.crmclientapp.android.util.utils.T.showToastSafeError("网络异常，请检查网络设置");
//        }
        if (throwable instanceof HttpException ||throwable instanceof ConnectException ||throwable instanceof SocketTimeoutException){
            com.dpad.telematicsclientapp.util.utils.T.showToastSafeError("服务器开小差了，请稍后再试~");
        } else {
            KLog.e(throwable.toString());
        }
        return Observable.error(throwable);
    }
}