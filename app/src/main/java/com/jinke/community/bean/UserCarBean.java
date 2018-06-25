package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class UserCarBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * parkOrder_Lock : 1
         * car_CarNo : 渝A11111
         */

        private String parkOrder_Lock;
        private String car_CarNo;

        public String getParkOrder_Lock() {
            return parkOrder_Lock;
        }

        public void setParkOrder_Lock(String parkOrder_Lock) {
            this.parkOrder_Lock = parkOrder_Lock;
        }

        public String getCar_CarNo() {
            return car_CarNo;
        }

        public void setCar_CarNo(String car_CarNo) {
            this.car_CarNo = car_CarNo;
        }
    }
}
