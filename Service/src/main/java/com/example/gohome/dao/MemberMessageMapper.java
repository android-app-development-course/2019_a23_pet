package com.example.gohome.dao;

import com.example.gohome.entity.MemberMessage;
import com.example.gohome.entity.ResponseEntity.ResponseMemberMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberMessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(MemberMessage record);

    int insertSelective(MemberMessage record);

    MemberMessage selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(MemberMessage record);

    int updateByPrimaryKey(MemberMessage record);

    MemberMessage selectByUserId(Integer userId);

    List<ResponseMemberMessage> queryMemberMessageByAreaId(Integer areaId);
}