package com.example.gohome.User.Model;

import java.io.Serializable;

public class AdoptInfo implements Serializable {
    private int photoId;
    private String petName;
    private String desc;

    public AdoptInfo(int photoId, String petName, String desc){
        super();
        this.photoId = photoId;
        this.petName = petName;
        this.desc = desc;
    }

    public int getPhotoId() { return photoId; }
    public String getPetName() { return petName; }
    public String getDesc() { return desc; }
}
