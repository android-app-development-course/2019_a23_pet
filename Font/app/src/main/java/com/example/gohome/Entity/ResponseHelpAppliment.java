package com.example.gohome.Entity;

import java.util.List;

public class ResponseHelpAppliment {

    //变量名要与后端传来的字段名一致
    private Integer pageSize;
    private Integer total;
    private List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList;
    private Integer pageNum;

    public ResponseHelpAppliment(Integer pageSize,Integer total, List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList,Integer pageNum){
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.responseHelpApplimentList = responseHelpApplimentList;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<responseHelpAppliment> getResponseHelpApplimentList() {
        return responseHelpApplimentList;
    }

    public void setResponseHelpApplimentList(List<responseHelpAppliment> responseHelpApplimentList) {
        this.responseHelpApplimentList = responseHelpApplimentList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }


    //救助申请信息
    public class responseHelpAppliment {

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getApplimentId() {
            return applimentId;
        }

        public void setApplimentId(Integer applimentId) {
            this.applimentId = applimentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getApplicantTel() {
            return applicantTel;
        }

        public void setApplicantTel(String applicantTel) {
            this.applicantTel = applicantTel;
        }

        public String getPetName() {
            return petName;
        }

        public void setPetName(String petName) {
            this.petName = petName;
        }

        public String getPetAge() {
            return petAge;
        }

        public void setPetAge(String petAge) {
            this.petAge = petAge;
        }

        public String getPetType() {
            return petType;
        }

        public void setPetType(String petType) {
            this.petType = petType;
        }

        public boolean getVaccine() {
            return vaccine;
        }

        public void setVaccine(boolean vaccine) {
            this.vaccine = vaccine;
        }

        public boolean getSterilization() {
            return sterilization;
        }

        public void setSterilization(boolean sterilization) {
            this.sterilization = sterilization;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPetPhotoId() {
            return petPhotoId;
        }

        public void setPetPhotoId(String petPhotoId) {
            this.petPhotoId = petPhotoId;
        }


        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getResultDescription() {
            return resultDescription;
        }

        public void setResultDescription(String resultDescription) {
            this.resultDescription = resultDescription;
        }

        private Integer userId; //救助申请人ID
        private Integer applimentId; //本救助申请的ID
        private String address; //申请人的地址
        private String date;     //救助申请日期
        private String applicantName;   //救助申请人姓名
        private String applicantTel;     //救助申请人电话
        private String petName; //宠物姓名
        private String petAge;       //宠物年龄
        private String petType;    //宠物类型

        public boolean getPetGender() {
            return petGender;
        }

        public void setPetGender(boolean petGender) {
            this.petGender = petGender;
        }

        private boolean petGender;   //宠物性别
        private boolean vaccine; //疫苗情况
        private boolean sterilization;         //绝育情况
        private String description;     //救助申请描述
        private String petPhotoId;      //宠物图片的id
        private Integer state;  //申请救助信息状态
        private String resultDescription; //信息处理结果反馈


        public responseHelpAppliment() {
        }

        public responseHelpAppliment(Integer userId, Integer applimentId, String address, String date, String applicantName, String applicantTel,
                                     String petName, String petAge, String petType, boolean petGender, boolean vaccine, boolean sterilization, String description, String resultDescription,
                                     String petPhotoId, Integer state) {
            this.userId = userId;
            this.applimentId = applimentId;
            this.address = address;
            this.date = date;
            this.applicantName = applicantName;
            this.applicantTel = applicantTel;
            this.petName = petName;
            this.petAge = petAge;
            this.petType = petType;
            this.petGender = petGender;
            this.vaccine = vaccine;
            this.sterilization = sterilization;
            this.description = description;
            this.resultDescription = resultDescription;
            this.petPhotoId = petPhotoId;
            this.state = state;

        }

        @Override
        public String toString() {
            return "responseHelpAppliment [userId=" + userId + ", applimentId=" + applimentId + ",address=" + address + ", date="
                    + date + ", applicantName=" + applicantName + ", applicantTel=" + applicantTel  + ", petName=" + petName +
                    ", petAge= " + petAge + ", petType=" + petType + ",petGender" + petGender + ",vaccine" + vaccine + ",sterilization" + sterilization + ",description" + description
                    + ",resultDescription" + resultDescription + ",petPhotoId" + petPhotoId + ",state" + state + "]";
        }
    }


    @Override
    public String toString() {
        return "ResponseHelpAppliment [responseHelpApplimentList=" + responseHelpApplimentList + ", total=" + total + ",pageSize=" + pageSize+ "pageNum" + pageNum+ "]";
    }

}
