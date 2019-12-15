package com.example.gohome.dao;

import com.example.gohome.entity.HelpHandleInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

@Service
public interface HelpHandleInfoMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(HelpHandleInfo record);

    int insertSelective(HelpHandleInfo record);

    HelpHandleInfo selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(HelpHandleInfo record);

    int updateByPrimaryKey(HelpHandleInfo record);

    /*根据接管人id状态*/
    Page<HelpHandleInfo> queryHelpHandleInfoByHandleId(Integer handId);
}