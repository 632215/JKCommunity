package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by root on 17-8-4.
 */

public class BaseUserBean implements Serializable {
    private String openid;
    private int uid;

    private String accessToken;
    private String identity;
    private String phone;
    private String sex;
    private String nickName;
    private String name;
    private String avatar;
    private String platformName;
    private String servicePhone;
    private String pre_money;//预存余额
    private boolean isHouse;
    private boolean isShow;
    private String point_num;
    private String isSuccess;

    public String getPoint_num() {
        return point_num;
    }

    public void setPoint_num(String point_num) {
        this.point_num = point_num;
    }

    public String getPre_money() {
        return pre_money;
    }

    public void setPre_money(String pre_money) {
        this.pre_money = pre_money;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
}
