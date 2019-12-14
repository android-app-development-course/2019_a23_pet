package com.example.gohome.dao;

import com.example.gohome.entity.HelpAppliment;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

@Service
public interface HelpApplimentMapper {
    int deleteByPrimaryKey(Integer applimentId);

    int insert(HelpAppliment record);

    int insertSelective(HelpAppliment record);

    HelpAppliment selectByPrimaryKey(Integer applimentId);

    int updateByPrimaryKeySelective(HelpAppliment record);

    int updateByPrimaryKey(HelpAppliment record);

    /*根据救助申请信息状态*/
    Page<HelpAppliment> queryHelpApplimentByState(Integer state);
    /*根据申请人*/
    Page<HelpAppliment> queryHelpApplimentByUserId(Integer userId);

}