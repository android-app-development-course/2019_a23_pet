package com.example.gohome.entity;

import java.util.Date;

/*  用户信息类，记录用户的所有个人信息 */
public class UserMessage {

    public static final int USER_TYPE_NORMAL = 0;
    public static final int USER_TYPE_MEMBER = 1;
    public static final int USER_TYPE_ORGANIZER = 2;
    //主键用户ID
    private Integer userId;
    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    //用户手机号
    private String telephone;
    //用户性别
    private Integer gender;
   //用户地址
    private String address;

    //用户类型，0为普通用户，1为组员a，2为地区组织管理人
    private Integer userType;
    //头像
    private String portrait;
    //信息创建日期
    private Date created;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "User [userId=" + userId + "userName=" + userName + ", gender=" + gender + ", telephone=" + telephone  +
                "userType= " + userType +  "portrait=" + portrait  + "address=" + address + "userPassword=" + userPassword
        + "created=" + created +"]";
    }

}
