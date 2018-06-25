package com.jinke.community.bean;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-11.
 */

public class BrokenDetailsBean {

    /**
     * content : 后台测试
     * contactName : Panamera
     * communityName : 成都·金科一城
     * picUrls : http://dev.tq-service.com/jinmei/Uploads/Wx/2017-08-10/201708101129395395.jpg,http://dev.tq-service.com/jinmei/Uploads/Wx/2017-08-10/201708101129395323.jpg
     */

    private String content;
    private String contactName;
    private String communityName;
    private String picUrls;
    private String status;
    private String avatar;

    private String createTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public List<String> getPicUrls() {

        List<String> list = new ArrayList<>();
        if (!StringUtils.isEmpty(picUrls)) {
            String[] pic = picUrls.split(",");
            for (int i = 0; i < pic.length; i++) {
                list.add(pic[i]);
            }
        }
        return list;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }
}
