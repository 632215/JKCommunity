package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class PendingPaymentBean {
    private String time;
    private String totalMoney;
    private List<PendingDetailsBean> list;

    public PendingPaymentBean(String time, String totalMoney, List<PendingDetailsBean> list) {
        this.time = time;
        this.totalMoney = totalMoney;
        this.list = list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<PendingDetailsBean> getList() {
        return list;
    }

    public void setList(List<PendingDetailsBean> list) {
        this.list = list;
    }
}
