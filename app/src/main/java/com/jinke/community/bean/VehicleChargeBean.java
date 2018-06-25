package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class VehicleChargeBean {

    private List<DataBean> list;

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public static class DataBean {
        /**
         * parking_Key : eabdea1212934cf49327382ece1789aa
         * park_Name : 成都花园
         * pay_month : 2
         * standard_price : 400
         * valid_date : 1505790010
         * carType_No : 3652
         * end_valid_date : 到期时间
         */

        private String parking_Key;
        private String park_Name;
        private String pay_month;
        private String standard_price;
        private String valid_date;
        private String carType_No;
        private String end_valid_date;

        public String getParking_Key() {
            return parking_Key;
        }

        public void setParking_Key(String parking_Key) {
            this.parking_Key = parking_Key;
        }

        public String getPark_Name() {
            return park_Name;
        }

        public void setPark_Name(String park_Name) {
            this.park_Name = park_Name;
        }

        public String getPay_month() {
            return pay_month;
        }

        public void setPay_month(String pay_month) {
            this.pay_month = pay_month;
        }

        public String getStandard_price() {
            return standard_price;
        }

        public void setStandard_price(String standard_price) {
            this.standard_price = standard_price;
        }

        public String getValid_date() {
            return valid_date;
        }

        public void setValid_date(String valid_date) {
            this.valid_date = valid_date;
        }

        public String getCarType_No() {
            return carType_No;
        }

        public void setCarType_No(String carType_No) {
            this.carType_No = carType_No;
        }

        public String getEnd_valid_date() {
            return end_valid_date;
        }

        public void setEnd_valid_date(String end_valid_date) {
            this.end_valid_date = end_valid_date;
        }
    }
}
