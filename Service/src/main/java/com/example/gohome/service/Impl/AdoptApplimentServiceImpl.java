package com.example.gohome.service.Impl;

import com.example.gohome.dao.AdoptApplimentMapper;
import com.example.gohome.dao.AdoptMessageMapper;
import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.AdoptMessage;
import com.example.gohome.entity.ResponseEntity.ResponseAdoptAppliment;
import com.example.gohome.service.AdoptApplimentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class AdoptApplimentServiceImpl implements AdoptApplimentService {
    @Autowired
    private AdoptApplimentMapper adoptApplimentMapper;
    @Autowired
    private AdoptMessageMapper adoptMessageMapper;

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
        //获取申请领养信息
        Page<AdoptAppliment> data = adoptApplimentMapper.queryAdoptApplimentByState(state);
        List<ResponseAdoptAppliment> responseAdoptApplimentList = new ArrayList<>();
        for(AdoptAppliment adoptAppliment: data){
            ResponseAdoptAppliment responseAdoptAppliment = new ResponseAdoptAppliment();
            //设置领养申请信息
            responseAdoptAppliment.setApplimentId(adoptAppliment.getApplimentId());
            responseAdoptAppliment.setUserId(adoptAppliment.getUserId());
            responseAdoptAppliment.setAddress(adoptAppliment.getAddress());
            responseAdoptAppliment.setAdoptApplimentId(adoptAppliment.getApplimentId());
            responseAdoptAppliment.setApplyName(adoptAppliment.getApplyName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            responseAdoptAppliment.setDate(simpleDateFormat.format(adoptAppliment.getCreated()));
            System.out.println("time:"+simpleDateFormat.format(adoptAppliment.getCreated()));

            responseAdoptAppliment.setDescription(adoptAppliment.getDescription());
            responseAdoptAppliment.setDescription(adoptAppliment.getJob());
            responseAdoptAppliment.setState(0);
            responseAdoptAppliment.setTelephone(adoptAppliment.getTelephone());

            //设置领养动物信息
            //先查询获取该领养申请对应的领养信息
            AdoptMessage adoptMessage = adoptMessageMapper.selectByPrimaryKey(adoptAppliment.getAdoptId());
            //将值存储到返回体中
            responseAdoptAppliment.setPetAge(adoptMessage.getAge());
            responseAdoptAppliment.setPetGender(adoptMessage.getGender() == 1);
            responseAdoptAppliment.setPetName(adoptMessage.getPetName());
            responseAdoptAppliment.setPetPhotoId(adoptMessage.getPictures());
            responseAdoptAppliment.setPetType(adoptMessage.getPetType());  //0为小猫，1为狗，2为鸟
            responseAdoptAppliment.setResultDescription(null);
            responseAdoptAppliment.setSterilization(adoptMessage.getSteriled());
            responseAdoptAppliment.setVaccine(adoptMessage.getVaccinate());

            responseAdoptApplimentList.add(responseAdoptAppliment);
        }
        adoptApplimentMap.put("responseAdoptApplimentList",responseAdoptApplimentList);  //分页获取的数据
        adoptApplimentMap.put("total",data.getTotal());       //总页数
        adoptApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        adoptApplimentMap.put("pageNum",pageNum);//当前页码
        System.out.println("adoptApplimentMap"+adoptApplimentMap);


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
