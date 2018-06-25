package com.jinke.community.bean;

import java.util.List;

/**
 * 用户红点分组
 * 分组: keeper管家 life生活 circle圈子 person我
 * <p>
 * Created by Administrator on 2018/3/14.
 */

public class RedCircleGroupBean {

    String person;//个人
    String personInfo;//我的资料（标签）
    String personInfoCode;
    String personOrder;//我的订单
    String personOrderCode;
    String personBreak;//我的报事
    String personBreakCode;
    String personSpread;//app推广
    String personSpreadCode;
    String houseKeeper;//管家
    String houseKeeperCall;//呼叫中心
    String houseKeeperCalloCode;

    private List<ListBean> list;

    public RedCircleGroupBean() {
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonOrder() {
        return personOrder;
    }

    public void setPersonOrder(String personOrder) {
        this.personOrder = personOrder;
    }

    public String getPersonBreak() {
        return personBreak;
    }

    public void setPersonBreak(String personBreak) {
        this.personBreak = personBreak;
    }

    public String getPersonSpread() {
        return personSpread;
    }

    public void setPersonSpread(String personSpread) {
        this.personSpread = personSpread;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(String personInfo) {
        this.personInfo = personInfo;
    }

    public String getHouseKeeper() {
        return houseKeeper;
    }

    public void setHouseKeeper(String houseKeeper) {
        this.houseKeeper = houseKeeper;
    }

    public String getHouseKeeperCall() {
        return houseKeeperCall;
    }

    public void setHouseKeeperCall(String houseKeeperCall) {
        this.houseKeeperCall = houseKeeperCall;
    }

    public String getPersonInfoCode() {
        return personInfoCode;
    }

    public void setPersonInfoCode(String personInfoCode) {
        this.personInfoCode = personInfoCode;
    }

    public String getPersonOrderCode() {
        return personOrderCode;
    }

    public void setPersonOrderCode(String personOrderCode) {
        this.personOrderCode = personOrderCode;
    }

    public String getPersonBreakCode() {
        return personBreakCode;
    }

    public void setPersonBreakCode(String personBreakCode) {
        this.personBreakCode = personBreakCode;
    }

    public String getPersonSpreadCode() {
        return personSpreadCode;
    }

    public void setPersonSpreadCode(String personSpreadCode) {
        this.personSpreadCode = personSpreadCode;
    }

    public String getHouseKeeperCalloCode() {
        return houseKeeperCalloCode;
    }

    public void setHouseKeeperCalloCode(String houseKeeperCalloCode) {
        this.houseKeeperCalloCode = houseKeeperCalloCode;
    }

    public static class ListBean {
        /**
         * id : 1
         * pointId : 1
         * userId : 70
         * status : 1
         * createTime :
         * pointCode : MENU_MY
         * pointName : 首页—我
         */

        private int id;
        private String pointId;
        private int userId;
        private int status;
        private String createTime;
        private String pointCode;
        private String pointName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPointCode() {
            return pointCode;
        }

        public void setPointCode(String pointCode) {
            this.pointCode = pointCode;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }
    }
}
