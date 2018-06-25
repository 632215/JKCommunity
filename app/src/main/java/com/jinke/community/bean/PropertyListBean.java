package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-11.
 */

public class PropertyListBean implements Serializable{


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * content : 给你个好哦看过贵客
         * id : e3534d46-5907-4c44-9b4d-05ab8f30e600
         * createTime : 2017/8/15 14:17:00
         * status : 已完成
         */

        private String content;
        private String id;
        private String createTime;
        private String status;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
