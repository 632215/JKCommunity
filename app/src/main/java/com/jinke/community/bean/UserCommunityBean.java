package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class UserCommunityBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 198
         * name : 深圳·金科智慧城
         * nameAlias : 智慧城
         * tel :
         * telService1 : 035216351465
         * telService2 : 035216351465
         * tel24h : 035216351465
         * telMaintenance : 035216351465
         * provinceId : 440000
         * provinceName : 广东省
         * cityId : 77
         * cityName : 深圳
         * district : {"id":707,"name":"南山区","level":3,"fangCode":"440300","usetype":false,"upid":77,"displayorder":0,"weatherCode":"CN101280604"}
         * districtName : 南山区
         * companyId : 99
         * companyName : 虚拟公司
         * address : 广东省深圳南山区金科大道
         * positionLat : 113.951289
         * positionLng : 22.546551
         * builder :
         * plotRatio : 0
         * greenRatio : 0
         * buildingAge : 1900-01-01
         * fangPrice : 20000
         * anjukePrice : 25000
         * areaTotal : 0
         * buildingTotal : 0
         * houseTotal : 0
         * parkTotal : 0
         * esProjectId : 38e70023-9bb1-4874-855f-6f46ac61fff8
         * esProjectName : 深圳·金科智慧城
         */

        private boolean isSelect;
        private int id;
        private String name;
        private String nameAlias;
        private String tel;
        private String telService1;
        private String telService2;
        private String tel24h;
        private String telMaintenance;
        private String provinceName;
        private String districtName;
        private String companyName;
        private String address;
        private String builder;
        private String buildingAge;
        private String esProjectId;
        private String esProjectName;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameAlias() {
            return nameAlias;
        }

        public void setNameAlias(String nameAlias) {
            this.nameAlias = nameAlias;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTelService1() {
            return telService1;
        }

        public void setTelService1(String telService1) {
            this.telService1 = telService1;
        }

        public String getTelService2() {
            return telService2;
        }

        public void setTelService2(String telService2) {
            this.telService2 = telService2;
        }

        public String getTel24h() {
            return tel24h;
        }

        public void setTel24h(String tel24h) {
            this.tel24h = tel24h;
        }

        public String getTelMaintenance() {
            return telMaintenance;
        }

        public void setTelMaintenance(String telMaintenance) {
            this.telMaintenance = telMaintenance;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBuilder() {
            return builder;
        }

        public void setBuilder(String builder) {
            this.builder = builder;
        }

        public String getBuildingAge() {
            return buildingAge;
        }

        public void setBuildingAge(String buildingAge) {
            this.buildingAge = buildingAge;
        }

        public String getEsProjectId() {
            return esProjectId;
        }

        public void setEsProjectId(String esProjectId) {
            this.esProjectId = esProjectId;
        }

        public String getEsProjectName() {
            return esProjectName;
        }

        public void setEsProjectName(String esProjectName) {
            this.esProjectName = esProjectName;
        }
    }
}
