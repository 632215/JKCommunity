package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-19.
 */

public class PaymentRecordDetailBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * into_account_time : 1503208490
         * jk_serial_num :
         * pay_type : app_alipay
         * order_num : wx20170820135442681843
         * pay_list : [{"month":"2017-08","money":0.02,"projectname":"车位服务费,猫眼门铃费"},{"month":"2017-07","money":0.01,"projectname":"洋房物业服务费"}]
         * pay_time : 1503208490
         * total_money : 0.03
         * pay_name : 缴费
         */

        private String into_account_time;
        private String jk_serial_num;
        private String pay_type;
        private String order_num;
        private String pay_time;
        private double total_money;
        private String pay_name;
        private List<PayListBean> pay_list;

        public String getInto_account_time() {
            return into_account_time;
        }

        public void setInto_account_time(String into_account_time) {
            this.into_account_time = into_account_time;
        }

        public String getJk_serial_num() {
            return jk_serial_num;
        }

        public void setJk_serial_num(String jk_serial_num) {
            this.jk_serial_num = jk_serial_num;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public double getTotal_money() {
            return total_money;
        }

        public void setTotal_money(double total_money) {
            this.total_money = total_money;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public List<PayListBean> getPay_list() {
            return pay_list;
        }

        public void setPay_list(List<PayListBean> pay_list) {
            this.pay_list = pay_list;
        }

        public static class PayListBean {
            /**
             * month : 2017-08
             * money : 0.02
             * projectname : 车位服务费,猫眼门铃费
             */

            private String month;
            private double money;
            private String projectname;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getProjectname() {
                return projectname;
            }

            public void setProjectname(String projectname) {
                this.projectname = projectname;
            }
        }
    }
}
