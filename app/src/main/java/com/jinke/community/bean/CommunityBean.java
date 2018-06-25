package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class CommunityBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * authority_name : 报事试点小区
         * config :
         * status : 1
         * authority_code : property_broken_test
         */

        private String authority_name;
        private String config;
        private String status;
        private String authority_code;

        public String getAuthority_name() {
            return authority_name;
        }

        public void setAuthority_name(String authority_name) {
            this.authority_name = authority_name;
        }

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAuthority_code() {
            return authority_code;
        }

        public void setAuthority_code(String authority_code) {
            this.authority_code = authority_code;
        }
    }
}
