package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by root on 17-8-19.
 */

public class ExpensesPayBean implements Serializable {
    private String accessToken;
    private String houseId;
    private String prepayList;
    private String moneyTotal;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPrepayList() {
        return prepayList;
    }

    public void setPrepayList(String prepayList) {
        this.prepayList = prepayList;
    }

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }
}
