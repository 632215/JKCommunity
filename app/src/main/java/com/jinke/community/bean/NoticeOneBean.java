package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-25.
 */

public class NoticeOneBean implements Serializable{


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * content : 请您提前准备，如有疑问可咨询023-12345678。请您提前准备，如有疑问可咨询023-12345678。请您提前准备，如有疑问可咨询023-12345678。
         * createTime : 2017-08-25 15:58:33
         * title : 8.25号公告
         * noticeId : 2865
         * manager : 金科服务物业管理处
         * type : 1
         * avatar :
         * picUrl :
         */

        private String content;
        private String createTime;
        private String title;
        private int noticeId;
        private String manager;
        private int type;
        private String avatar;
        private String picUrl;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(int noticeId) {
            this.noticeId = noticeId;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
