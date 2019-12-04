package com.example.gohome.dao;

import com.example.gohome.entity.User;

public interface AdoptMessageMapper {
    int deleteByPrimaryKey(Integer adoptId);

    int insert(User.AdoptMessage record);

    int insertSelective(User.AdoptMessage record);

    User.AdoptMessage selectByPrimaryKey(Integer adoptId);

    int updateByPrimaryKeySelective(User.AdoptMessage record);

    int updateByPrimaryKey(User.AdoptMessage record);
}