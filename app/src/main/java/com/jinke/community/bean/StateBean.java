package com.jinke.community.bean;

/**
 * Created by Administrator on 2018/5/30.
 */

public class StateBean {

    /**
     * status : 未通过
     */
    private String certifiedUrl;//问卷调查 需自己配上accesstoken
    private String status;
    private String remark;
    private String manualUrl;// 手册

    public String getCertifiedUrl() {
        return certifiedUrl;
    }

    public void setCertifiedUrl(String certifiedUrl) {
        this.certifiedUrl = certifiedUrl;
    }

    public String getManualUrl() {
        return manualUrl;
    }

    public void setManualUrl(String manualUrl) {
        this.manualUrl = manualUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
