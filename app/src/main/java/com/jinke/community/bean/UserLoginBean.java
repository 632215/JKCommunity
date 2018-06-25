package com.jinke.community.bean;

/**
 * Created by root on 17-7-28.
 */

public class UserLoginBean {

    /**
     * captcha : 005883
     */

    private String captcha;
    /**
     * accessToken :
     */
    private boolean isHouse;

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    private String accessToken;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
