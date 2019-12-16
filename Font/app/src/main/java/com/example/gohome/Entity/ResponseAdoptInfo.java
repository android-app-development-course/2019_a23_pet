package com.example.gohome.Entity;

import java.util.List;

public class ResponseAdoptInfo {
    private Integer pageSize;
    private Integer pageNum;
    private Integer total;
    private List<AdoptInfo> adoptInfoList;

    public ResponseAdoptInfo(){}

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AdoptInfo> getAdoptInfoList() {
        return adoptInfoList;
    }

    public void setAdoptInfoList(List<AdoptInfo> adoptInfoList) {
        this.adoptInfoList = adoptInfoList;
    }
}
