package com.example.gohome.entity;

import java.util.Date;

public class AdoptMessage {
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


    @Override
    public String toString() {
        return "AdoptMessage [adoptId=" + adoptId + ", handleId=" + handleId + ",created=" + created+  ", petName="
                + petName + ", address=" + address + ", description=" + description + ", gender=" + gender + ", vaccinate=" + vaccinate  +
                ", sterilize= " + steriled + ", age=" + age  + ",state=" + state + "pictures=" + pictures + "]";
    }

}