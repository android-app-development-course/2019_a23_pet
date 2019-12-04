package com.example.gohome.entity;

import java.util.Date;

public class HelpHandleOperation {
    private Integer operatioinId;

    private Integer infoId;

    private String description;

    private Integer state;

    private Date created;

    public Integer getOperatioinId() {
        return operatioinId;
    }

    public void setOperatioinId(Integer operatioinId) {
        this.operatioinId = operatioinId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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