package com.example.gohome.dao;

import com.example.gohome.entity.AreaOrganizer;
import org.springframework.stereotype.Service;

@Service
public interface AreaOrganizerMapper {
    int deleteByPrimaryKey(Integer areaId);

    int insert(AreaOrganizer record);

    int insertSelective(AreaOrganizer record);

    AreaOrganizer selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(AreaOrganizer record);

    int updateByPrimaryKey(AreaOrganizer record);

    AreaOrganizer selectByUserId(Integer userId);
}