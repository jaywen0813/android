package com.dpad.telematicsclientapp.netlibrary.newapp.entity;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.util.PreferenceUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;

import java.io.Serializable;



/**
 * @Auther: WindyHu
 * @Date: 2018/5/11 09:43
 * @Description:
 */
public class LoginResultVO implements Serializable {
    private static final long serialVersionUID = -7925014676906585664L;

    /**
     * 用户名
     */
    private String userName = "";


    /**
     * token
     */
    private String token = "";

    /**
     * 用户ID
     */
    private String userId = "";
    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户默认车辆vin
     */
    private String vin;


    /**
     * 是否有已激活T车（1是有，空则所有车辆没激活或者没有T车）
     */
    private String isTServer;


    private String nickName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    //判断是否同意过车联网系统服务使用细则
    private String isAgree;//0未同意  1已同意

    public String getIsAgree() {
        if (Kits.Empty.check(isAgree)) {
            isAgree = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("isAgree");
        }
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString("isAgree", isAgree);
    }


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getIsTServer() {
        return isTServer;
    }

    public void setIsTServer(String isTServer) {
        this.isTServer = isTServer;
    }

    public String getUserName() {
        if (Kits.Empty.check(userName)) {
            userName = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("USERNAME");
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        if (Kits.Empty.check(token)) {
            token = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("TOKEN");
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        if (Kits.Empty.check(userId)) {
            userId = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.USERID_KEY);
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
