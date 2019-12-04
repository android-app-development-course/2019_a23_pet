package com.example.gohome.dao;

import com.example.gohome.entity.AdoptHandleOperation;

public interface AdoptHandleOperationMapper {
    int deleteByPrimaryKey(Integer operationId);

    int insert(AdoptHandleOperation record);

    int insertSelective(AdoptHandleOperation record);

    AdoptHandleOperation selectByPrimaryKey(Integer operationId);

    int updateByPrimaryKeySelective(AdoptHandleOperation record);

    int updateByPrimaryKey(AdoptHandleOperation record);
}