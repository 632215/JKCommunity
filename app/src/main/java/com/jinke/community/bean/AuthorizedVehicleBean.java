package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class AuthorizedVehicleBean {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * user_No : 1710171008185753400
         * reserveOrder_ID : 5
         * reserveOrder_No : RP201710191015512546553
         * reserveOrder_ReserveTime : 1508385600000
         * reserveOrder_CreateTime : 1508379351253
         * reserveOrder_CancelTime :
         * reserveOrder_CarNO : 渝A99999
         * parking_Key : eabdea1212934cf49327382ece1789aa
         * reserveOrder_Status : 1
         * reserveOrder_Desc : 发送成功!
         */
        private String communityName;
        private String user_No;
        private String reserveOrder_ID;
        private String reserveOrder_No;
        private String reserveOrder_ReserveTime;
        private String reserveOrder_CreateTime;
        private String reserveOrder_CancelTime;
        private String reserveOrder_CarNO;
        private String parking_Key;
        private String reserveOrder_Status;
        private String reserveOrder_Desc;

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getUser_No() {
            return user_No;
        }

        public void setUser_No(String user_No) {
            this.user_No = user_No;
        }

        public String getReserveOrder_ID() {
            return reserveOrder_ID;
        }

        public void setReserveOrder_ID(String reserveOrder_ID) {
            this.reserveOrder_ID = reserveOrder_ID;
        }

        public String getReserveOrder_No() {
            return reserveOrder_No;
        }

        public void setReserveOrder_No(String reserveOrder_No) {
            this.reserveOrder_No = reserveOrder_No;
        }

        public String getReserveOrder_ReserveTime() {
            return reserveOrder_ReserveTime;
        }

        public void setReserveOrder_ReserveTime(String reserveOrder_ReserveTime) {
            this.reserveOrder_ReserveTime = reserveOrder_ReserveTime;
        }

        public String getReserveOrder_CreateTime() {
            return reserveOrder_CreateTime;
        }

        public void setReserveOrder_CreateTime(String reserveOrder_CreateTime) {
            this.reserveOrder_CreateTime = reserveOrder_CreateTime;
        }

        public String getReserveOrder_CancelTime() {
            return reserveOrder_CancelTime;
        }

        public void setReserveOrder_CancelTime(String reserveOrder_CancelTime) {
            this.reserveOrder_CancelTime = reserveOrder_CancelTime;
        }

        public String getReserveOrder_CarNO() {
            return reserveOrder_CarNO;
        }

        public void setReserveOrder_CarNO(String reserveOrder_CarNO) {
            this.reserveOrder_CarNO = reserveOrder_CarNO;
        }

        public String getParking_Key() {
            return parking_Key;
        }

        public void setParking_Key(String parking_Key) {
            this.parking_Key = parking_Key;
        }

        public String getReserveOrder_Status() {
            return reserveOrder_Status;
        }

        public void setReserveOrder_Status(String reserveOrder_Status) {
            this.reserveOrder_Status = reserveOrder_Status;
        }

        public String getReserveOrder_Desc() {
            return reserveOrder_Desc;
        }

        public void setReserveOrder_Desc(String reserveOrder_Desc) {
            this.reserveOrder_Desc = reserveOrder_Desc;
        }
    }
}
