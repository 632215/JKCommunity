package com.jinke.community.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class PropertyProgressBean {

    /**
     * listObj : [{"telePhone":"19923015925","createTime":"2018-04-28 09:13:33","remark":"就考试西宁市","nodeName":"问题发起","grade":"","userName":"199****592"},{"telePhone":"无","createTime":"2018-04-28 09:14:34","remark":"系统任务分配成功","nodeName":"系统指派","grade":"","userName":"系统一分钟默认处理"},{"telePhone":"qq02","createTime":"2018-04-28 09:18:27","remark":"wqqweqwe","nodeName":"处理问题","grade":"","userName":"韩斌02"}]
     * ifEffectiveId : 有效报事
     * serviceCost : 0
     * isState : 10
     * serviceType : 不收费
     */

    private String ifEffectiveId;
    private String serviceCost;
    private String isState;
    private String serviceType;
    private List<ListObjBean> listObj;

    public String getIfEffectiveId() {
        return ifEffectiveId;
    }

    public void setIfEffectiveId(String ifEffectiveId) {
        this.ifEffectiveId = ifEffectiveId;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getIsState() {
        return isState;
    }

    public void setIsState(String isState) {
        this.isState = isState;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean {
        /**
         * telePhone : 19923015925
         * createTime : 2018-04-28 09:13:33
         * remark : 就考试西宁市
         * nodeName : 问题发起
         * grade :
         * userName : 199****592
         */

        private String telePhone;
        private String createTime;
        private String remark;
        private String nodeName;
        private String grade;
        private String userName;

        public String getTelePhone() {
            return telePhone;
        }

        public void setTelePhone(String telePhone) {
            this.telePhone = telePhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
