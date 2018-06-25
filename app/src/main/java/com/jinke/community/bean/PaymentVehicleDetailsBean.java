package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PaymentVehicleDetailsBean {
    /**
     * time_end : 1509332879
     * order_num : park20171030110736291360
     * trade_no : 2017103021001004650271927371
     * pay_name : 车位
     * into_account_time : 1509332879
     * jk_trade_no : JKWC20171030110736291360
     * total_money : 0.01
     * payType : app_alipay
     * pay_time : 1509332879
     * car_list : [{"plate_no":"A00016","park_end_time":"1509332879","pay_month":"1","standard_price":"0.01","prices":"0.01"}]
     */
    private String into_account_status;
    private String time_end;
    private String order_num;
    private String trade_no;
    private String pay_name;
    private String into_account_time;
    private String jk_trade_no;
    private String total_money;
    private String payType;
    private String pay_time;
    private List<CarListBean> car_list;

    public String getInto_account_status() {
        return into_account_status;
    }

    public void setInto_account_status(String into_account_status) {
        this.into_account_status = into_account_status;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getInto_account_time() {
        return into_account_time;
    }

    public void setInto_account_time(String into_account_time) {
        this.into_account_time = into_account_time;
    }

    public String getJk_trade_no() {
        return jk_trade_no;
    }

    public void setJk_trade_no(String jk_trade_no) {
        this.jk_trade_no = jk_trade_no;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public List<CarListBean> getCar_list() {
        return car_list;
    }

    public void setCar_list(List<CarListBean> car_list) {
        this.car_list = car_list;
    }

    public static class CarListBean {
        /**
         * plate_no : A00016
         * park_end_time : 1509332879
         * pay_month : 1
         * standard_price : 0.01
         * prices : 0.01
         */

        private String plate_no;
        private String park_end_time;
        private String pay_month;
        private String standard_price;
        private String prices;

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getPark_end_time() {
            return park_end_time;
        }

        public void setPark_end_time(String park_end_time) {
            this.park_end_time = park_end_time;
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

        public String getPrices() {
            return prices;
        }

        public void setPrices(String prices) {
            this.prices = prices;
        }
    }
}
