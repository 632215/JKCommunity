package com.jinke.community.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/6.
 * 报事列表-对大管家
 */

public class PropertyBean<T> {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean implements Serializable {
        /**
         * id : 229
         * keepId : 1524885191420
         * content : 啰嗦了我了
         * tripartite :
         * projectId :
         * houseNo :
         * houseName :
         * reportNum : JK1524898202409TF
         * type :
         * userId : 83
         * userName : 199****592
         * contactPhone :
         * contactName :
         * image : https://staticfile.tq-service.com/image/LargeSystem/20180428/55bd263e8f144f17bea6efa2046fe7e3.jpg
         * houseId : e3935eac-956e-413f-a824-85b677d46480
         * status : 4
         * createTime : 1524898202
         * isSync : 1
         * syncTime : 1524898450
         * postComment : [{"id":72,"postId":"1524885191420","parentUserId":"","userId":83,"content":"脑子噢噢噢哦哦","score":5,"createTime":1524900464}]
         */

        private String id;
        private String keepId;
        private String content;
        private String tripartite;
        private String projectId;
        private String houseNo;
        private String houseName;
        private String reportNum;
        private String type;
        private String userId;
        private String userName;
        private String contactPhone;
        private String contactName;
        private String image;
        private String houseId;
        private String status;
        private String createTime;
        private String isSync;
        private String syncTime;
        private T postComment;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeepId() {
            return keepId;
        }

        public void setKeepId(String keepId) {
            this.keepId = keepId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTripartite() {
            return tripartite;
        }

        public void setTripartite(String tripartite) {
            this.tripartite = tripartite;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getReportNum() {
            return reportNum;
        }

        public void setReportNum(String reportNum) {
            this.reportNum = reportNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public List<String> getImage() {
            List<String> list = new ArrayList<>();
            if (!StringUtils.isEmpty(image)) {
                String[] pic = image.split(",");
                for (String path : pic) {
                    list.add(path);
                }
            }
            return list;
        }


        public void setImage(String image) {
            this.image = image;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public int getStatus() {
            return Integer.parseInt(status);
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIsSync() {
            return isSync;
        }

        public void setIsSync(String isSync) {
            this.isSync = isSync;
        }

        public String getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(String syncTime) {
            this.syncTime = syncTime;
        }

        public T getPostComment() {
            return postComment;
        }

        public void setPostComment(T postComment) {
            this.postComment = postComment;
        }
    }
}
