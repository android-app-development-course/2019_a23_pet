package com.example.gohome.Entity;

import java.util.Date;

public class HelpAppliment {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantTel() {
        return applicantTel;
    }

    public void setApplicantTel(String applicantTel) {
        this.applicantTel = applicantTel;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPetPhotoId() {
        return petPhotoId;
    }

    public void setPetPhotoId(Integer petPhotoId) {
        this.petPhotoId = petPhotoId;
    }
    public Integer getHelpApplimentId() {
        return helpApplimentId;
    }

    public void setHelpApplimentId(Integer helpApplimentId) {
        this.helpApplimentId = helpApplimentId;
    }


    private String date;     //救助申请日期
    private String applicantName;   //救助申请人姓名
    private String applicantTel;     //救助申请人电话
    private String applicantAddress; //救助申请人地址
    private String petName; //宠物姓名
    private String petAge;       //宠物年龄
    private String petType;    //宠物类型
    private boolean petGender;   //宠物性别
    private boolean vaccine ; //疫苗情况
    private boolean sterilization;         //绝育情况
    private String description;     //救助申请描述
    private Integer petPhotoId;      //宠物图片的id
    private Integer helpApplimentId;   //救助申请信息数据库id
    private Integer state;  //申请救助信息状态
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




    public HelpAppliment(String date,String applicantName,String applicantTel, String applicantAddress,
                         String petName, String petAge, String petType, boolean petGender, boolean vaccine,
                         boolean sterilization, String description, Integer petPhotoId,Integer helpApplimentId,
                         Integer state , String resultDescription){

        this.date = date;
        this.applicantName = applicantName;
        this.applicantTel = applicantTel;
        this.applicantAddress = applicantAddress;
        this.petName =petName;
        this.petAge = petAge;
        this.petType = petType;
        this.petGender = petGender;
        this.vaccine = vaccine;
        this.sterilization = sterilization;
        this.description = description;
        this.petPhotoId = petPhotoId;
        this.helpApplimentId = helpApplimentId;
        this.state = state;
        this.resultDescription = resultDescription;
    }
}
