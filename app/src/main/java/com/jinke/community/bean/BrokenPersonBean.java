package com.jinke.community.bean;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrokenPersonBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * guid : 1667
         * content : 好哦
         * createTime : 2017-11-07 18:02:01
         * noticeId : 1293
         * title :
         * manager : 向建新
         * picUrl : https://staticfile.tq-service.com/image/LargeSystem/20171107/70b08b9b517f4bf3a24d8e984d173b00.jpg
         * voteStatus : [{"id":"4","status":"1","userId":"133713","jmOrderId":"1293","userName":"Us.","voteTime":1510553215},{"id":"13","status":"1","userId":"757","jmOrderId":"1293","userName":"米奇琳","voteTime":1511776589}]
         * type : 2
         * voteTotal : 4
         */

        private int guid;
        private String content;
        private String createTime;
        private int noticeId;
        private String title;
        private String manager;
        private String picUrl;
        private int type;
        private int status;
        private int voteTotal;
        private List<VoteStatusBean> voteStatus;


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGuid() {
            return guid;
        }

        public void setGuid(int guid) {
            this.guid = guid;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVoteTotal() {
            return voteTotal;
        }

        public void setVoteTotal(int voteTotal) {
            this.voteTotal = voteTotal;
        }

        public List<VoteStatusBean> getVoteStatus() {
            return voteStatus;
        }

        public void setVoteStatus(List<VoteStatusBean> voteStatus) {
            this.voteStatus = voteStatus;
        }

        public static class VoteStatusBean {
            /**
             * id : 4
             * status : 1
             * userId : 133713
             * jmOrderId : 1293
             * userName : Us.
             * voteTime : 1510553215
             */

            private String id;
            private String status;
            private String userId;
            private String jmOrderId;
            private String userName;
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

            public String getJmOrderId() {
                return jmOrderId;
            }

            public void setJmOrderId(String jmOrderId) {
                this.jmOrderId = jmOrderId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getVoteTime() {
                return voteTime;
            }

            public void setVoteTime(int voteTime) {
                this.voteTime = voteTime;
            }
        }
    }
}
