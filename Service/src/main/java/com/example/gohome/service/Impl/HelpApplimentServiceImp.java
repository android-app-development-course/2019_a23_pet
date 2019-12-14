package com.example.gohome.service.Impl;

import com.example.gohome.dao.HelpApplimentMapper;
import com.example.gohome.entity.HelpAppliment;
import com.example.gohome.entity.ResponseEntity.ResponseHelpAppliment;
import com.example.gohome.service.HelpApplimentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HelpApplimentServiceImp implements HelpApplimentService {

    @Autowired
    private HelpApplimentMapper helpApplimentMapper;


    @Override
    @Transactional
    public boolean insert(HelpAppliment record) {
        //判断插入的领养申请人的id不为空
        if (record.getUserId() != null  && !"".equals(record.getUserId())){
            try{
                //插入救助申请
                int effectedNum = helpApplimentMapper.insert(record);
                if (effectedNum >0 ){
                    return true;
                }else {
                    throw new RuntimeException("添加救助申请失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("添加救助申请失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("救助申请人的ID不能为空！");
        }
    }

    @Override
    public HelpAppliment selectByPrimaryKey(Integer applimentId) {
        HelpAppliment helpAppliment = null;
        if(applimentId!=null){
            try {
                helpAppliment = helpApplimentMapper.selectByPrimaryKey(applimentId);
            }catch (Exception e){
                throw new RuntimeException("服务器错误，查询的救助申请信息失败");
            }
        }else{
            throw new RuntimeException("查询的信息id不能为空！");
        }
        return helpAppliment;
    }

    @Override
    @Transactional
    public boolean updateByPrimaryKey(HelpAppliment record) {
        //判断领养申请人的ID不为空
        if(record.getUserId() != null && record.getUserId()>0 ){
            try{
                //更新领养信息
                int effectedNum = helpApplimentMapper.updateByPrimaryKey(record);
                if (effectedNum > 0 ) {
                    return true;
                }else {
                    throw new RuntimeException("更新救助申请失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("更新救助申请失败："+e.getMessage());
            }
        }else {
            throw new RuntimeException("更新救助申请人的ID不能为空！");
        }
    }

    @Override
    public Map queryHelpApplimentByState(Integer pageNum, Integer pageSize, Integer state) {
        Map helpApplimentMap = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        //获取申请领养信息
        Page<HelpAppliment> data = helpApplimentMapper.queryHelpApplimentByState(state);
        List<ResponseHelpAppliment> responseHelpApplimentList = new ArrayList<>();
        for(HelpAppliment helpAppliment: data){
            ResponseHelpAppliment responseHelpAppliment = new ResponseHelpAppliment();
            //设置救助申请信息
            responseHelpAppliment.setUserId(helpAppliment.getUserId());
            responseHelpAppliment.setAddress(helpAppliment.getAddress());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            responseHelpAppliment.setDate(simpleDateFormat.format(helpAppliment.getCreated()));
            System.out.println("time:"+simpleDateFormat.format(helpAppliment.getApplyDate()));
            responseHelpAppliment.setApplicantName(helpAppliment.getApplyName());
            responseHelpAppliment.setApplicantTel(helpAppliment.getTelephone());
            responseHelpAppliment.setApplimentId(helpAppliment.getApplimentId());
            if(helpAppliment.getDescription()!=null){
            responseHelpAppliment.setDescription(helpAppliment.getDescription());
            }
            if (helpAppliment.getAge()!=null){
                responseHelpAppliment.setPetAge(helpAppliment.getAge());
            }
            if(helpAppliment.getPictures()!=null){
                responseHelpAppliment.setPetPhotoId(helpAppliment.getPictures());
            }
            responseHelpAppliment.setPetGender(helpAppliment.getGender() == 1);
            responseHelpAppliment.setVaccine(helpAppliment.getVaccinate());
            responseHelpAppliment.setSterilization(helpAppliment.getSteriled());
            responseHelpAppliment.setPetType(helpAppliment.getPetType());   //0为猫，1为狗，2为小鸟
            responseHelpAppliment.setResultDescription(null);
            if(helpAppliment.getPetName()!=null){
                responseHelpAppliment.setPetName(helpAppliment.getPetName());
            }
            responseHelpAppliment.setState(helpAppliment.getState());

            responseHelpApplimentList.add(responseHelpAppliment);
        }
        helpApplimentMap.put("responseHelpApplimentList",responseHelpApplimentList);  //分页获取的数据
        helpApplimentMap.put("total",data.getTotal());       //总页数
        helpApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        helpApplimentMap.put("pageNum",pageNum);//当前页码
        System.out.println("helpApplimentMap"+helpApplimentMap);

        return helpApplimentMap;
    }

    @Override
    public Map queryHelpApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId) {
        Map helpApplimentMap = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        Page<HelpAppliment> data = helpApplimentMapper.queryHelpApplimentByUserId(userId);
        helpApplimentMap.put("helpApplimentInfo",data);  //分页获取的数据
        helpApplimentMap.put("total",data.getTotal());       //总页数
        helpApplimentMap.put("pageSize",data.getPageSize());     //每页大小
        return helpApplimentMap;
    }


    @Override
    @Transactional
    public boolean deleteByPrimaryKey(Integer applimentId) {
        //判断删除的ID号有效
        if (applimentId > 0){
            try {
                int effectedNum = helpApplimentMapper.deleteByPrimaryKey(applimentId);
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
