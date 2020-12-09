package com.dpad.telematicsclientapp.netlibrary.newapp.entity;

import java.io.Serializable;

/**
 * @Auther: WindyHu
 * @Date: 2018/5/11 09:43
 * @Description:
 */
public class CheckUpdateAppVO implements Serializable {

    private static final long serialVersionUID = -7925014676906585664L;
    private String id = "";
    private String versCode = "";
    private String osType = "";
    private String versDes = "";
    private String url = "";
    private boolean forceFlag = false;
    private String miniOsVersion = "";
    private String fileName = "";
    private String publishTime = "";
    private String authCode = "";
    private String versType = "";
    private String checkFlag = "";
    private String appid = "";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersCode() {
        return versCode;
    }

    public void setVersCode(String versCode) {
        this.versCode = versCode;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getVersDes() {
        return versDes;
    }

    public void setVersDes(String versDes) {
        this.versDes = versDes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getForceFlag() {
        return forceFlag;
    }

    public void setForceFlag(boolean forceFlag) {
        this.forceFlag = forceFlag;
    }

    public String getMiniOsVersion() {
        return miniOsVersion;
    }

    public void setMiniOsVersion(String miniOsVersion) {
        this.miniOsVersion = miniOsVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getVersType() {
        return versType;
    }

    public void setVersType(String versType) {
        this.versType = versType;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
