package com.example.gohome.Entity;

import java.io.Serializable;

public class ProcessInfo implements Serializable {
    private int petPortrait;
    private String petNickname, userText;
    private int[] processState;

    public ProcessInfo(int petPortrait, String petNickname, String userText, int[] processState) {
        super();
        this.petPortrait = petPortrait;
        this.petNickname = petNickname;
        this.userText = userText;
        this.processState = processState;
    }

    public int getPetPortrait() {
        return petPortrait;
    }

    public String getPetNickname() {
        return petNickname;
    }

    public String getUserText() {
        return userText;
    }

    public int[] getProcessState() {
        return processState;
    }

    public void setPetPortrait(int petPortrait) {
        this.petPortrait = petPortrait;
    }

    public void setPetNickname(String petNickname) {
        this.petNickname = petNickname;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public void setProcessState(int[] processState) {
        this.processState = processState;
    }
}
