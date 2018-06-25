package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/8/3.
 */

public class PendingDetailsBean {
    private String costName;
    private String money;

    public PendingDetailsBean(String costName, String money) {
        this.costName = costName;
        this.money = money;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
