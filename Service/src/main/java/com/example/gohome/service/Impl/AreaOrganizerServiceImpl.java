package com.example.gohome.service.Impl;

import com.example.gohome.dao.AreaOrganizerMapper;
import com.example.gohome.entity.AreaOrganizer;
import com.example.gohome.service.AreaOrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AreaOrganizerServiceImpl implements AreaOrganizerService {

    @Autowired
    AreaOrganizerMapper areaOrganizerMapper;

    @Override
    public AreaOrganizer areaOrganizerByAreaId(Integer areaId) {
        if(areaId!=null){
            try {
                AreaOrganizer areaOrganizer = areaOrganizerMapper.selectByPrimaryKey(areaId);
                return areaOrganizer;
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else{
            throw new RuntimeException("信息错误，获取组织信息失败！");
        }
    }

    @Override
    public AreaOrganizer areaOrganizerByUserId(Integer userId) {
        if(userId!=null){
            try {
                AreaOrganizer areaOrganizer = areaOrganizerMapper.selectByUserId(userId);
                return areaOrganizer;
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else{
            throw new RuntimeException("信息错误，获取组织信息失败！");
        }
    }
}
