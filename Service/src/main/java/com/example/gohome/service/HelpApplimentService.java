package com.example.gohome.service;

import com.example.gohome.entity.HelpAppliment;
import com.example.gohome.entity.ResponseEntity.ResponseHelpAppliment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface HelpApplimentService {


    //通过主键删除
    boolean deleteByPrimaryKey(Integer applimentId);
    //插入记录
    boolean insert(HelpAppliment record);
    //根据主键选择
    HelpAppliment selectByPrimaryKey(Integer applimentId);
    //根据主键更新
    boolean updateByPrimaryKey(HelpAppliment record);

    //将未处理状态的救助申请信息状态转为处理中状态（需要修改help_appliment，help_handle_info两个表）
    boolean updateHelpApplimentToDoing(ResponseHelpAppliment responseHelpAppliment);

    /* 分页查找救助申请信息  */
    /*根据救助申请信息状态*/
    Map queryHelpApplimentByState(Integer pageNum, Integer pageSize, Integer state);
    /*根据申请人*/
    Map queryHelpApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId);
}
