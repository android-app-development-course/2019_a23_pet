package com.example.gohome.dao;

import com.example.gohome.entity.AdoptAppliment;

public interface AdoptApplimentMapper {
    int deleteByPrimaryKey(Integer applimentId);

    int insert(AdoptAppliment record);

    int insertSelective(AdoptAppliment record);

    AdoptAppliment selectByPrimaryKey(Integer applimentId);

    int updateByPrimaryKeySelective(AdoptAppliment record);

    int updateByPrimaryKey(AdoptAppliment record);
}