package com.example.gohome.dao;

import com.example.gohome.entity.JoinAppliment;
import org.springframework.stereotype.Service;

@Service
public interface JoinApplimentMapper {
    int deleteByPrimaryKey(Integer applimentId);

    int insert(JoinAppliment record);

    int insertSelective(JoinAppliment record);

    JoinAppliment selectByPrimaryKey(Integer applimentId);

    int updateByPrimaryKeySelective(JoinAppliment record);

    int updateByPrimaryKey(JoinAppliment record);
}