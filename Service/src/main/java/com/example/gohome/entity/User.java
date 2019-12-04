package com.example.gohome.entity;

import java.util.Date;

/*  用户信息类，记录用户的所有个人信息 */
public class User {
    //主键用户ID
    private Integer userId;
    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    //用户手机号
    private String telephone;
    //用户性别
    private String gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "User [userId=" + userId + "userName=" + userName + ", gender=" + gender + ", telephone=" + telephone  +
                "userType= " + userType +  "portrait=" + portrait  + "address=" + address + "userPassword=" + userPassword
        + "created=" + created +"]";
    }

    public static class AdoptMessage {
        private Integer adoptId;

        private String petName;

        private Integer petType;

        private Integer gender;

        private String age;

        private Integer handleId;

        private String pictures;

        private Integer state;

        private String description;

        private String address;

        private Boolean vaccinate;

        private Boolean steriled;

        private Date created;

        public Integer getAdoptId() {
            return adoptId;
        }

        public void setAdoptId(Integer adoptId) {
            this.adoptId = adoptId;
        }

        public String getPetName() {
            return petName;
        }

        public void setPetName(String petName) {
            this.petName = petName == null ? null : petName.trim();
        }

        public Integer getPetType() {
            return petType;
        }

        public void setPetType(Integer petType) {
            this.petType = petType;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age == null ? null : age.trim();
        }

        public Integer getHandleId() {
            return handleId;
        }

        public void setHandleId(Integer handleId) {
            this.handleId = handleId;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures == null ? null : pictures.trim();
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description == null ? null : description.trim();
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address == null ? null : address.trim();
        }

        public Boolean getVaccinate() {
            return vaccinate;
        }

        public void setVaccinate(Boolean vaccinate) {
            this.vaccinate = vaccinate;
        }

        public Boolean getSteriled() {
            return steriled;
        }

        public void setSteriled(Boolean steriled) {
            this.steriled = steriled;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }
    }
}
