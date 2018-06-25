package com.jinke.community.bean;

/**
 * Created by Administrator on 2018/6/21.
 */

public class WXPayBean {

    /**
     * sign : 42A8D6EFDD3D8EA77D07FB725962CEC1
     * timestamp : 1529576304
     * noncestr : TukPCMrlhk8jvS9h
     * partnerid : 1487743262
     * prepayid : wx211818248861152cd88b250d1443862867
     * packageValue : Sign=WXPay
     * appid : wx90868d2ade65417c
     */

    private String sign;
    private int timestamp;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String packageValue;
    private String appid;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
