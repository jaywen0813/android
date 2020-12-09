package com.dpad.telematicsclientapp.mvp.net;

/**
 * 网络接口请求异常
 */

public class NetError extends Exception {
    public static final int ParseError     = 0;   //数据解析异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int AuthError      = 2;   //用户验证异常
    public static final int NoDataError    = 3;   //无数据返回异常
    public static final int BusinessError  = 4;   //业务异常
    public static final int OtherError     = 5;   //其他异常

    public static final String ERROR_MSG_NOCONNECT   = "网络异常";
    public static final String ERROR_MSG_PARSEERROR  = "数据解析失败";
    public static final String ERROR_MSG_AUTHERROR   = "用户验证失败";
    public static final String ERROR_MSG_NODATAERROR = "无数据异常";
    public static final String ERROR_MSG_OTHERERROR  = "无法连接到服务器";

    private Throwable exception;
    private int type = NoConnectError;
    //错误码
    private int errorCode;


    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    public NetError(String detailMessage, int type, int errorCode) {
        super(detailMessage);
        this.type = type;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        if (exception != null)
            return exception.getMessage();
        return super.getMessage();
    }

    public int getType() {
        return type;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
