package com.example.gohome.Entity;

import java.io.Serializable;

public class ProcessInfo implements Serializable {
    private Integer applimentId;
    private Integer adoptId;
    private Integer userId;
    private String petPortrait;
    private String petNickname, userText;
    private Integer processState;

    public ProcessInfo(Integer applimentId, Integer adoptId, Integer userId, String petPortrait, String petNickname, String userText, Integer processState) {
        super();
        this.applimentId = applimentId;
        this.adoptId = adoptId;
        this.userId = userId;
        this.petPortrait = petPortrait;
        this.petNickname = petNickname;
        this.userText = userText;
        this.processState = processState;
    }

    public Integer getApplimentId() {
        return applimentId;
    }

    public Integer getAdoptId() {
        return adoptId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPetPortrait() {
        return petPortrait;
    }

    public String getPetNickname() {
        return petNickname;
    }

    public String getUserText() {
        return userText;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setApplimentId(Integer applimentId) {
        this.applimentId = applimentId;
    }

    public void setAdoptId(Integer adoptId) {
        this.adoptId = adoptId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPetPortrait(String petPortrait) {
        this.petPortrait = petPortrait;
    }

    public void setPetNickname(String petNickname) {
        this.petNickname = petNickname;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }
}