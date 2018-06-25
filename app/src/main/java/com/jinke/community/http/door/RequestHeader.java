package com.jinke.community.http.door;

/**
 * Created by root on 17-8-21.
 */

public class RequestHeader {
    private String unionId;
    private String appId;


    public String getUnionId() {
        return unionId == null ? "" : unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppId() {
        return appId == null ? "" : appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
