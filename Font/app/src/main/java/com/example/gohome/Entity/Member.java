package com.example.gohome.Entity;

import com.example.gohome.utils.Cn2Spell;

import java.util.Date;

public class Member {

    private Integer userId;
    private String telephone;
    private String address;
    private String userName;
    private Integer gender;
    private String protrait;
    private Integer areaId;
    private Date memberCreated;
    private String headerWord;
    private String pinyin;

    public Member(Integer userId, String userName, String address, String protrait){
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.pinyin = Cn2Spell.getPinYin(userName);
        this.headerWord = Cn2Spell.getPinYinFirstLetter(userName);
        this.protrait = protrait;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.headerWord = Cn2Spell.getPinYinHeadChar(userName);
        this.pinyin = Cn2Spell.getPinYinFirstLetter(userName);
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public String getPinyin() {
        return pinyin;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProtrait() {
        return protrait;
    }

    public void setProtrait(String protrait) {
        this.protrait = protrait;
    }

    public Date getMemberCreated() {
        return memberCreated;
    }

    public void setMemberCreated(Date memberCreated) {
        this.memberCreated = memberCreated;
    }
}
