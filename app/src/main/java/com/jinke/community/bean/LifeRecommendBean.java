package com.jinke.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17-8-16.
 */

public class LifeRecommendBean implements Serializable{


    private List<ListBeanX> list;

    public List<ListBeanX> getList() {
        return list;
    }

    public void setList(List<ListBeanX> list) {
        this.list = list;
    }

    public static class ListBeanX implements Serializable{
        /**
         * title : 蔬果榜
         * code : A07
         * list : [{"id":"","title":"果蔬排行","point":"-1","circleImageUrl":"http://www.easyicon.net/api/resizeApi.php?id=1108349&size=72","circleLinkUrl":"http://www.easyicon.net/1108349-strawberry_Fruit_icon.html","type":"","typeName":"","remark":"备注21","createTime":"","modifyTime":""},{"id":"","title":"的法第三方第三方","point":"0","circleImageUrl":"https://staticfile.tq-service.com/image/LargeSystem/20170831/1150fcbc36ba47b3a3e5e849cdd747dc.jpg","circleLinkUrl":"baidu.com","type":"","typeName":"","remark":"东方闪电a","createTime":"","modifyTime":""},{"id":"","title":"果蔬排行","point":"2","circleImageUrl":"http://www.easyicon.net/api/resizeApi.php?id=1108348&size=72","circleLinkUrl":"http://www.easyicon.net/1108348-pineapple_Fruit_icon.html","type":"","typeName":"","remark":"备注22","createTime":"","modifyTime":""},{"id":"","title":"果蔬排行","point":"3","circleImageUrl":"http://www.easyicon.net/api/resizeApi.php?id=1129384&size=72","circleLinkUrl":"http://www.easyicon.net/1129384-Cabbage_icon.html","type":"","typeName":"","remark":"备注23","createTime":"","modifyTime":""}]
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
             * id :
             * title : 果蔬排行
             * point : -1
             * circleImageUrl : http://www.easyicon.net/api/resizeApi.php?id=1108349&size=72
             * circleLinkUrl : http://www.easyicon.net/1108349-strawberry_Fruit_icon.html
             * type :
             * typeName :
             * remark : 备注21
             * createTime :
             * modifyTime :
             */

            private String id;
            private String title;
            private String point;
            private String circleImageUrl;
            private String circleLinkUrl;
            private String type;
            private String typeName;
            private String remark;
            private String createTime;
            private String modifyTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }
        }
    }
}
