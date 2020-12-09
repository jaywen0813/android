package com.dpad.telematicsclientapp.netlibrary.net;

import android.app.Activity;
import android.content.Intent;


import com.dpad.telematicsclientapp.mvp.event.BusProvider;
import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.netlibrary.entity.CuscResult;

import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.PreferenceUtils;
import com.dpad.telematicsclientapp.util.utils.T;
import com.dpad.telematicsclientapp.util.utils.UserLoginStateChangeEvent;
import com.google.gson.Gson;
import com.socks.library.KLog;



/**
 * @创建者 booobdai.
 * @创建时间 2017/10/23  13:15.
 * @描述 ${Api返回数据 ret 代码}.
 */
public class NetErrorCode {

    /**
     * 成功码
     */
    public static final int CODE_SUCCEED = 00000;

    /**
     * 未登录
     */
    public static final int CODE_NEED_LOGIN = 00004;

    public static void getCodeMessageShow(String result) {
        CuscResult cuscresult = new Gson().fromJson(result, CuscResult.class);

        if (!Kits.Empty.check(cuscresult.getStatus()) && cuscresult.getStatus().equals("404")) {
            T.showToastSafeError("服务器开小差了，请稍后再试~");
            return;
        }
        switch (cuscresult.getCode()) {
            case "10001":
                T.showToastSafeError("服务器开小差了，请稍后再试~");
                KLog.e("系统内部异常");
                break;
            case "00004":
                if (MainApplicaton.sIsLogin) {
                    T.showToastSafeError("token过期，请重新登录");
                }
                detailToken();
                break;
            case "00005":
                if (MainApplicaton.sIsLogin) {
                    T.showToastSafeError("登录过期，请重新登录");
                }
                detailToken();
                break;
            case "00105":
                T.showToastSafeError(cuscresult.getMessage());
                detailToken();
                break;
            case "00106":
                T.showToastSafeError(cuscresult.getMessage());
                detailToken();
                break;
            case "00006":
                T.showToastSafeError("您的账号在另一地点登录，您已被迫下线");
                detailToken();
                break;
            case "00000":
                break;

//            case "00112"://T车辆已被绑定的错误提示码，有单独的提示框   做二手车功能的时候记得打开
//                break;
            /*
             * 设备绑定的错误码,有单独提示框
             */
            case "20002":
            case "20003":
            case "20004":
            case "20005":
                break;
            default:
                T.showToastSafeError(cuscresult.getMessage());
                break;
        }
    }

    public synchronized static void detailToken() {
        Activity activity;
        MainApplicaton.sIsLogin = false;
        MainApplicaton.setVin("");
        MainApplicaton.setSaleSubmodelId("");
        MainApplicaton.isTcar = false;
        MainApplicaton.userVehType = "0";
        MainApplicaton.setBurialPointUserType("1");
        UserLoginStateChangeEvent userLoginStateChangeEvent = new UserLoginStateChangeEvent(false, false);
        BusProvider.getBus().post(userLoginStateChangeEvent);
        PreferenceUtils.getInstance(MainApplicaton.getInstance()).clear(MainApplicaton.getContext());
        activity = AppManager.getInstance().getCurrentActivity();
//        if (!((activity instanceof KtLoginActivity) ||  (activity instanceof SplashActivity))) {  //2020年12月9日 10:07:36  注释
//            Intent intent3 = new Intent(MainApplicaton.getContext(), KtLoginActivity.class);
//            intent3.putExtra("needLogin", "needLogin");
//            activity.startActivity(intent3);
//        }
        try {
            DataStatisticsUtils.startServiceWrite(activity);
        } catch (Exception e) {
            KLog.e(e.toString());
        }
    }

}
