package com.example.gohome.service;

import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.ResponseEntity.ResponseAdoptAppliment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AdoptApplimentService {

    //通过主键删除
    boolean deleteByPrimaryKey(Integer applimentId);
    //插入记录
    boolean insert(AdoptAppliment record);
    //根据主键选择
    AdoptAppliment selectByPrimaryKey(Integer applimentId);
    //根据主键更新
    boolean updateByPrimaryKey(AdoptAppliment record);

    boolean updateAdoptApplimentState(AdoptAppliment adoptAppliment);

    //将未处理状态的领养申请信息状态转为处理中状态（需要修改adopt_message，adopt_appliment，adopt_handle_info三个表）
    boolean updateAdoptApplimentToDoing(ResponseAdoptAppliment responseAdoptAppliment);

    /* 分页查找领养申请信息  */
    /*根据领养申请信息状态*/
    Map queryAdoptApplimentByState(Integer pageNum, Integer pageSize, Integer state);
    /*根据申请人*/
    Map queryAdoptApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId);
    /*根据领养信息ID*/
    Map queryAdoptApplimentByAdoptId(Integer pageNum, Integer pageSize, Integer adoptId);
    /*根据领养申请信息状态*/
    Map queryAdoptApplimentByStateAndHandleId(Integer pageNum, Integer pageSize, Integer state,Integer handleId);
}
