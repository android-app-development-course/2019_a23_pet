package com.example.gohome.dao;

import com.example.gohome.entity.HelpAppliment;
import org.springframework.stereotype.Service;

@Service
public interface HelpApplimentMapper {
    int deleteByPrimaryKey(Integer applimentId);

    int insert(HelpAppliment record);

    int insertSelective(HelpAppliment record);

    HelpAppliment selectByPrimaryKey(Integer applimentId);

    int updateByPrimaryKeySelective(HelpAppliment record);

    int updateByPrimaryKey(HelpAppliment record);
}