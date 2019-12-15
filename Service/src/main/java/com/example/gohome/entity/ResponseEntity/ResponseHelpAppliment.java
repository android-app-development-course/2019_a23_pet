package com.example.gohome.entity.ResponseEntity;

public class ResponseHelpAppliment {


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApplimentId() {
        return applimentId;
    }

    public void setApplimentId(Integer applimentId) {
        this.applimentId = applimentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Integer getPetType() {
        return petType;
    }

    public void setPetType(Integer petType) {
        this.petType = petType;
    }

    public boolean isPetGender() {
        return petGender;
    }

    public void setPetGender(boolean petGender) {
        this.petGender = petGender;
    }

    public boolean isVaccine() {
        return vaccine;
    }

    public void setVaccine(boolean vaccine) {
        this.vaccine = vaccine;
    }

    public boolean isSterilization() {
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

    public String getPetPhotoId() {
        return petPhotoId;
    }

    public void setPetPhotoId(String petPhotoId) {
        this.petPhotoId = petPhotoId;
    }


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


    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getInforId() {
        return inforId;
    }

    public void setInforId(Integer inforId) {
        this.inforId = inforId;
    }

    private Integer userId; //救助申请人ID
    private Integer applimentId; //本救助申请的ID
    private Integer handleId;     //对接人ID
    private String address; //申请人的地址
    private String date;     //救助申请日期
    private String applicantName;   //救助申请人姓名
    private String applicantTel;     //救助申请人电话
    private String petName; //宠物姓名
    private String petAge;       //宠物年龄
    private Integer petType;    //宠物类型
    private boolean petGender;   //宠物性别
    private boolean vaccine; //疫苗情况
    private boolean sterilization;         //绝育情况
    private String description;     //救助申请描述
    private String petPhotoId;      //宠物图片的id
    private Integer state;  //申请救助信息状态
    private String resultDescription; //信息处理结果反馈
    private Integer inforId;



    public ResponseHelpAppliment() {
    }

    public ResponseHelpAppliment(Integer inforId,Integer handleId,Integer userId, Integer applimentId, String address, String date, String applicantName, String applicantTel,
                                 String petName, String petAge, Integer petType, boolean petGender, boolean vaccine, boolean sterilization, String description, String resultDescription,
                                 String petPhotoId, Integer state) {
        this.inforId = inforId;
        this.handleId = handleId;
        this.userId = userId;
        this.applimentId = applimentId;
        this.address = address;
        this.date = date;
        this.applicantName = applicantName;
        this.applicantTel = applicantTel;
        this.petName = petName;
        this.petAge = petAge;
        this.petType = petType;
        this.petGender = petGender;
        this.vaccine = vaccine;
        this.sterilization = sterilization;
        this.description = description;
        this.resultDescription = resultDescription;
        this.petPhotoId = petPhotoId;
        this.state = state;

    }

    @Override
    public String toString() {
        return "responseHelpAppliment [userId=" + userId + ", applimentId=" + applimentId + ",address=" + address + ", date="
                + date + ", applicantName=" + applicantName + ", applicantTel=" + applicantTel  + ", petName=" + petName +
                ", petAge= " + petAge + ", petType=" + petType + ",petGender" + petGender + ",vaccine" + vaccine + ",sterilization" + sterilization + ",description" + description
                + ",resultDescription" + resultDescription + ",petPhotoId" + petPhotoId + ",state" + state + ",handleId=" + handleId + "]";
    }

}
