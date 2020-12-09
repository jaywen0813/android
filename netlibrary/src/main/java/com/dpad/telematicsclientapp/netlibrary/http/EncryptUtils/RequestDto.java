package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils;

import java.io.Serializable;

public class RequestDto implements Serializable {



        String requestId;//操作id
        String userId;
        String appId;
        String url;
        String deviceType;
        String surplusTimes;//剩余的请求次数

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getSurplusTimes() {
            return surplusTimes;
        }

        public void setSurplusTimes(String surplusTimes) {
            this.surplusTimes = surplusTimes;
        }



}
