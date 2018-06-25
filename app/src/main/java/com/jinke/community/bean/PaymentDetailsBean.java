package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class PaymentDetailsBean {

    /**
     * into_account_time : 1512382903
     * jk_serial_num :
     * pay_type : app_alipay
     * order_num : 2017120421001004170523067911
     * pay_time : 1512382903
     * total_money : 0.01
     * pay_name : 预存
     * pay_list : [{"lave":"150.6","sort_num":2,"month":"2017-04","money":"0.01","current":150.59,"projectname":"水电公摊费"}]
     */

    private String into_account_time;
    private String jk_serial_num;
    private String pay_type;
    private String order_num;
    private String pay_time;
    private String total_money;
    private String into_account_status;
    private String pay_name;
    private List<PaymentDetailsBean.PayListBean> pay_list;

    public String getInto_account_time() {
        return into_account_time;
    }

    public void setInto_account_time(String into_account_time) {
        this.into_account_time = into_account_time;
    }

    public String getInto_account_status() {
        return into_account_status;
    }

    public void setInto_account_status(String into_account_status) {
        this.into_account_status = into_account_status;
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

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public List<PaymentDetailsBean.PayListBean> getPay_list() {
        return pay_list;
    }

    public void setPay_list(List<PaymentDetailsBean.PayListBean> pay_list) {
        this.pay_list = pay_list;
    }

    public static class PayListBean {
        /**
         * lave : 150.6
         * sort_num : 2
         * month : 2017-04
         * money : 0.01
         * current : 150.59
         * projectname : 水电公摊费
         */

        private String lave;
        private int sort_num;
        private String month;
        private String money;
        private double current;
        private int projectType;
        private String projectname;

        public int getProjectType() {
            return projectType;
        }

        public void setProjectType(int projectType) {
            this.projectType = projectType;
        }

        public String getLave() {
            return lave;
        }

        public void setLave(String lave) {
            this.lave = lave;
        }

        public int getSort_num() {
            return sort_num;
        }

        public void setSort_num(int sort_num) {
            this.sort_num = sort_num;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public double getCurrent() {
            return current;
        }

        public void setCurrent(double current) {
            this.current = current;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }
    }
}
