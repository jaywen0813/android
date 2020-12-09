package com.dpad.telematicsclientapp.netlibrary.http;


import android.content.Context;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.util.utils.T;
import com.socks.library.KLog;




/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-06-12-0012 13:36
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NetInstance {

    private static MainService sEventsService;
    private static MainService sEventsService1;
    private static MainService sEventsService2;
    private static MainService sEventsService3;

    public static MainService getEventsService() {
        Context activity = Kits.Empty.check(AppManager.getInstance().getCurrentActivity()) ? MainApplicaton.getInstance().getBaseContext() : AppManager.getInstance().getCurrentActivity();
        if (activity != null) {
            if (NetworkUtil.isNetworkAvailable(activity)) {
                T.showToastSafeError("当前网络不可用,请检查网络设置!");
                KLog.e("当前网络不可用,请检查网络设置!");
            }
        }
        if (sEventsService == null) {
            synchronized (NetInstance.class) {
                if (sEventsService == null) {
                    sEventsService = ApiClient.getRetrofit(ApiConstants.APP_API_HOST).create(MainService.class);
                }
            }
        }
        return sEventsService;
    }

    /**
     * 数据埋点专用url
     * @return
     */
    public static MainService getDataEventsService() {
        if (sEventsService1 == null) {
            synchronized (NetInstance.class) {
                if (sEventsService1 == null) {
                    sEventsService1= ApiClient.getRetrofit(ApiConstants.APP_API_HOST_ForData).create(MainService.class);
                }
            }
        }
        return sEventsService1;
    }

    /**
     * 远程控制的url
     * @return
     */
    public static MainService getRemoteControlService() {
        Context activity = Kits.Empty.check(AppManager.getInstance().getCurrentActivity()) ? MainApplicaton.getInstance().getBaseContext() : AppManager.getInstance().getCurrentActivity();
        if (activity != null) {
            if (NetworkUtil.isNetworkAvailable(activity)) {
                T.showToastSafeError("当前网络不可用,请检查网络设置!");
                KLog.e("当前网络不可用,请检查网络设置!");
            }
        }
        if (sEventsService2 == null) {
            synchronized (NetInstance.class) {
                if (sEventsService2 == null) {
                    sEventsService2= ApiClient.getRetrofit(ApiConstants.getRemoteControlBaseUrl()).create(MainService.class);
                }
            }
        }
        return sEventsService2;
    }


    //充电桩动态显示隐藏url
    public static MainService getEventsService2() {
        Context activity = Kits.Empty.check(AppManager.getInstance().getCurrentActivity()) ? MainApplicaton.getInstance().getBaseContext() : AppManager.getInstance().getCurrentActivity();
        if (activity != null) {
            if (NetworkUtil.isNetworkAvailable(activity)) {
                T.showToastSafeError("当前网络不可用,请检查网络设置!");
                KLog.e("当前网络不可用,请检查网络设置!");
            }
        }
        if (sEventsService3 == null) {
            synchronized (NetInstance.class) {
                if (sEventsService3 == null) {
                    sEventsService3 = ApiClient.getRetrofit(ApiConstants.APP_API_HOST2).create(MainService.class);
                }
            }
        }
        return sEventsService3;
    }


}
