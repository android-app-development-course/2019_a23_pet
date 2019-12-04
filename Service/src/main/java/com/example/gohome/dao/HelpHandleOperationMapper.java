package com.example.gohome.dao;

import com.example.gohome.entity.HelpHandleOperation;

public interface HelpHandleOperationMapper {
    int deleteByPrimaryKey(Integer operatioinId);

    int insert(HelpHandleOperation record);

    int insertSelective(HelpHandleOperation record);

    HelpHandleOperation selectByPrimaryKey(Integer operatioinId);

    int updateByPrimaryKeySelective(HelpHandleOperation record);

    int updateByPrimaryKey(HelpHandleOperation record);
}