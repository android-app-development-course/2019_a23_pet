package com.example.gohome.dao;

import com.example.gohome.entity.MemberMessage;

public interface MemberMessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(MemberMessage record);

    int insertSelective(MemberMessage record);

    MemberMessage selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(MemberMessage record);

    int updateByPrimaryKey(MemberMessage record);
}