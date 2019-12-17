package com.example.gohome.Entity;

import androidx.annotation.NonNull;

import com.example.gohome.Utils.Cn2Spell;

import java.util.Date;

public class Member {

    private Integer messageId;//MemberMessage
    private Integer userId;//MemberMessage
    private String telephone;//user
    private String address;//ext
    private String userName;//ext
    private Integer gender;//ext
    private String portrait;//ext
    private Integer areaId;//MemberMessage
    private Date memberCreated;//MemberMessage
    private String headerWord;
    private String pinyin;


    public Member(Member member) {
        this.messageId = member.messageId;
        this.userId = member.userId;
        this.telephone = member.telephone;
        this.address = member.address;
        this.userName = member.userName;
        this.gender = member.gender;
        this.portrait = member.portrait;
        this.areaId = member.areaId;
        this.memberCreated = member.memberCreated;
    }

    public Member(Integer userId, String userName, String address, String portrait, Integer photoId){
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.pinyin = Cn2Spell.getPinYin(userName);
        this.headerWord = Cn2Spell.getPinYinFirstLetter(userName);
        this.portrait = portrait;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Date getMemberCreated() {
        return memberCreated;
    }

    public void setMemberCreated(Date memberCreated) {
        this.memberCreated = memberCreated;
    }

    @NonNull
    @Override
    public String toString() {
        return "Member:[message_id="+messageId+"user_id="+userId+"userName="+userName+"]";
    }


}
