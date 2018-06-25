package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-2.
 */

public class CityBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 110000
         * name : 北京市
         */
        private boolean isSelet = false;
        private int id;
        private String name;

        public boolean isSelet() {
            return isSelet;
        }

        public void setSelet(boolean selet) {
            isSelet = selet;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
