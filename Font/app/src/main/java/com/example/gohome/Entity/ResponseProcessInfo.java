package com.example.gohome.Entity;

import java.util.List;

public class ResponseProcessInfo {
    private Integer pageSize;
    private Integer pageNum;
    private Integer total;
    private List<ProcessInfo> processInfoList;

    public ResponseProcessInfo(){}

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

    public List<ProcessInfo> getProcessInfoList() {
        return processInfoList;
    }

    public void setProcessInfoList(List<ProcessInfo> processInfoList) {
        this.processInfoList = processInfoList;
    }
}