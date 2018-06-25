package com.jinke.community.bean;

/**
 * Created by Administrator on 2017/11/24.
 */

public class PraiseBean {

    /**
     * id : 3
     * status : 0
     * userId : 133713
     * userName : Us.
     * jmOrderId : 1306
     * voteTime : 1510545313
     */

    private String id;
    private String status;
    private String userId;
    private String userName;
    private String jmOrderId;
    private int voteTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJmOrderId() {
        return jmOrderId;
    }

    public void setJmOrderId(String jmOrderId) {
        this.jmOrderId = jmOrderId;
    }

    public int getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(int voteTime) {
        this.voteTime = voteTime;
    }
}
