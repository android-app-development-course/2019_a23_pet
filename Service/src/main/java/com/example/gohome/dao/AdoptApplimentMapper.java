package com.example.gohome.dao;

import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.ProcessInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AdoptApplimentMapper {
    int deleteByPrimaryKey(Integer applimentId);

    int insert(AdoptAppliment record);

    int insertSelective(AdoptAppliment record);

    AdoptAppliment selectByPrimaryKey(Integer applimentId);

    int updateByPrimaryKeySelective(AdoptAppliment record);

    int updateByPrimaryKey(AdoptAppliment record);

    /*根据领养申请信息状态*/
    Page<AdoptAppliment> queryAdoptApplimentByState(Integer state);
    /*根据申请人*/
    Page<AdoptAppliment> queryAdoptApplimentByUserId(Integer userId);
    Page<ProcessInfo> queryAdoptApplimentByUserId2(Integer userId);
    /*根据领养信息ID*/
    Page<AdoptAppliment> queryAdoptApplimentByAdoptId(Integer adoptId);
}