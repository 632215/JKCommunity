package com.jinke.community.bean;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/11.
 */

public class KeepPropertyBean {
    /**
     * id : 10
     * keepId : 1512546641827
     * title :
     * content : asdasdas
     * userId : 1566
     * image :
     * userName : asdsa
     * phone : 13800138000
     * houseId : c8ab8158-7e5e-40f2-997b-f08fd1414d82
     * status : 1
     * createTime : 1512548498
     * isSync :
     * syncTime :
     * postComment : [{"id":1,"postId":"10","parentUserId":"","userId":133740,"content":"爱爱爱","score":1,"createTime":""}]
     */

    private String id;
    private String keepId;
    private String title;
    private String content;
    private int userId;
    private String image;
    private String userName;
    private String phone;
    private String houseId;
    private String status;
    private int createTime;
    private String isSync;
    private String syncTime;
    private String houseName;

    private List<PostCommentBean> postComment;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
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

    public List<PostCommentBean> getPostComment() {
        return postComment;
    }

    public void setPostComment(List<PostCommentBean> postComment) {
        this.postComment = postComment;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public static class PostCommentBean {
        /**
         * id : 1
         * postId : 10
         * parentUserId :
         * userId : 133740
         * content : 爱爱爱
         * score : 1
         * createTime :
         */

        private int id;
        private String postId;
        private String parentUserId;
        private int userId;
        private String content;
        private int score;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getParentUserId() {
            return parentUserId;
        }

        public void setParentUserId(String parentUserId) {
            this.parentUserId = parentUserId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
