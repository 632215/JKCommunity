package com.jinke.community.bean;

import com.jinke.community.utils.GsonUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 * 首页报事记录
 */

public class BreaKBean implements Serializable{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * content : 1115 1823
         * createTime : 2017-11-15 18:24:26
         * title :
         * noticeId : 1404
         * manager : 吴园
         * type : 2
         * avatar : http://wx.qlogo.cn/mmopen/WD4FduqfeKKFRicRdMY4EjTckKzcbbic2xm8ZUXjeFTZEje3slnD8ibXiavIR5YYN6j9yLV6MRlU0ehmZWGajUhicTQ/0
         * picUrl : https://staticfile.tq-service.com/image/LargeSystem/20171115/0e7025592f2044c5a34870843bc3d8b7.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/a801fa20d32f49f8b787e9414d3365bc.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/b086ca6c074f44cc967556fa2152908b.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/1a808cf64aca433db40de39f3ae94518.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/e3e84398067745a1bffc551885f91a1f.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/f7db2c78d70e4106a39a608320abab39.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/f0f1e0c4640e47998afcf9e56538dcb9.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/8a39abcc1bde47dbabcbffaa7f479041.jpg,https://staticfile.tq-service.com/image/LargeSystem/20171115/5a8f5fd8a95e4be9a4de86ea24bea645.jpg
         * voteStatus : []
         */




        private String content;
        private String createTime;
        private String title;
        private int noticeId;
        private String manager;
        private int type;
        private String avatar;
        private String picUrl;
        private String voteStatus;

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

        public PraiseBean getVoteStatus() {
            if (voteStatus.toString() != "") {
                return GsonUtil.GsonToBean(voteStatus, PraiseBean.class);
            } else {
                return null;
            }
        }

        public void setVoteStatus(PraiseBean voteStatus) {
            this.voteStatus = GsonUtil.GsonString(voteStatus);
        }
    }
}
