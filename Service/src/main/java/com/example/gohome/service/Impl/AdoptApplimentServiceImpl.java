package com.example.gohome.service.Impl;

import com.example.gohome.dao.AdoptApplimentMapper;
import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.service.AdoptApplimentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdoptApplimentServiceImpl implements AdoptApplimentService {
    @Autowired
    private AdoptApplimentMapper adoptApplimentMapper;

    @Override
    @Transactional
    public boolean insert(AdoptAppliment record) {
        //判断插入的领养申请人的id不为空
        if (record.getUserId() != null  && !"".equals(record.getUserId())){
            try{
                //插入领养申请
                int effectedNum = adoptApplimentMapper.insert(record);
                if (effectedNum >0 ){
                    return true;
                }else {
                    throw new RuntimeException("添加领养申请失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("添加领养申请失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("领养申请人的ID不能为空！");
        }
    }

    @Override
    public AdoptAppliment selectByPrimaryKey(Integer applimentId) {
        AdoptAppliment adoptAppliment = null;
        if(applimentId!=null){
            try {
                adoptAppliment = adoptApplimentMapper.selectByPrimaryKey(applimentId);
            }catch (Exception e){
                throw new RuntimeException("服务器错误，查询的领养申请信息失败");
            }
        }else{
            throw new RuntimeException("查询的信息id不能为空！");
        }
        return adoptAppliment;
    }

    @Override
    @Transactional
    public boolean updateByPrimaryKey(AdoptAppliment record) {
        //判断领养申请人的ID不为空
        if(record.getUserId() != null && record.getUserId()>0 ){
            try{
                //更新领养信息
                int effectedNum = adoptApplimentMapper.updateByPrimaryKey(record);
                if (effectedNum > 0 ) {
                    return true;
                }else {
                    throw new RuntimeException("更新领养申请失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("更新领养申请失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("更新领养申请人的ID不能为空！");
        }
    }

    @Override
    public Map queryAdoptApplimentByState(Integer pageNum, Integer pageSize, Integer state) {
        Map adoptApplimentMap = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        Page<AdoptAppliment> data = adoptApplimentMapper.queryAdoptApplimentByState(state);
        adoptApplimentMap.put("adoptApplimentInfo",data);  //分页获取的数据
        adoptApplimentMap.put("total",data.getTotal());       //总页数
        adoptApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        return adoptApplimentMap;
    }

    @Override
    public Map queryAdoptApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId) {
        Map adoptApplimentMap = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        Page<AdoptAppliment> data = adoptApplimentMapper.queryAdoptApplimentByUserId(userId);
        adoptApplimentMap.put("adoptApplimentInfo",data);  //分页获取的数据
        adoptApplimentMap.put("total",data.getTotal());       //总页数
        adoptApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        return adoptApplimentMap;
    }

    @Override
    public Map queryAdoptApplimentByAdoptId(Integer pageNum, Integer pageSize, Integer adoptId) {
        Map adoptApplimentMap = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        Page<AdoptAppliment> data = adoptApplimentMapper.queryAdoptApplimentByAdoptId(adoptId);
        adoptApplimentMap.put("adoptApplimentInfo",data);  //分页获取的数据
        adoptApplimentMap.put("total",data.getTotal());       //总页数
        adoptApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        return adoptApplimentMap;
    }

    @Override
    @Transactional
    public boolean deleteByPrimaryKey(Integer applimentId) {
        //判断删除的ID号有效
        if (applimentId > 0){
            try {
                int effectedNum = adoptApplimentMapper.deleteByPrimaryKey(applimentId);
                if (effectedNum >0 ){
                    return true;
                }else {
                    throw new RuntimeException("删除领养申请失败！");
                }
            }catch (Exception e ){
                throw new RuntimeException("删除领养申请失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("删除领养申请的ID不能为空！");
        }
    }


}
