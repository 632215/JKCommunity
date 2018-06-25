package com.jinke.community.bean;

import java.text.DecimalFormat;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-19.
 */

public class PrepaidExpensesBean {


    /**
     * pre_money : 1.0
     * list : [{"amount":"1.00","prepayName":"物业服务费","prepayId":"c1178e0f-0257-4121-91bc-a63700a7572b"},{"amount":"0","prepayName":"水电公摊费","prepayId":"b31ec8f6-6677-4d40-91db-a63700a84bfb"},{"amount":"0","prepayName":"车位管理费","prepayId":"caaf92f3-75ff-45b1-ac47-a63700c22e97"}]
     */

    private int point_num;
    private String pre_money;
    private List<ListBean> list;

    public int getPoint_num() {
        return point_num;
    }

    public void setPoint_num(int point_num) {
        this.point_num = point_num;
    }

    public String getPre_money() {
        if (!StringUtils.isEmpty(pre_money)) {
            double money = Double.valueOf(pre_money);
            DecimalFormat df = new DecimalFormat("######0.00");
            return df.format(money);
        }
        return pre_money;
    }

    public void setPre_money(String pre_money) {
        this.pre_money = pre_money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 1.00
         * prepayName : 物业服务费
         * prepayId : c1178e0f-0257-4121-91bc-a63700a7572b
         */

        private String amount;
        private String prepayName;
        private String prepayId;
        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPrepayName() {
            return prepayName;
        }

        public void setPrepayName(String prepayName) {
            this.prepayName = prepayName;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }
    }
}
