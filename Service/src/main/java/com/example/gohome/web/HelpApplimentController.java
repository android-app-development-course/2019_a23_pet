package com.example.gohome.web;


import com.example.gohome.entity.HelpAppliment;
import com.example.gohome.service.HelpApplimentService;
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
@RequestMapping("/helpappliment")
public class HelpApplimentController {


    @Autowired
    HelpApplimentService helpApplimentService;


    /**
     * 添加救助申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/inserthelpappliment" , method = RequestMethod.POST)
    private Map<String,Object> insertHelpAppliment(@RequestBody HelpAppliment helpAppliment){
        System.out.println("helpAppliment:"+ helpAppliment);
        helpAppliment.setState(0);
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", helpApplimentService.insert(helpAppliment));
        return modelMap;
    }

    /**
     * 删除救助申请
     * @return modelMap
     */
    @RequestMapping(value = "/deletehelpappliment", method = RequestMethod.POST)
    private Map<String,Object> deleteHelpAppliment(Integer applimentId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //删除救助申请信息
        modelMap.put("success", helpApplimentService.deleteByPrimaryKey(applimentId));
        return modelMap;
    }


    /**
     * 根据主键查找救助申请
     * @return modelMap
     */
    @RequestMapping(value = "/queryhelpapplimentbyid", method = RequestMethod.GET)
    private Map<String,Object> queryByPrimaryKey(Integer applimentId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //查找救助申请信息
        modelMap.put("helpAppliment",helpApplimentService.selectByPrimaryKey(applimentId));
        return modelMap;
    }

    /**
     * 更新救助申请
     * @return modelMap
     */
    @RequestMapping(value = "/updatehelpappliment", method = RequestMethod.POST)
    private Map<String,Object> updateByPrimaryKey(HelpAppliment helpAppliment){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //删除救助申请信息
        modelMap.put("success", helpApplimentService.updateByPrimaryKey(helpAppliment));
        return modelMap;
    }


    /**
     * 根据状态分页显示救助申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryhelpapplimentbystate", method = RequestMethod.GET)
    private Map queryHelpApplimentByState(Integer pageNum, Integer pageSize, Integer state){
        return helpApplimentService.queryHelpApplimentByState(pageNum, pageSize, state);
    }

    /**
     * 根据用户ID分页显示救助申请信息
     * @return modelMap
     */
    @RequestMapping(value = "/queryhelpapplimentbyuserid", method = RequestMethod.GET)
    private Map queryHelpApplimentByUserId(Integer pageNum, Integer pageSize, Integer userId){
        return helpApplimentService.queryHelpApplimentByUserId(pageNum, pageSize, userId);
    }



}


