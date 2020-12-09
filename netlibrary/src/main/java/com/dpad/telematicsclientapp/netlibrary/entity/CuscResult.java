package com.dpad.telematicsclientapp.netlibrary.entity;

import java.io.Serializable;

/**
 * 统一返回格式
 * @auther: WindyHu
 * @date: 2018/5/10 9:59
 */
public class CuscResult<T> implements Serializable{


    private static final long serialVersionUID = 7925607901844005925L;

    private String code;

    private String status;

    private String message;

    private T result;

    private Integer total;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
