package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20.
 */

public class RegisterLoginBean implements Serializable{

    /**
     * accessToken : 0eaec0a2260ec45b9b2acc98fed25759
     * isHouse : true
     */
    private String isLogin;
    private String accessToken;
    private String isHouse;

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIsHouse() {
        return isHouse;
    }

    public void setIsHouse(String isHouse) {
        this.isHouse = isHouse;
    }
}
