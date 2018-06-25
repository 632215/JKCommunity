package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-2.
 */

public class CommunityGPSBean implements Serializable {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 29
         * name : 10年城
         * position_lat : 29.61367
         * position_lng : 106.48944
         * communityId : ecbdc732-baf2-4c63-aa42-b50b0cdeac5e
         * distance : 762
         */

        private int id;
        private String name;
        private double position_lat;
        private double position_lng;
        private String communityId;
        private int distance;

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

        public double getPosition_lat() {
            return position_lat;
        }

        public void setPosition_lat(double position_lat) {
            this.position_lat = position_lat;
        }

        public double getPosition_lng() {
            return position_lng;
        }

        public void setPosition_lng(double position_lng) {
            this.position_lng = position_lng;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
