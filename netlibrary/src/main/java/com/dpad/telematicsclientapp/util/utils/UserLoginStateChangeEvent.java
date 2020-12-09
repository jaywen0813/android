package com.dpad.telematicsclientapp.util.utils;

import com.dpad.telematicsclientapp.mvp.event.IBus;



/**
 * @创建者 booobdai.
 * @创建时间 2017/12/7  9:17.
 * @描述 ${用户登录状态改变的通知事件}.
 */
public class UserLoginStateChangeEvent implements IBus.IEvent {

    //是否是登录状态
    private boolean isLogin;

    //是否是t车服务
    private boolean isTService;

    public boolean isTService() {
        return isTService;
    }

    public void setTService(boolean TService) {
        isTService = TService;
    }

    /**
     * @param isLogin 当前状态是否是登录
     */
    public UserLoginStateChangeEvent(boolean isLogin, boolean isTService) {
        this.isLogin = isLogin;
        this.isTService = isTService;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public int getTag() {
        return Constant.EventBus.TAG_USER_LOGIN_STATE_CHANGE;
    }
}
