package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-18.
 */

public class ArrearsListBean implements Serializable {

    /**
     * list : [{"detail":[{"fee":"0.01","chargeId":"09412948-9fcc-42ce-af68-1d06d9b26627","item":"洋房物业服务费"}],"monthMoney":"0.01","month":"2017-07"},{"detail":[{"fee":"0.01","chargeId":"6d14b150-57a7-4bfe-bc84-9d00aa9e9fe2","item":"车位服务费"},{"fee":"0.01","chargeId":"98a1a01a-211c-43b8-be31-a7d1012d5331","item":"猫眼门铃费"}],"monthMoney":"0.02","month":"2017-08"}]
     * total_money : 0.03
     */

    private String total_money;
    private List<ListBean> list;

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * detail : [{"fee":"0.01","chargeId":"09412948-9fcc-42ce-af68-1d06d9b26627","item":"洋房物业服务费"}]
         * monthMoney : 0.01
         * month : 2017-07
         */

        private String monthMoney;
        private String month;
        private List<DetailBean> detail;

        public String getMonthMoney() {
            return monthMoney;
        }

        public void setMonthMoney(String monthMoney) {
            this.monthMoney = monthMoney;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean implements Serializable {
            /**
             * fee : 0.01
             * chargeId : 09412948-9fcc-42ce-af68-1d06d9b26627
             * item : 洋房物业服务费
             */

            private String fee;
            private String chargeId;
            private String item;
            private String costType;

            public String getCostType() {
                return costType;
            }

            public void setCostType(String costType) {
                this.costType = costType;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getChargeId() {
                return chargeId;
            }

            public void setChargeId(String chargeId) {
                this.chargeId = chargeId;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }
    }
}
