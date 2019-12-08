package com.example.gohome.service.Impl;

import com.example.gohome.dao.AdoptHandleOperationMapper;
import com.example.gohome.dao.AdoptMessageMapper;
import com.example.gohome.dao.UserMessageDao;
import com.example.gohome.entity.AdoptMessage;
import com.example.gohome.service.AdoptMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdoptMessageServiceImpl implements AdoptMessageService {


    @Autowired
    AdoptMessageMapper adoptMessageMapper;
    @Autowired
    UserMessageDao userMessageDao;
    @Autowired
    AdoptHandleOperationMapper adoptHandleRecordDao;


    @Transactional
    @Override
    public boolean insertAdoptMessage(AdoptMessage adoptMessage) {
        //判断领养信息发布人的ID不为空
        if(adoptMessage.getHandleId()!=null && !"".equals(adoptMessage.getHandleId())){
            try{
                //插入待领养宠物信息
                int effectedNumAdoptMessage = adoptMessageMapper.insert(adoptMessage);

                if (effectedNumAdoptMessage >0 ){
                    return true;
                }else {
                    throw new RuntimeException("添加待领养动物信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("添加待领养动物信息失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("添加待领养动物信息的用户Id为空！");
        }
    }

    @Override
    @Transactional
    public boolean deleteAdoptMessage(int InfoId) {
        //判断删除的ID号有效
        if (InfoId > 0){
            try {
                int effectedNum = adoptMessageMapper.deleteByPrimaryKey(InfoId);
                if (effectedNum >0 ){
                    return true;
                }else {
                    throw new RuntimeException("删除待领养动物信息失败！");
                }
            }catch (Exception e ){
                throw new RuntimeException("删除待领养动物信息失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("删除待领养动物信息的InfoID不能为空！");
        }
    }

//    @Override
//    @Transactional
//    public Map<String, Object> queryAdoptMessage(Integer pageNum, Integer pageSize) {
//        Map<String, Object> modelMap = new HashMap<>();
//        PageHelper.startPage(pageNum, pageSize);
//        Page<AdoptApply> data = adoptApplyDao.queryAdoptApply();
//        modelMap.put("adoptApply", data);
//        modelMap.put("total", data.getTotal());
//        modelMap.put("pageSize", data.getPageSize());
//        return modelMap;
//    }
//
//    @Override
//    @Transactional
//    public Map<String, Object> queryAdoptMessageByState(Integer pageNum, Integer pageSize, Integer state) {
//        Map<String, Object> modelMap = new HashMap<>();
//        try {
//            PageHelper.startPage(pageNum, pageSize);
//            Page<AdoptMessage> data;
//            if(state==0){
//                data = adoptApplyDao.queryAdoptApplyByState(state);
//            }else{
//                data = adoptApplyDao.queryAdoptApplyByHandleState(state);
//            }
//            modelMap.put("adoptApply", data);
//            modelMap.put("total", data.getTotal());
//            modelMap.put("pageSize", data.getPageSize());
//        }catch (Exception e){
//            throw new RuntimeException("获取信息失败：" + e.getMessage());
//        }
//
//        return modelMap;
//    }
//
//    @Override
//    public Map<String, Object> queryAdoptMessageByStateHandleId(Integer pageNum, Integer pageSize, Integer state, Integer handleId) {
//        Map<String, Object> modelMap = new HashMap<>();
//        try {
//            PageHelper.startPage(pageNum, pageSize);
//            Page<AdoptMessage> data = adoptApplyDao.queryAdoptApplyByStateAndHandleId(state, handleId);
//            modelMap.put("adoptApply", data);
//            modelMap.put("total", data.getTotal());
//            modelMap.put("pageSize", data.getPageSize());
//        }catch (Exception e){
//            throw new RuntimeException("获取信息失败：" + e.getMessage());
//        }
//
//        return modelMap;    }
//
//    @Override
//    @Transactional
//    public AdoptMessage adoptApplyByInfoId(Integer infoId) {
//        return adoptApplyDao.adoptApplyByInfoId(infoId);
//    }
//
//    @Override
//    @Transactional
//    public boolean updateAdoptMessageByState(Integer infoID, Integer handleID, Integer state, String description) {
//        try {
//            AdoptMessage adoptApply = adoptApplyDao.adoptApplyByInfoId(infoID);
//            if(adoptApply.getState()>=state){
//                System.out.println(adoptApply.getState());
//                System.out.println(state);
//                throw new RuntimeException("申请信息已改变，请刷新后重试");
//            }
//            int effectNum1 = adoptApplyDao.updateAdoptApplyByState(infoID,state);
//            AdoptHandleRecord adoptHandleRecord = new AdoptHandleRecord();
//            adoptHandleRecord.setState(state);
//            adoptHandleRecord.setDescription(description);
//            adoptHandleRecord.setHandleId(handleID);
//            adoptHandleRecord.setAdoptId(adoptApply.getAdoptID());
//            adoptHandleRecord.setApplimentId(infoID);
//            int effectNum2 = adoptHandleRecordDao.insertAdoptHandleRecord(adoptHandleRecord);
//            int effectNum3 = 1;
//            if(state==2){//领养成功，修改领养信息状态
//                effectNum3 = adoptInformationDao.updateAdoptInfoState(1, adoptApply.getAdoptID());
//            }
//            if(effectNum1>0 && effectNum2>0 && effectNum3>0){
//                return true;
//            }else {
//                throw new RuntimeException("修改领养信息数据错误");
//            }
//        }catch (Exception e){
//            throw new RuntimeException("操作失败:" + e.getMessage());
//        }
//    }
}
