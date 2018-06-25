package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-21.
 */

public class ListDateInfo {

    /**
     * listDate : [{"dateTime":1499733799411}]
     * total : 0
     */

    private int total;
    private List<ListDateBean> listDate;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListDateBean> getListDate() {
        return listDate;
    }

    public void setListDate(List<ListDateBean> listDate) {
        this.listDate = listDate;
    }

    public static class ListDateBean {
        /**
         * dateTime : 1499733799411
         */

        private long dateTime;

        public long getDateTime() {
            return dateTime;
        }

        public void setDateTime(long dateTime) {
            this.dateTime = dateTime;
        }
    }
}
