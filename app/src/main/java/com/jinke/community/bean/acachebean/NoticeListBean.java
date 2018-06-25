package com.jinke.community.bean.acachebean;

import com.google.gson.internal.LinkedTreeMap;
import com.jinke.community.bean.VoteStatusBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-14.
 * 首页公告和爆料
 */

public class NoticeListBean implements Serializable {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * content : 请您提前准备，如有疑问可咨询023-12345678。请您提前准备，如有疑问可咨询023-12345678。请您提前准备，如有疑问可咨询023-12345678。
         * createTime : 2017-11-24 17:46:06
         * title : 看看系统时间对不对
         * noticeId : 5871
         * manager : 深圳·金科智慧城物业管理处
         * type : 1
         * noticeType : 4
         * avatar :
         * picUrl :
         * voteStatus :
         * voteTotal :
         */

        private String content;
        private String createTime;
        private String title;
        private int noticeId;
        private String manager;
        private int type;
        private String status;
        private String noticeType;
        private String avatar;
        private String picUrl;
        private Object voteStatus;
        private String voteTotal;
        private String voteIsUser;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVoteIsUser() {
            return voteIsUser;
        }

        public void setVoteIsUser(String voteIsUser) {
            this.voteIsUser = voteIsUser;
        }

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

        public String getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(String noticeType) {
            this.noticeType = noticeType;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<String> getPicUrl() {
            List<String> list = new ArrayList<>();
            if (!StringUtils.isEmpty(picUrl)) {
                String[] pic = picUrl.split(",");
                for (String path : pic) {
                    list.add(path);
                }
            }
            return list;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public List<VoteStatusBean> getVoteStatus() {
            if (this.voteStatus == null) {
                return null;
            } else {
//                Gson gson = new Gson();
//                gson.toJson(this.voteStatus);
//                return GsonUtil.GsonToBean(gson.toJson(this.voteStatus), VoteStatusBean.class);
                List<VoteStatusBean> list = new ArrayList<>();

                List<LinkedTreeMap> linkedTreeMaps = new ArrayList<>((Collection<? extends LinkedTreeMap>) this.voteStatus);
                for (LinkedTreeMap map : linkedTreeMaps) {
                    VoteStatusBean bean = new VoteStatusBean();
                    Set<String> sett = map.keySet();
                    for (String key : sett) {
                        System.out.println(key + ":" + map.get(key));
                        switch (key) {
                            case "id":
                                bean.setId((String) map.get(key));
                                break;
                            case "userId":
                                bean.setUserId((String) map.get(key));
                                break;
                            case "userName":
                                bean.setUserName((String) map.get(key));
                                break;
                            case "jmOrderId":
                                bean.setJmOrderId((String) map.get(key));
                                break;
                            case "voteTime":
                                bean.setVoteTime((String) map.get(key));
                                break;
                            case "status":
                                bean.setStatus((String) map.get(key));
                                break;
                        }
                    }
                    list.add(bean);
                }
                return list;
            }
        }

        public void setVoteStatus(Object voteStatus) {
            this.voteStatus = voteStatus;
        }

        public String getVoteTotal() {
            return voteTotal;
        }

        public void setVoteTotal(String voteTotal) {
            this.voteTotal = voteTotal;
        }
    }
}

