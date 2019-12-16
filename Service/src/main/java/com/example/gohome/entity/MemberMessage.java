package com.example.gohome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MemberMessage {
    protected Integer messageId;

    protected Integer userId;

    protected Integer areaId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date created;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}