package com.jinke.community.bean.acachebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-16.
 */

public class LifeTopBannerBean implements Serializable{


    /**
     * title : 分类三三
     * code : A08
     * list : [{"id":"88cd444c7998471a9a8c6f29169eef23","type":"A08","typeName":null,"title":"3","circleImageUrl":"","circleLinkUrl":"2","createTime":null,"modifyTime":null,"remark":"2","point":"3"}]
     */

    private String title;
    private String code;
    private List<ListBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 88cd444c7998471a9a8c6f29169eef23
         * type : A08
         * typeName : null
         * title : 3
         * circleImageUrl :
         * circleLinkUrl : 2
         * createTime : null
         * modifyTime : null
         * remark : 2
         * point : 3
         */

        private boolean isVisibility=false;
        private String id;
        private String type;
        private Object typeName;
        private String title;
        private String circleImageUrl;
        private String circleLinkUrl;
        private Object createTime;
        private Object modifyTime;
        private String remark;
        private String point;

        public boolean isVisibility() {
            return isVisibility;
        }

        public void setVisibility(boolean visibility) {
            isVisibility = visibility;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
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

        public String getCircleLinkUrl() {
            return circleLinkUrl;
        }

        public void setCircleLinkUrl(String circleLinkUrl) {
            this.circleLinkUrl = circleLinkUrl;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }
    }
}
