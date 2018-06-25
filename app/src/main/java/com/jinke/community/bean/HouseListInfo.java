package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-21.
 */

public class HouseListInfo {

    /**
     * listDate : [{"houseId":"123232","houseName":"测试组团-测试楼栋1-1单元-101111","tqcommunityAddr":"重庆 重庆市 渝北区","tqcommunityName":"测试小区"}]
     * total : 1
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
         * houseId : 123232
         * houseName : 测试组团-测试楼栋1-1单元-101111
         * tqcommunityAddr : 重庆 重庆市 渝北区
         * tqcommunityName : 测试小区
         */

        private String houseId;
        private String houseName;
        private String tqcommunityAddr;
        private String tqcommunityName;

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getTqcommunityAddr() {
            return tqcommunityAddr;
        }

        public void setTqcommunityAddr(String tqcommunityAddr) {
            this.tqcommunityAddr = tqcommunityAddr;
        }

        public String getTqcommunityName() {
            return tqcommunityName;
        }

        public void setTqcommunityName(String tqcommunityName) {
            this.tqcommunityName = tqcommunityName;
        }
    }
}

