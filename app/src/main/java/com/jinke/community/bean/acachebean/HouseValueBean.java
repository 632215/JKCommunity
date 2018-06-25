package com.jinke.community.bean.acachebean;

import java.io.Serializable;

/**
 * Created by root on 17-8-18.
 */

public class HouseValueBean implements Serializable {

    /**
     * areaNum : 105
     * totalprice : 516
     * rent : 0
     */

    private String areaNum;
    private String totalprice;
    private String rent;

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
