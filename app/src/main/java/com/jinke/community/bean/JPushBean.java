package com.jinke.community.bean;

/**
 * Created by Administrator on 2018/3/6.
 */

public class JPushBean {

    private String page_next;
    private String type;
    private String postId;
    private String payType;
    private String pay_id;
    private String url;

    public JPushBean() {
    }

    public String getPage_next() {
        return page_next;
    }

    public void setPage_next(String page_next) {
        this.page_next = page_next;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
