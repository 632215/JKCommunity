package com.jinke.community.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/27.
 */

public class VoteStatusBean implements Serializable {
        private String id;
        private String userId;
        private String userName;
        private String jmOrderId;
        private String voteTime;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getVoteTime() {
            return voteTime;
        }

        public void setVoteTime(String voteTime) {
            this.voteTime = voteTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
//    }
}
