package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/8/1.
 */

public class OwnerBean {
    private String name;
    private String phoneNum;

    public OwnerBean(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
