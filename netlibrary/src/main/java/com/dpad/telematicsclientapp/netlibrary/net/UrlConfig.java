package com.dpad.telematicsclientapp.netlibrary.net;



import com.dpad.telematicsclientapp.mvp.BuildConfig;
import com.dpad.telematicsclientapp.netlibrary.http.ApiConstants;
import com.socks.library.KLog;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/8  9:29.
 * @描述 ${url 统一管理}.
 */
public class UrlConfig {

    //是否测试

    private static boolean isTest = BuildConfig.DEBUG;
    //测试服务器地址
    public static final String BASE_URL_TEST = "http://10.26.193.159/app-backstages/";
//    public static final String BASE_URL_TEST = "http://10.26.193.159:8181/app-backstages/";

    // 正式服务器地址
    public static final String BASE_URL_RELEASE = ApiConstants.APP_API_HOST;


    public static String getBaseUrl() {
        KLog.e(isTest ? BASE_URL_TEST : BASE_URL_RELEASE+"------地址");
        return isTest ? BASE_URL_TEST : BASE_URL_RELEASE;
    }
}
