package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-2.
 */

public class CommunityListBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * district_name : 重庆市-江津区
         * community : [{"id":33,"position_lng":106.268559,"name":"江津中央公园城","position_lat":29.320738},{"id":139,"position_lng":106.262903,"name":"江津世界城","position_lat":29.27425}]
         * district_id : 3326
         */

        private String district_name;
        private int district_id;
        private List<CommunityBean> community;

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public List<CommunityBean> getCommunity() {
            return community;
        }

        public void setCommunity(List<CommunityBean> community) {
            this.community = community;
        }

        public static class CommunityBean {
            /**
             * id : 33
             * position_lng : 106.268559
             * name : 江津中央公园城
             * position_lat : 29.320738
             */

            private int id;
            private double position_lng;
            private String name;
            private double position_lat;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getPosition_lng() {
                return position_lng;
            }

            public void setPosition_lng(double position_lng) {
                this.position_lng = position_lng;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPosition_lat() {
                return position_lat;
            }

            public void setPosition_lat(double position_lat) {
                this.position_lat = position_lat;
            }
        }
    }
}
