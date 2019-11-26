package com.example.gohome.Entity;
import java.util.Date;

public class JoinAppliment {

    private Integer applimentId, userId, areaId, state, operationState, gender;
    private Date applyDate, operatorTime, created;
    private String applyName, telephone, description, address, username, protrait;
    private int photoId;

    public JoinAppliment(Integer gender, Date applyDate, String telephone, String applyName, String description, String address, String username, Integer photoId) {
        this.gender = gender;
        this.applyDate = applyDate;
        this.applyName = applyName;
        this.telephone = telephone;
        this.description = description;
        this.address = address;
        this.username = username;
        this.photoId = photoId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public Integer getApplimentId() {
        return applimentId;
    }

    public void setApplimentId(Integer applimentId) {
        this.applimentId = applimentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOperationState() {
        return operationState;
    }

    public void setOperationState(Integer operationState) {
        this.operationState = operationState;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProtrait() {
        return protrait;
    }

    public void setProtrait(String protrait) {
        this.protrait = protrait;
    }

}
