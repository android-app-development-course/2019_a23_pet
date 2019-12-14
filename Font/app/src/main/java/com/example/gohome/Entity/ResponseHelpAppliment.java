package com.example.gohome.Entity;

import java.util.List;

public class ResponseHelpAppliment {

    //变量名要与后端传来的字段名一致
    private Integer pageSize;
    private Integer total;
    private List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList;
    private Integer pageNum;

    //救助申请信息
    public class responseHelpAppliment{

        private String date;     //救助申请日期
        private String applicantName;   //救助申请人姓名
        private String applicantTel;     //救助申请人电话
        private String applicantAddress; //救助申请人地址
        private String petName; //宠物姓名
        private String petAge;       //宠物年龄
        private String petType;    //宠物类型
        private boolean petGender;   //宠物性别
        private boolean vaccine ; //疫苗情况
        private boolean sterilization;         //绝育情况
        private String description;     //救助申请描述
        private Integer petPhotoId;      //宠物图片的id
        private Integer helpApplimentId;   //救助申请信息数据库id
        private Integer state;  //申请救助信息状态
        private String resultDescription; //信息处理结果反馈



//        @Override
//        public String toString() {
//            return "responseHelpAppliment [address=" + address + ", applyName=" + applyName + ",description=" + description+  ", telephone="
//                    + telephone + ", petName=" + petName + ", petAge=" + petAge + ", petType=" + petType + ", petGender=" + petGender  +
//                    ", vaccine= " + vaccine + ", sterilization=" + sterilization  + "petPhotoId" + petPhotoId + "date" + date + "job" +  job + "adoptApplimentId" + adoptApplimentId
//                    + "state" + state + "resultDescription" + resultDescription +"]";
//        }

    }

}
