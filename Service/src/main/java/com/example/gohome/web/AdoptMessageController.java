package com.example.gohome.web;


import com.example.gohome.entity.AdoptMessage;
import com.example.gohome.service.AdoptMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
   * @Description: 处理待领养宠物信息
 * @return
 **/
@RestController
@RequestMapping("/adoptmessage")
public class AdoptMessageController {

    @Autowired
    AdoptMessageService adoptMessageService;

    /**
     * 添加待领养宠物信息
     * @return modelMap
     */
    @RequestMapping(value = "/insertadoptmessage" , method = RequestMethod.POST)
    private Map<String,Object> insertAdoptMessage(@RequestBody AdoptMessage adoptMessage){
        adoptMessage.setState(0);
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", adoptMessageService.insertAdoptMessage(adoptMessage));
        return modelMap;
    }

    /**
     * 删除领养信息
     * @return modelMap
     */
    @RequestMapping(value = "/deleteadoptmessage", method = RequestMethod.POST)
    private Map<String,Object> deleteAdoptMessage(Integer infoId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //删除领养信息
        modelMap.put("success", adoptMessageService.deleteAdoptMessage(infoId));
        return modelMap;
    }


    /**
     * 更新领养信息
     * @return modelMap
     */
    @RequestMapping(value = "/updateadoptmessage", method = RequestMethod.POST)
    private Map<String,Object> updateByPrimaryKey(AdoptMessage adoptMessage){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //删除领养信息
        modelMap.put("success", adoptMessageService.updateAdoptMessageState(adoptMessage));
        return modelMap;
    }


}
