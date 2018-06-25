package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/8/15.
 */

public class FillGridViewBean {
    private String purpose;
    private boolean isSelecter;

    public FillGridViewBean(String purpose, boolean isSelecter) {
        this.purpose = purpose;
        this.isSelecter = isSelecter;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isSelecter() {
        return isSelecter;
    }

    public void setSelecter(boolean selecter) {
        isSelecter = selecter;
    }
}
