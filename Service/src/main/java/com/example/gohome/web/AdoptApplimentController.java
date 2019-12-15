package com.example.gohome.web;


import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.ResponseEntity.ResponseAdoptAppliment;
import com.example.gohome.service.AdoptApplimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
   * @Description: 处理领养申请信息
 * @return
 **/
@RestController
@RequestMapping("/adoptappliment")
public class AdoptApplimentController {

    @Autowired
    AdoptApplimentService adoptApplimentService;


    /**
     * 添加领养申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/insertadoptappliment" , method = RequestMethod.POST)
    private Map<String,Object> insertAdoptAppliment(@RequestBody AdoptAppliment adoptAppliment){
        adoptAppliment.setState(0);
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", adoptApplimentService.insert(adoptAppliment));
        return modelMap;
    }

    /**
     * 删除领养申请
     * @return modelMap
     */
    @RequestMapping(value = "/deleteadoptappliment", method = RequestMethod.POST)
    private Map<String,Object> deleteAdoptAppliment(Integer applimentId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //删除领养申请信息
        modelMap.put("success", adoptApplimentService.deleteByPrimaryKey(applimentId));
        return modelMap;
    }


    /**
     * 根据主键查找领养申请
     * @return modelMap
     */
    @RequestMapping(value = "/queryadoptapplimentbyid", method = RequestMethod.GET)
    private Map<String,Object> queryByPrimaryKey(Integer applimentId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //查找领养申请信息
        modelMap.put("adoptAppliment",adoptApplimentService.selectByPrimaryKey(applimentId));
        return modelMap;
    }

    /**
     * 更新领养申请
     * @return modelMap
     */
    @RequestMapping(value = "/updateadoptappliment", method = RequestMethod.POST)
    private Map<String,Object> updateByPrimaryKey(AdoptAppliment adoptAppliment){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //更新领养申请信息
        modelMap.put("success", adoptApplimentService.updateByPrimaryKey(adoptAppliment));
        return modelMap;
    }

    /**
     * 更新领养申请状态为处理中
     * @return modelMap
     */
    @RequestMapping(value = "/updateadoptapplimenttodoing", method = RequestMethod.POST)
    private Map<String,Object> updateAdoptApplimentToDoing(@RequestBody ResponseAdoptAppliment responseAdoptAppliment){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //更新领养申请信息
        modelMap.put("success", adoptApplimentService.updateAdoptApplimentToDoing(responseAdoptAppliment));
        return modelMap;
    }


    /**
     * 根据状态分页显示领养申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryadoptapplimentbystate", method = RequestMethod.GET)
    private Map queryAdoptApplimentByState(Integer pageNum, Integer pageSize, Integer state){
        return adoptApplimentService.queryAdoptApplimentByState(pageNum, pageSize, state);
    }

    /**
     * 根据用户ID分页显示领养申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryadoptapplimentbyuserid", method = RequestMethod.GET)
    private Map queryAdoptApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId){
        return adoptApplimentService.queryAdoptApplimentByUserId(pageNum, pageSize, userId);
    }

    /**
     * 根据待领养动物ID分页显示领养申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryadoptapplimentbyadoptid", method = RequestMethod.GET)
    private Map queryAdoptApplimentByAdoptId(Integer pageNum, Integer pageSize, Integer adoptId){
        return adoptApplimentService.queryAdoptApplimentByUserId(pageNum, pageSize, adoptId);
    }

    /**
     * 根据状态分页显示领养申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryadoptapplimentbystateandhandleid", method = RequestMethod.GET)
    private Map queryAdoptApplimentByStateAndHandleId(Integer pageNum, Integer pageSize, Integer state,Integer handleId){
        return adoptApplimentService.queryAdoptApplimentByStateAndHandleId(pageNum, pageSize, state,handleId);
    }



}
