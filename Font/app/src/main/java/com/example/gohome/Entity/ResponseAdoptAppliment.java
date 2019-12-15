package com.example.gohome.Entity;

import java.util.List;

public class ResponseAdoptAppliment {


    //变量名要与后端传来的字段名一致
    private Integer pageSize;
    private Integer total;
    private List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList;
    private Integer pageNum;


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

    public List<ResponseAdoptAppliment.responseAdoptAppliment> getResponseAdoptApplimentList() {
        return responseAdoptApplimentList;
    }

    public void setResponseAdoptApplimentList(List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList) {
        this.responseAdoptApplimentList = responseAdoptApplimentList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }





    //领养申请信息
    public static class responseAdoptAppliment{

        public Integer getApplimentId() {
            return applimentId;
        }

        public void setApplimentId(Integer applimentId) {
            this.applimentId = applimentId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        private Integer applimentId; //本申请记录的ID
        private Integer userId; //申请人Id
        private Integer adoptId;    //领养申请信息数据库ID
        private Integer handleId;       //接管人id
        private String applyName; //用户申请姓名
        private String telephone; //电话号码
        private String address; //用户住址
        private String job; //用户职业
        private String description; //描述
        private String petName;  //申请领养的宠物姓名
        private String petAge;   //申请领养宠物的年龄
        private String petType;  //宠物类型
        private boolean petGender;  //宠物性别
        private boolean vaccine; // 是否注射疫苗
        private boolean sterilization ; // 绝育情况
        private String petPhotoId;      //宠物图片的id
        private String date;  //申请日期
        private Integer state;  //申请领养信息状态
        private String resultDescription; //信息处理结果反馈


        public Integer getHandleId() {
            return handleId;
        }

        public void setHandleId(Integer handleId) {
            this.handleId = handleId;
        }

        public String getApplyName() {
            return applyName;
        }

        public void setApplyName(String applyName) {
            this.applyName = applyName;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getResultDescription() {
            return resultDescription;
        }

        public void setResultDescription(String resultDescription) {
            this.resultDescription = resultDescription;
        }

        public Integer getAdoptId() {
            return adoptId;
        }

        public void setAdoptId(Integer adoptId) {
            this.adoptId = adoptId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public void setPetPhotoId(String petPhotoId) {
            this.petPhotoId = petPhotoId;
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

        public boolean getPetGender() {
            return petGender;
        }

        public void setPetGender(boolean petGender) {
            this.petGender = petGender;
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

        public String getPetPhotoId() {
            return petPhotoId;
        }

        public responseAdoptAppliment(){}

        public responseAdoptAppliment(Integer handleId,Integer applimentId, Integer userId, String applyName, String telephone, String address, String description, String petName , String petAge,
                                      String petType, boolean petGender, boolean vaccine, boolean sterilization,
                                      String petPhotoId, String date, String job, Integer adoptId, Integer state, String resultDescription){
            this.handleId = handleId;
            this.applimentId =applimentId;
            this.userId = userId;
            this.address = address;
            this.applyName = applyName;
            this.description = description;
            this.telephone = telephone;
            this.petName = petName;
            this.petAge = petAge;
            this.petType = petType;
            this.petGender = petGender;
            this.vaccine = vaccine;
            this.sterilization = sterilization;
            this.petPhotoId = petPhotoId;
            this.date = date;
            this.job = job;
            this.adoptId = adoptId;
            this.state = state;
            this.resultDescription = resultDescription;
        }

        @Override
        public String toString() {
            return "responseAdoptAppliment [address=" + address + ", applyName=" + applyName + ",description=" + description+  ", telephone="
                    + telephone + ", petName=" + petName + ", petAge=" + petAge + ", petType=" + petType + ", petGender=" + petGender  +
                    ", vaccine= " + vaccine + ", sterilization=" + sterilization  + "petPhotoId" + petPhotoId + "date" + date + "job" +  job + "adoptId" + adoptId
                    + "state" + state + "resultDescription" + resultDescription +"]";
        }

    }


    public ResponseAdoptAppliment(Integer pageSize, Integer total, List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList, Integer pageNum){
        this.responseAdoptApplimentList = responseAdoptApplimentList;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public ResponseAdoptAppliment(){}

    @Override
    public String toString() {
        return "ResponseAdoptAppliment [responseAdoptApplimentList=" + responseAdoptApplimentList + ", total=" + total + ",pageSize=" + pageSize+ "pageNum" + pageNum+ "]";
    }


}
