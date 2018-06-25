package com.jinke.community.bean;

/**
 * Created by root on 17-8-17.
 */

public class PostItNoticeDetailBean {


    /**
     * content : <p>&nbsp;工装量尺寸地点改在人行中心办公室里面的洽谈室哈，时间为15：30，请大家相互转告&nbsp;</p><p><br/></p>
     * createTime : 2017-08-28 15:00:43
     * noticeId : 2866
     * title : 8.28
     * manager : 金科智慧城物业管理处
     * contentHtml : <p>&nbsp;工装量尺寸地点改在人行中心办公室里面的洽谈室哈，时间为15：30，请大家相互转告&nbsp;</p><p><br/></p>
     * type : 1
     * avatar :
     */

    private String content;
    private String createTime;
    private int noticeId;
    private String title;
    private String manager;
    private String contentHtml;
    private int type;
    private String avatar;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
