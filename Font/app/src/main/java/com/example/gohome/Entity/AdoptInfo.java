package com.example.gohome.Entity;

import java.io.Serializable;

public class AdoptInfo implements Serializable {
    private int petPhotoId;
    private String petName;
    private String petAge;
    private String petType;
    private int petGender;
    private int vacn; // 疫苗
    private int strl; // 绝育
    private String desc;
    private String area;
    private String time;

    public AdoptInfo(int photoId, String petName,
                     String petType,int petGender, String petAge,
                     String desc, String area, String time) {
        super();
        this.petPhotoId = photoId;
        this.petName = petName;
        this.petType = petType;
        this.petGender = petGender;
        this.petAge = petAge;
        this.desc = desc;
        this.area = area;
        this.time = time;
    }

    public int getPetPhotoId() {
        return petPhotoId;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetAge() { return petAge; }

    public String getPetType() { return petType; }

    public int getGender() { return petGender; }

    public int getVacn() { return vacn; }

    public int getStrl() { return strl; }

    public String getDesc() {
        return desc;
    }

    public String getArea() { return area; }

    public String getTime() { return time; }

    public void setPetPhotoId(int petPhotoId) {
        this.petPhotoId = petPhotoId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}