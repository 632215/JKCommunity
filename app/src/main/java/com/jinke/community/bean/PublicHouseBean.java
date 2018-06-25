package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-11.
 */

public class PublicHouseBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * community_name : 深圳·金科智慧城
         * address : 广东省深圳南山区
         * house_id : bd1ae26d-2171-4aed-a73e-a77b00b9d452
         * owners : [{"phone":"18315058038","ownerName":"万德应"}]
         * house_name : 8-1-201
         * community_id : 38e70023-9bb1-4874-855f-6f46ac61fff8
         */

        private String community_name;
        private String address;
        private String house_id;
        private String house_name;
        private String community_id;
        private List<OwnersBean> owners;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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

        public List<OwnersBean> getOwners() {
            return owners;
        }

        public void setOwners(List<OwnersBean> owners) {
            this.owners = owners;
        }

        public static class OwnersBean implements Serializable {
            /**
             * phone : 18315058038
             * ownerName : 万德应
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
}
