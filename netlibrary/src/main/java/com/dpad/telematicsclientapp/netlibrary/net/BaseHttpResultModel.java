package com.dpad.telematicsclientapp.netlibrary.net;

import com.dpad.telematicsclientapp.mvp.net.IModel;



/**
 * @创建者 booobdai.
 * @创建时间 2017/9/7  14:08.
 * @描述 ${网络请求返回数据的基类}.
 */
public class BaseHttpResultModel implements IModel {

    //ret不为100是请求失败
    public int    code;
    //错误信息
    public String msg;

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public boolean isCommonError() {
        return code != NetErrorCode.CODE_SUCCEED;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

}
