package com.dpad.telematicsclientapp.netlibrary.http;

/**
 * Created by vigss on 2018/3/19.
 * <p>
 * 如果是用生产环境,需要将APP_API_HOST切换为生产的地址, isRelease需要改为true(分享等url会自动改变)
 */

public class ApiConstants {
    /**
     * 生产地址需要重新打包
     * 如果是预生产,将isRelease 改为false,如果是生产,改为true;订单,商城,爱车分享链接会改变,baseurl需手动改
     */
    private static boolean isRelease = false;

    //   sit登陆地址
//    public static final String APP_API_HOST ="http://123.125.218.30:10081/app-backstage/app-backstages/";

    //    sit远控地址
//    public static final String REMOTE_CONTROL_BASE_URL="http://123.125.218.30:10081/app-rc/";


    /**
     *远程控制的本地调试链接
     */
//    public static String REMOTE_CONTROL_BASE_URL = "http://192.168.0.162:8190/";
    //余聪本地
//    public static final String APP_API_HOST ="http://192.168.0.162:8181/app-backstages/";


    /**
     * 远控预生产
     */
    public static String REMOTE_CONTROL_BASE_URL = "https://ntsp.dpca.com.cn/preprod/appportal/app-rc/";

    public static final String APP_API_HOST = getUrl();//总的url

    public static final String APP_API_HOST2 = getUrl2();//充电桩动态显示隐藏专用的url

    /**
     * 生产环境地址根据release改变而改变
     *
     * @return
     */
    private static String getUrl() {
        /**
         * 预生产登录地址
         */
        String url = "https://ntsp.dpca.com.cn/preprod/appportal/appCenter/app-nev/app-nev/";
        if (isRelease) {
            url = url.replace("preprod/", "");
        }
        return url;
    }


    //远控生产
//    public static String REMOTE_CONTROL_BASE_URL = "https://ntsp.dpca.com.cn/appportal/app-rc/";


    //数据埋点专用baseurl
    public static final String APP_API_HOST_ForData = APP_API_HOST.substring(0, APP_API_HOST.lastIndexOf("app-nev/"));


    /**
     * 爱车分享预生产
     */
    public static final String APP_SHARE_CARECAR_URL = "https://ntsp.dpca.com.cn/preprod/appportal/#/maintainCar?vin=";

    /**
     * 商城订单预生产
     */
    public static final String APP_TORDER_URL = "https://ntsp.dpca.com.cn/preprod/tmall/#/order?token=";

    /**
     * 商城订单预生产
     */
//    public static final String APP_TMALL_URL = "https://ntsp.dpca.com.cn/preprod/tmall/#/?token=";
    public static final String APP_TMALL_URL = "https://ntsp.dpca.com.cn/preprod/nev_tmall/#/?token=";


    /**
     * 预约试驾
     */
//    public static final String APP_YYSJ_URL = "https://ntsp.dpca.com.cn/preprod/tmall/#/subscribe?";
    public static final String APP_YYSJ_URL = "https://ntsp.dpca.com.cn/preprod/nev_tmall/#/subscribe?";


    /**
     * 获取爱车分享的url
     *
     * @return
     */
    public static String getCarCareShareUrl() {
        return isRelease ? APP_SHARE_CARECAR_URL.replace("preprod/", "") : APP_SHARE_CARECAR_URL;
    }

    /**
     * 获取t订单的url
     *
     * @return
     */
    public static String getTorderUrl() {
        return isRelease ? APP_TORDER_URL.replace("preprod/", "") : APP_TORDER_URL;
    }

    /**
     * @return
     */
    public static String getAppTmallUrl() {
        return isRelease ? APP_TMALL_URL.replace("preprod/", "") : APP_TMALL_URL;
    }


    /**
     * @return
     */
    public static String getAppYysjUrl() {
        return isRelease ? APP_YYSJ_URL.replace("preprod/", "") : APP_YYSJ_URL;
    }

    /**
     * 远程控制的url
     *
     * @return
     */
    public static String getRemoteControlBaseUrl() {
        return isRelease ? REMOTE_CONTROL_BASE_URL.replace("preprod/", "") : REMOTE_CONTROL_BASE_URL;
    }

    //充电桩动态显示隐藏url
    private static String getUrl2() {
        /**
         * 预生产登录地址
         */
        String url = "https://ntsp.dpca.com.cn/preprod/appportal/appCenter/app-nev/vehicle-type/";
        if (isRelease) {
            url = url.replace("preprod/", "");
        }
        return url;
    }

}
