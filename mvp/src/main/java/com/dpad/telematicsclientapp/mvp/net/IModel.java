package com.dpad.telematicsclientapp.mvp.net;

/**
 * M 接口
 */

public interface IModel {

    boolean isNull();       //空数据

    boolean isAuthError();  //验证错误

    boolean isBizError();   //业务错误

    boolean isCommonError(); //普通错误

    String getErrorMsg();   //后台返回的错误信息

    int getErrorCode(); //后台返回错误代码
}
