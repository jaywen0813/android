package com.dpad.telematicsclientapp.mvp.net;

import com.google.gson.JsonParseException;
import com.socks.library.KLog;

import org.json.JSONException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


/**
 * 接口请求 统一异常处理
 */

public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {


    private static final String TAG = ApiSubscriber.class.getSimpleName();

    @Override
    public void onError(Throwable e) {
        onComplete();
        KLog.e(TAG, "onError : " + e.getMessage()+e.toString());
        NetError error;
        if (!(e instanceof NetError)) {
            if (e instanceof HttpException) {
                error = new NetError(NetError.ERROR_MSG_NOCONNECT, NetError.NoConnectError, ((HttpException) e)
                        .code());
            } else if (e instanceof JSONException || e instanceof JsonParseException) {
                error = new NetError(NetError.ERROR_MSG_PARSEERROR, NetError.ParseError);
            } else {
                error = new NetError(NetError.ERROR_MSG_OTHERERROR, NetError.OtherError);
            }
        } else {
            error = (NetError) e;
        }
        onFail(error);
    }

    @Override
    public void onComplete() {

    }

    protected void onFail(NetError error) {
        KLog.e(TAG, "onFail : " + error.getMessage());
        if (useCommonErrorHandler() && XApi.getCommonProvider() != null) {
            XApi.getCommonProvider().handleError(error);   //使用通用异常处理,在Application中
        }
    }

    protected boolean useCommonErrorHandler() {
        return true;
    }
}
