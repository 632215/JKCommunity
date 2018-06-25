package com.jinke.community.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-19.
 */

public class PaymentRecordBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * pay_id : 20170524000003943838
         * pay_type : mp_wxpay
         * pay_name : 预存
         * total_money : 0.01
         * pay_time : 1495440796
         */

        private String pay_id;
        private String pay_type;
        private String pay_name;
        private String total_money;
        private String pay_time;

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getPay_time() {
            if (!StringUtils.isEmpty(pay_time)) {
                long time = Long.valueOf(pay_time) * 1000;
                Date date = new Date(time);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                return format.format(date);
            } else {
                return pay_time;
            }
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }
    }
}
