package com.jinke.community.bean;

import java.util.List;

/**
 * Created by root on 17-8-17.
 */

public class WorkingSortBean {


    /**
     * list : [{"avatar":"http://q.qlogo.cn/qqapp/101365191/A1F70A9E0559B67217F758920E5DEA8E/100","createTime":"2017-08-14 14:45:57.0","id":3,"modifyTime":"2017-08-16 10:43:34.0","rank":1,"step":1001},{"avatar":"http://q.qlogo.cn/qqapp/101364347/5DB4F89871394582B5428FA7D6E19137/40","createTime":"2017-08-14 16:33:53.0","id":5,"rank":2,"step":999},{"avatar":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6zDOOY2J8gbrw743oJbB9Oup07Pvo8PznqJicNYmVJk1Zicic2FicmlYw8pVngRvANM80n4yrtfqtkicQ/0","createTime":"2017-08-14 14:45:45.0","id":2,"rank":3,"step":952}]
     * curUserStep : {"avatar":"http://q.qlogo.cn/qqapp/101365191/A1F70A9E0559B67217F758920E5DEA8E/100","createTime":"2017-08-14 14:45:57.0","id":3,"modifyTime":"2017-08-16 10:43:34.0","rank":1,"step":1001}
     */
    private String countOwner;

    private CurUserStepBean curUserStep;
    private List<ListBean> list;

    public CurUserStepBean getCurUserStep() {
        return curUserStep;
    }

    public void setCurUserStep(CurUserStepBean curUserStep) {
        this.curUserStep = curUserStep;
    }

    public List<ListBean> getList() {
        return list;
    }

    public String getCountOwner() {
        return countOwner;
    }

    public void setCountOwner(String countOwner) {
        this.countOwner = countOwner;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CurUserStepBean {
        /**
         * avatar : http://q.qlogo.cn/qqapp/101365191/A1F70A9E0559B67217F758920E5DEA8E/100
         * createTime : 2017-08-14 14:45:57.0
         * id : 3
         * modifyTime : 2017-08-16 10:43:34.0
         * rank : 1
         * step : 1001
         */

        private String avatar;
        private String nickName;
        private String createTime;
        private int id;
        private String modifyTime;
        private int rank;
        private int step;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }
    }

    public static class ListBean {
        /**
         * avatar : http://q.qlogo.cn/qqapp/101365191/A1F70A9E0559B67217F758920E5DEA8E/100
         * createTime : 2017-08-14 14:45:57.0
         * id : 3
         * modifyTime : 2017-08-16 10:43:34.0
         * rank : 1
         * step : 1001
         */
        private String nickName;
        private String avatar;
        private String createTime;
        private int id;
        private String modifyTime;
        private int rank;
        private int step;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }
    }
}
