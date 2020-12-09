package com.dpad.telematicsclientapp.netlibrary.net;


import com.dpad.telematicsclientapp.mvp.net.XApi;
import com.dpad.telematicsclientapp.netlibrary.http.ApiConstants;



/**
 * @创建者 booobdai.
 * @创建时间 2017/8/15  16:33.
 * @描述 ${Retrofit 管理类}.
 */
public class Api {

    private static EventsService sEventsService;

    public static EventsService getEventsService() {
        if (sEventsService == null) {
            synchronized (Api.class) {
                if (sEventsService == null) {
                    sEventsService = XApi.getInstance().getRetrofit(ApiConstants.APP_API_HOST, true).create(EventsService.class);
                }
            }
        }
        return sEventsService;
    }

}
