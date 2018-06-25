package com.jinke.community.bean;

/**
 * Created by Administrator on 2018/6/4.
 */

public class JiaLeDoorBean {
    private String errorCode;
    private String msg;
    private String qrcode;

    public JiaLeDoorBean() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
