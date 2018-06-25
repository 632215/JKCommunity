package com.jinke.community.bean;

import java.util.List;


public class PasswordInfo {


    /**
     * listDate : [{"doorName":"科技门","passWord":"464379"}]
     * total : 0
     */

    private int total;
    public List<ListDateBean> listDate;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

//    public List<ListDateBean> getListDate() {
//        return listDate!=null?listDate:null;
//    }

//    public void setListDate(List<ListDateBean> listDate) {
//        this.listDate = listDate;
//    }

    public static class ListDateBean {
        /**
         * doorName : 科技门
         * passWord : 464379
         */

        private String doorName;
        private String passWord;

        public String getDoorName() {
            return doorName;
        }

        public void setDoorName(String doorName) {
            this.doorName = doorName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
    }
}

