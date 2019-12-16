package com.example.gohome.dao;

import com.example.gohome.entity.AdoptMessage;
import com.github.pagehelper.Page;

import java.util.List;

public interface AdoptMessageMapper {
    int deleteByPrimaryKey(Integer adoptId);

    int insert(AdoptMessage record);

    int insertSelective(AdoptMessage record);

    AdoptMessage selectByPrimaryKey(Integer adoptId);

    int updateByPrimaryKeySelective(AdoptMessage record);

    int updateByPrimaryKey(AdoptMessage record);

    Page<AdoptMessage> queryAdoptMessageByState(Integer state);
}