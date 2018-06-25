package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SetDefaultHouseBean implements Serializable {
    /**
     * community_name : 深圳·金科智慧城
     * address : 广东省深圳南山区
     * cityCode : 440300
     * house_id : c8ab8158-7e5e-40f2-997b-f08fd1414d82
     * owners : [{"phone":"17623236005","ownerName":"向建新"}]
     * house_name : 科技中心8-1-140
     * community_id : 38e70023-9bb1-4874-855f-6f46ac61fff8
     * area_num : 200
     */

    private String community_name;
    private String address;
    private String cityCode;
    private String house_id;
    private String house_name;
    private String community_id;
    private String area_num;
    private List<OwnersBean> owners;

    private String identity;
    private String name;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getArea_num() {
        return area_num;
    }

    public void setArea_num(String area_num) {
        this.area_num = area_num;
    }

    public List<OwnersBean> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnersBean> owners) {
        this.owners = owners;
    }

    public static class OwnersBean {
        /**
         * phone : 17623236005
         * ownerName : 向建新
         */

        private String phone;
        private String ownerName;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
    }
}
