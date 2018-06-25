package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class LifeSuperMarketBean implements Serializable{

    /**
     * passcodeKey : cb1b48a96c9c41139a40fbcf3377c7d1
     * list : [{"id":"eddf8575b2a449bc96a612f94bcbd452","createTime":"2017-10-31 15:37:06","point":"1","title":"无人超市","circleImageUrl":"https://staticfile.tq-service.com/image/LargeSystem/20171031/885b30f8d1e44fc7a67e779621ddd989.png","remark":"","status":"1","type":"SMART_MK","authorityCode":"SMART_MK","modifyTime":"","circleLinkUrl":"#"}]
     */

    private String passcodeKey;
    private List<ListBean> list;

    public String getPasscodeKey() {
        return passcodeKey;
    }

    public void setPasscodeKey(String passcodeKey) {
        this.passcodeKey = passcodeKey;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : eddf8575b2a449bc96a612f94bcbd452
         * createTime : 2017-10-31 15:37:06
         * point : 1
         * title : 无人超市
         * circleImageUrl : https://staticfile.tq-service.com/image/LargeSystem/20171031/885b30f8d1e44fc7a67e779621ddd989.png
         * remark :
         * status : 1
         * type : SMART_MK
         * authorityCode : SMART_MK
         * modifyTime :
         * circleLinkUrl : #
         */

        private String id;
        private String createTime;
        private String point;
        private String title;
        private String circleImageUrl;
        private String remark;
        private String status;
        private String type;
        private String authorityCode;
        private String modifyTime;
        private String circleLinkUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCircleImageUrl() {
            return circleImageUrl;
        }

        public void setCircleImageUrl(String circleImageUrl) {
            this.circleImageUrl = circleImageUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAuthorityCode() {
            return authorityCode;
        }

        public void setAuthorityCode(String authorityCode) {
            this.authorityCode = authorityCode;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getCircleLinkUrl() {
            return circleLinkUrl;
        }

        public void setCircleLinkUrl(String circleLinkUrl) {
            this.circleLinkUrl = circleLinkUrl;
        }
    }
}
