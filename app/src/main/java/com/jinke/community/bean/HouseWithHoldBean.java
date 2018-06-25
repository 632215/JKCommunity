package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HouseWithHoldBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * sign_status : {"ali_sign_status":0,"wx_sign_status":0}
         * community_name : 深圳·金科智慧城
         * address : 广东省深圳南山区
         * grants : [{"grantId":261,"grantPhone":"13800138000","relation":"2","grantName":"重庆"},{"grantId":263,"grantPhone":"15223084077","relation":"2","grantName":"哟"}]
         * house_id : c8ab8158-7e5e-40f2-997b-f08fd1414d82
         * owners : [{"id":"10138","houseId":"c8ab8158-7e5e-40f2-997b-f08fd1414d82","ownerName":"魏爽","phone":"15223084076","modifyTime":1511849912},{"id":"10139","houseId":"c8ab8158-7e5e-40f2-997b-f08fd1414d82","ownerName":"向建新","phone":"17623236005","modifyTime":1511849912}]
         * community_id : 38e70023-9bb1-4874-855f-6f46ac61fff8
         * house_name : 科技中心8-1-140
         * isgrant : 1
         * dft_house : 1
         */

        private SignStatusBean sign_status;
        private String community_name;
        private String address;
        private String house_id;
        private String community_id;
        private String house_name;
        private int isgrant;
        private int dft_house;
        private List<GrantsBean> grants;
        private List<OwnersBean> owners;

        public SignStatusBean getSign_status() {
            return sign_status;
        }

        public void setSign_status(SignStatusBean sign_status) {
            this.sign_status = sign_status;
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

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getHouse_name() {
            return house_name;
        }

        public void setHouse_name(String house_name) {
            this.house_name = house_name;
        }

        public int getIsgrant() {
            return isgrant;
        }

        public void setIsgrant(int isgrant) {
            this.isgrant = isgrant;
        }

        public int getDft_house() {
            return dft_house;
        }

        public void setDft_house(int dft_house) {
            this.dft_house = dft_house;
        }

        public List<GrantsBean> getGrants() {
            return grants;
        }

        public void setGrants(List<GrantsBean> grants) {
            this.grants = grants;
        }

        public List<OwnersBean> getOwners() {
            return owners;
        }

        public void setOwners(List<OwnersBean> owners) {
            this.owners = owners;
        }

        public static class SignStatusBean implements Serializable {
            /**
             * ali_sign_status : 0
             * wx_sign_status : 0
             */

            private int ali_sign_status;
            private int wx_sign_status;
            private int user_sign_status;

            public int getAli_sign_status() {
                return ali_sign_status;
            }

            public void setAli_sign_status(int ali_sign_status) {
                this.ali_sign_status = ali_sign_status;
            }

            public int getWx_sign_status() {
                return wx_sign_status;
            }

            public void setWx_sign_status(int wx_sign_status) {
                this.wx_sign_status = wx_sign_status;
            }

            public int getUser_sign_status() {
                return user_sign_status;
            }

            public void setUser_sign_status(int user_sign_status) {
                this.user_sign_status = user_sign_status;
            }
        }

        public static class GrantsBean implements Serializable {
            /**
             * grantId : 261
             * grantPhone : 13800138000
             * relation : 2
             * grantName : 重庆
             */

            private int grantId;
            private String grantPhone;
            private String relation;
            private String grantName;

            public int getGrantId() {
                return grantId;
            }

            public void setGrantId(int grantId) {
                this.grantId = grantId;
            }

            public String getGrantPhone() {
                return grantPhone;
            }

            public void setGrantPhone(String grantPhone) {
                this.grantPhone = grantPhone;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getGrantName() {
                return grantName;
            }

            public void setGrantName(String grantName) {
                this.grantName = grantName;
            }
        }

        public static class OwnersBean implements Serializable {
            /**
             * id : 10138
             * houseId : c8ab8158-7e5e-40f2-997b-f08fd1414d82
             * ownerName : 魏爽
             * phone : 15223084076
             * modifyTime : 1511849912
             */

            private String id;
            private String houseId;
            private String ownerName;
            private String phone;
            private int modifyTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(int modifyTime) {
                this.modifyTime = modifyTime;
            }
        }
    }
}
