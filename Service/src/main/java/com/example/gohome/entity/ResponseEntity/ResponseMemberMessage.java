package com.example.gohome.entity.ResponseEntity;

import com.example.gohome.entity.MemberMessage;

import java.util.Date;

public class ResponseMemberMessage extends MemberMessage {

    private String userName;
    private String portrait;
    private String address;
    private String telephone;//user
    private Integer gender;//user

    private Integer organizerId;
    private String organizerName;
    private String organizerAddress;
    private Date organizerCreated;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerAddress() {
        return organizerAddress;
    }

    public void setOrganizerAddress(String organizerAddress) {
        this.organizerAddress = organizerAddress;
    }

    public Date getOrganizerCreated() {
        return organizerCreated;
    }

    public void setOrganizerCreated(Date organizerCreated) {
        this.organizerCreated = organizerCreated;
    }
}
