package com.example.gohome.Entity;

import java.util.Date;

public class AdoptAppliment {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    private String applyName; //用户申请姓名
    private String telephone; //电话号码
    private String address; //用户住址

    private String job; //用户职业
    private String description; //描述
    private String petName;  //申请领养的宠物姓名
    private String petAge;   //申请领养宠物的年龄
    private String petType;  //宠物类型
    private boolean petGender;  //宠物性别
    private boolean vaccine; // 是否注射疫苗
    private boolean sterilization ; // 绝育情况
    private Integer petPhotoId;      //宠物图片的id
    private Date date;  //申请日期
    private Integer adoptApplimentId;    //领养申请信息数据库ID
    private Integer state;  //申请领养信息状态
    private String resultDescription; //信息处理结果反馈

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Integer getAdoptApplimentId() {
        return adoptApplimentId;
    }

    public void setAdoptApplimentId(Integer adoptApplimentId) {
        this.adoptApplimentId = adoptApplimentId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPetPhotoId(Integer petPhotoId) {
        this.petPhotoId = petPhotoId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public boolean getPetGender() {
        return petGender;
    }

    public void setPetGender(boolean petGender) {
        this.petGender = petGender;
    }

    public boolean getVaccine() {
        return vaccine;
    }

    public void setVaccine(boolean vaccine) {
        this.vaccine = vaccine;
    }

    public boolean getSterilization() {
        return sterilization;
    }

    public void setSterilization(boolean sterilization) {
        this.sterilization = sterilization;
    }

    public int getPetPhotoId() {
        return petPhotoId;
    }

    public void setPetPhotoId(int petPhotoId) {
        this.petPhotoId = petPhotoId;
    }

    public AdoptAppliment(String applyName,
        String telephone,String address,String description,String petName ,String petAge,
                          String petType,boolean petGender,boolean vaccine,boolean sterilization,
                          Integer petPhotoId,Date date,String job, Integer adoptApplimentId, Integer state, String resultDescription){
        this.address = address;
        this.applyName = applyName;
        this.description = description;
        this.telephone = telephone;
        this.petName = petName;
        this.petAge = petAge;
        this.petType = petType;
        this.petGender = petGender;
        this.vaccine = vaccine;
        this.sterilization = sterilization;
        this.petPhotoId = petPhotoId;
        this.date = date;
        this.job = job;
        this.adoptApplimentId = adoptApplimentId;
        this.state = state;
        this.resultDescription = resultDescription;
    }

}
