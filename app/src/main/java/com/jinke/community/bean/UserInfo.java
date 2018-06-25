package com.jinke.community.bean;

/**
 * Created by root on 17-8-11.
 */

public class UserInfo {
    /**
     * uid : 26
     * identity : 业主
     * phone : 18315058038
     * sex : 1
     * nickName : 了呵呵方便
     * name : 万德应
     * avatar : http://q.qlogo.cn/qqapp/101365191/A1F70A9E0559B67217F758920E5DEA8E/100
     */

    private int uid;
    private String phone;
    private String sex;
    private String nickName;
//    private String name;
//    private String identity;
    private String avatar;
    private String isSuccess;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

//    public String getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(String identity) {
//        this.identity = identity;
//    }
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
}
