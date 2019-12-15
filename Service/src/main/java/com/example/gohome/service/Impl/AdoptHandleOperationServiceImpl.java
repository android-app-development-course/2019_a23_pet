package com.example.gohome.service.Impl;

import com.example.gohome.dao.AdoptApplimentMapper;
import com.example.gohome.dao.AdoptHandleInfoMapper;
import com.example.gohome.dao.AdoptHandleOperationMapper;
import com.example.gohome.dao.AdoptMessageMapper;
import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.AdoptHandleInfo;
import com.example.gohome.entity.AdoptHandleOperation;
import com.example.gohome.entity.AdoptMessage;
import com.example.gohome.service.AdoptHandleOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdoptHandleOperationServiceImpl implements AdoptHandleOperationService {

    @Autowired
    AdoptHandleOperationMapper adoptHandleOperationMapper;
    @Autowired
    AdoptHandleInfoMapper adoptHandleInfoMapper;
    @Autowired
    AdoptApplimentMapper adoptApplimentMapper;
    @Autowired
    AdoptMessageMapper adoptMessageMapper;



    @Transactional
    @Override
    public boolean insertAdoptHandleOperation(AdoptHandleOperation adoptHandleOperation) {
        //判断领养接管信息的ID不为空
        if(adoptHandleOperation.getInfoId()!=null && !"".equals(adoptHandleOperation.getInfoId())){
            try{
                int effectedNumAdoptMessage = adoptHandleOperationMapper.insert(adoptHandleOperation);
                //同时修改adopt_message、adopt_appliment、adopt_handle_info三个表的state值
                int isSuccess = adoptHandleOperation.getState();     //0为失败，1位成功

                AdoptHandleInfo adoptHandleInfo = adoptHandleInfoMapper.selectByPrimaryKey(adoptHandleOperation.getInfoId());
                adoptHandleInfo.setState(1);
                int effectedNum1 = adoptHandleInfoMapper.updateByPrimaryKeySelective(adoptHandleInfo);

                AdoptAppliment adoptAppliment = adoptApplimentMapper.selectByPrimaryKey(adoptHandleInfo.getApplimentId());
                adoptAppliment.setState(2);
                int effectedNum2 = adoptApplimentMapper.updateByPrimaryKeySelective(adoptAppliment);

                AdoptMessage adoptMessage = adoptMessageMapper.selectByPrimaryKey(adoptAppliment.getAdoptId());
                if(isSuccess == 1){
                    adoptMessage.setState(2);  //设为成功领养
                }else{//设为领养失败，宠物继续展示
                    adoptMessage.setState(0);
                }
                int effectedNum3 = adoptMessageMapper.updateByPrimaryKeySelective(adoptMessage);

                if (effectedNumAdoptMessage >0 && effectedNum1 > 0 && effectedNum2 >0 && effectedNum3 > 0){
                    return true;
                }else {
                    throw new RuntimeException("添加领养操作记录失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("添加领养操作记录失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("添加操作记录的接管信息Id为空！");
        }
    }

    @Override
    @Transactional
    public boolean deleteAdoptHandleOperation(int operationId) {
        //判断删除的ID号有效
        if (operationId > 0){
            try {
                int effectedNum = adoptHandleOperationMapper.deleteByPrimaryKey(operationId);
                if (effectedNum >0 ){
                    return true;
                }else {
                    throw new RuntimeException("删除操作记录失败！");
                }
            }catch (Exception e ){
                throw new RuntimeException("删除操作记录失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("删除操作记录的operationId不能为空！");
        }
    }

    @Transactional
    public boolean updateAdoptHandleOperation(AdoptHandleOperation adoptHandleOperation){
        //判断领养信息的ID不为空
        if(adoptHandleOperation.getOperationId() != null && adoptHandleOperation.getOperationId()>0 ){
            try{
                //更新领养信息，选择性更新
                int effectedNum = adoptHandleOperationMapper.updateByPrimaryKeySelective(adoptHandleOperation);
                if (effectedNum > 0 ) {
                    return true;
                }else {
                    throw new RuntimeException("更新操作记录失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("更新操作记录失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("更新的操作记录ID不能为空！");
        }
    }



}
