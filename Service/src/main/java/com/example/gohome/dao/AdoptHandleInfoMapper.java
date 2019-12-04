package com.example.gohome.dao;

import com.example.gohome.entity.AdoptHandleInfo;

public interface AdoptHandleInfoMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(AdoptHandleInfo record);

    int insertSelective(AdoptHandleInfo record);

    AdoptHandleInfo selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(AdoptHandleInfo record);

    int updateByPrimaryKey(AdoptHandleInfo record);
}