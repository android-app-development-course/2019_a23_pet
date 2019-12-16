package com.example.gohome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AdoptHandleInfo {
    private Integer infoId;

    private Integer handleId;

    private Integer applimentId;

    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date created;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getApplimentId() {
        return applimentId;
    }

    public void setApplimentId(Integer applimentId) {
        this.applimentId = applimentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}