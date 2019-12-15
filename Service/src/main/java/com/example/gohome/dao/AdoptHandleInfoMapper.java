package com.example.gohome.dao;

import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.AdoptHandleInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptHandleInfoMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(AdoptHandleInfo record);

    int insertSelective(AdoptHandleInfo record);

    AdoptHandleInfo selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(AdoptHandleInfo record);

    int updateByPrimaryKey(AdoptHandleInfo record);

    List<AdoptHandleInfo> selectByHandleId(Integer handleId);

    /*根据接管人id状态*/
    Page<AdoptHandleInfo> queryAdoptHandleInfoByHandleId(Integer handId);
}