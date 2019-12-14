package com.example.gohome.dao;

import com.example.gohome.entity.HelpHandleInfo;
import org.springframework.stereotype.Service;

@Service
public interface HelpHandleInfoMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(HelpHandleInfo record);

    int insertSelective(HelpHandleInfo record);

    HelpHandleInfo selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(HelpHandleInfo record);

    int updateByPrimaryKey(HelpHandleInfo record);
}