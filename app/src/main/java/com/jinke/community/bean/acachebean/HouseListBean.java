package com.jinke.community.bean.acachebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-4.
 * <p>
 * 房屋列表缓存
 */

public class HouseListBean implements Serializable {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * area_id : 38e70023-9bb1-4874-855f-6f46ac61fff8
         * default : 0
         * address : 广东省深圳南山区
         * area_name : 深圳·金科智慧城
         * grants : []
         * house_id : bd1ae26d-2171-4aed-a73e-a77b00b9d452
         * owners : [{"owner_ID":"09560a62-678f-4ca7-a3a8-a77b00be45b2","owner_NAME":"万德应","sex":"男","contact":"18315058038","customer_LEVEL":"普通客户"}]
         * house_name : 8-1-201
         * isgrant : 1  是否可以授权,1-可以，0-不可以
         * houseStatus : 未售、已售
         */

        private boolean isSelect = false;
        private String community_name;
        private Uid uid;
        private String area_id;
        private int dft_house;
        private String address;
        private String area_name;
        private String house_id;
        private String house_name;
        private String community_id;
        private String houseStatus;
        private int isgrant;
        private List<Grants> grants;
        private List<OwnersBean> owners;

        public Uid getUid() {
            return uid;
        }

        public void setUid(Uid uid) {
            this.uid = uid;
        }

        public String getHouseStatus() {
            return houseStatus;
        }

        public void setHouseStatus(String houseStatus) {
            this.houseStatus = houseStatus;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getDft_house() {
            return dft_house;
        }

        public void setDft_house(int dft_house) {
            this.dft_house = dft_house;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
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

        public int getIsgrant() {
            return isgrant;
        }

        public void setIsgrant(int isgrant) {
            this.isgrant = isgrant;
        }

        public List<Grants> getGrants() {
            return grants;
        }

        public void setGrants(List<Grants> grants) {
            this.grants = grants;
        }

        public List<OwnersBean> getOwners() {
            return owners;
        }

        public void setOwners(List<OwnersBean> owners) {
            this.owners = owners;
        }

        public static class Grants implements Serializable {
            private int grantId;

            private String grantName;

            private String grantPhone;

            private String relation;

            public int getGrantId() {
                return grantId;
            }

            public void setGrantId(int grantId) {
                this.grantId = grantId;
            }

            public String getGrantName() {
                return grantName;
            }

            public void setGrantName(String grantName) {
                this.grantName = grantName;
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
        }


        public static class OwnersBean implements Serializable {
            /**
             * owner_ID : 09560a62-678f-4ca7-a3a8-a77b00be45b2
             * owner_NAME : 万德应
             * sex : 男
             * contact : 18315058038
             * customer_LEVEL : 普通客户
             */

            private String owner_ID;
            private String owner_NAME;
            private String sex;
            private String contact;
            private String customer_LEVEL;
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

            public String getOwner_ID() {
                return owner_ID;
            }

            public void setOwner_ID(String owner_ID) {
                this.owner_ID = owner_ID;
            }

            public String getOwner_NAME() {
                return owner_NAME;
            }

            public void setOwner_NAME(String owner_NAME) {
                this.owner_NAME = owner_NAME;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getCustomer_LEVEL() {
                return customer_LEVEL;
            }

            public void setCustomer_LEVEL(String customer_LEVEL) {
                this.customer_LEVEL = customer_LEVEL;
            }
        }

        public static class Uid implements Serializable{

            /**
             * id : 7
             * account : 15223084076
             * houseId : c8ab8158-7e5e-40f2-997b-f08fd1414d82
             * appid : 78967886
             * cno : 198
             * bno : 127
             * ano : 254
             * hno : 4170
             * uid : 2
             */

            private String id;
            private String account;
            private String houseId;
            private String appid;
            private String cno;
            private String bno;
            private String ano;
            private String hno;
            private String uid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getCno() {
                return cno;
            }

            public void setCno(String cno) {
                this.cno = cno;
            }

            public String getBno() {
                return bno;
            }

            public void setBno(String bno) {
                this.bno = bno;
            }

            public String getAno() {
                return ano;
            }

            public void setAno(String ano) {
                this.ano = ano;
            }

            public String getHno() {
                return hno;
            }

            public void setHno(String hno) {
                this.hno = hno;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }
}
