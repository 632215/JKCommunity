package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-7.
 */

public class AuthorizationBean {

    private String houseId;
    private List<Grants> grants;
    private String deleteIds;

    public String getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(String deleteIds) {
        this.deleteIds = deleteIds;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public List<Grants> getGrants() {
        return grants;
    }

    public void setGrants(List<Grants> grants) {
        this.grants = grants;
    }

    public static class Grants {
        private String grantId;
        private String grantName;
        private String grantPhone;
        private String relation;

        public String getGrantId() {
            return grantId;
        }

        public void setGrantId(String grantId) {
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


}
