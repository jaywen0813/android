package com.dpad.telematicsclientapp.netlibrary.entity;



import com.dpad.telematicsclientapp.netlibrary.net.BaseHttpResultModel;

/**
 * @创建者 booobdai.
 * @创建时间 2017/12/19  15:46.
 */

public class LogoutModel extends BaseHttpResultModel {


    /**
     * code : 200
     * message : null
     * result : 退出成功
     * total : null
     */


    private String result;
    private String total;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
