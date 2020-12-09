package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils;

import java.io.Serializable;

/**
 * @Author: WindyHu
 * @Date: 2019/5/27 17:52
 * @Description: rc请求体统一封装
 */
public class RcReqDto implements Serializable{

    private static final long serialVersionUID = 8906341294975776357L;
    /**
     * 加密内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
