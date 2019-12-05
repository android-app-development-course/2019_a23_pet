package com.example.gohome.Entity;

import android.view.View;

import java.io.Serializable;

public class AdoptInfo implements Serializable {
    private int photos;
    private String petName;
    private String age;
    private String petType;
    private int gender;
    private int vaccinate; // 疫苗
    private int steriled; // 绝育
    private String description;
    private String handleId;
    private String address;
    private String created;

    private View.OnClickListener requestBtnClickListener;

    public AdoptInfo(int photoId, String petName,
                     String petType, int gender, String age,
                     String description, String address, String created,
                     int vaccinate, int steriled, String handleId) {
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

    public int getVaccinate() { return vaccinate; }

    public int getSteriled() { return steriled; }

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