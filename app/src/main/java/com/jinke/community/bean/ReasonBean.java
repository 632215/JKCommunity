package com.jinke.community.bean;
import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/5/19
 */

public class ReasonBean {

    /**
     * listDate : [{"id":"1","name":"快递送件"},{"id":"2","name":"装修放行"},{"id":"3","name":"朋友来访"},{"id":"4","name":"上门搬家"},{"id":"5","name":"中介看房"},{"id":"6","name":"其他"}]
     * total : 6
     */

    private int total;
    private List<ListDateBean> listDate;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListDateBean> getListDate() {
        return listDate;
    }

    public void setListDate(List<ListDateBean> listDate) {
        this.listDate = listDate;
    }

    public static class ListDateBean {
        /**
         * id : 1
         * name : 快递送件
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

