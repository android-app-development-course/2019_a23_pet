package com.example.gohome.Entity;

public class AdoptAppliment {

    public Integer getApplimentId() {
        return applimentId;
    }

    public void setApplimentId(Integer applimentId) {
        this.applimentId = applimentId;
    }

    public Integer getAdoptId() {
        return adoptId;
    }

    public void setAdoptId(Integer adoptId) {
        this.adoptId = adoptId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Integer applimentId;  //领养申请信息id
    private Integer adoptId;  //领养动物信息id
    private Integer userId; //用户Id
    private String applyName; //用户申请姓名
    private String telephone; //电话号码
    private String address; //用户住址
    private String description; //描述
    private Integer state; //领养申请状态：0联系中，1已通过，2未通过

    public AdoptAppliment(Integer applimentId,Integer adoptId,Integer userId,String applyName,
        String telephone,String address,String description,Integer state){
        this.address = address;
        this.adoptId = adoptId;
        this.applimentId = applimentId;
        this.applyName = applyName;
        this.description = description;
        this.state = state;
        this.telephone = telephone;
        this.userId = userId;
    }


}
