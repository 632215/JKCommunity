package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-1.
 */

public class HouseBean {


    /**
     * accessToken : ca254c03a649cb0844677eb1a6b0dad9
     * houseList : [{"area":"深南大道科兴路11号","houseName":"8-1-201","name":"万德应","community":"深圳·金科智慧城","role":"业主","esHouseId":"bd1ae26d-2171-4aed-a73e-a77b00b9d452"},{"area":"深南大道科兴路11号","houseName":"科技中心8-1-126","name":"万德应","community":"深圳·金科智慧城","role":"业主","esHouseId":"e87bad81-7a48-4a1c-9d57-b19a8e1ae8a7"}]
     */

    private String accessToken;
    private List<HouseListBean> houseList;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<HouseListBean> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseListBean> houseList) {
        this.houseList = houseList;
    }

    public static class HouseListBean implements Serializable{
        /**
         * area : 深南大道科兴路11号
         * houseName : 8-1-201
         * name : 万德应
         * community : 深圳·金科智慧城
         * role : 业主
         * esHouseId : bd1ae26d-2171-4aed-a73e-a77b00b9d452
         */

        private String area;
        private String houseName;
        private String name;
        private String community;
        private String role;
        private String esHouseId;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getEsHouseId() {
            return esHouseId;
        }

        public void setEsHouseId(String esHouseId) {
            this.esHouseId = esHouseId;
        }
    }
}
