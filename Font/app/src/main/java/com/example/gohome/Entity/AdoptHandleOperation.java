package com.example.gohome.Entity;

public class AdoptHandleOperation {

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
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Integer infoId;

    private String description;

    private Integer state;


    public AdoptHandleOperation(Integer infoId, Integer state,String description){
        this.infoId = infoId;
        this.state = state;
        this.description = description;
    }

    public AdoptHandleOperation(){}

    @Override
    public String toString(){
        return "AdoptHandleOperation [infoId=" + infoId + ", state=" + state + ",description=" + description + "]";

    }



}
