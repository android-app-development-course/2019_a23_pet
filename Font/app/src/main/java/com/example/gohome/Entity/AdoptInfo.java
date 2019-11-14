package com.example.gohome.Entity;

import java.io.Serializable;

public class AdoptInfo implements Serializable {
    private int photoId;
    private String petName;
    private String desc;

    public AdoptInfo(int photoId, String petName, String desc) {
        super();
        this.photoId = photoId;
        this.petName = petName;
        this.desc = desc;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getPetName() {
        return petName;
    }

    public String getDesc() {
        return desc;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}