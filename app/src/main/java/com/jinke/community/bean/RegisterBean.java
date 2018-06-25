package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/17.
 */

public class RegisterBean implements Serializable{
    private String phone;
    private String smsCode;
    private String avatar;
    private String nickName;
    private String sex;
    private String source;
    private String tokenType;
    private String exToken;
    private String token;

    public RegisterBean() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExToken() {
        return exToken;
    }

    public void setExToken(String exToken) {
        this.exToken = exToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
