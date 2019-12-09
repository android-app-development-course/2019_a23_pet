package com.example.gohome.Entity;

import android.view.View;

import java.io.Serializable;

public class AdoptInfo implements Serializable {
    public void setPhotos(int photos) {
        this.photos = photos;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setVaccinate(boolean vaccinate) {
        this.vaccinate = vaccinate;
    }

    public void setSteriled(boolean steriled) {
        this.steriled = steriled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    //用于后端测试
    private String pictures;
    private int photos;
    private String petName;
    private String age;
    private String petType;
    private int gender;
    private boolean vaccinate; // 疫苗
    private boolean steriled; // 绝育
    private String description;
    private String handleId;
    private String address;
    private String created;

    private View.OnClickListener requestBtnClickListener;

    public AdoptInfo(){

    }

    public AdoptInfo(int photoId, String petName,
                     String petType, int gender, String age,
                     String description, String address, String created,
                     boolean vaccinate, boolean steriled, String handleId) {
        super();
        this.photos = photoId;
        this.petName = petName;
        this.petType = petType;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.vaccinate = vaccinate;
        this.steriled = steriled;
        this.address = address;
        this.handleId = handleId;
        this.created = created;
    }

    public int getPhotos() {
        return photos;
    }

    public String getPetName() {
        return petName;
    }

    public String getAge() { return age; }

    public String getPetType() { return petType; }

    public int getGender() { return gender; }

    public boolean getVaccinate() { return vaccinate; }

    public boolean getSteriled() { return steriled; }

    public String getDescription() {
        return description;
    }

    public String getAddress() { return address; }

    public String getHandleId() { return handleId; }

    public String getCreated() { return created; }

    public View.OnClickListener getRequestBtnClickListener() { return requestBtnClickListener;}

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

}