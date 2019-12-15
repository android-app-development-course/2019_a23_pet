package com.example.gohome.web;


import com.example.gohome.dao.AdoptHandleOperationMapper;
import com.example.gohome.entity.AdoptHandleOperation;
import com.example.gohome.service.AdoptHandleOperationService;
import com.example.gohome.service.AdoptMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
   * @Description: 处理待领养宠物信息
 * @return
 **/
@RestController
@RequestMapping("/adopthandleoperation")
public class AdoptHandleOperationController {

    @Autowired
    AdoptHandleOperationService adoptHandleOperationService;


    /**
     *
     * @return modelMap
     */
    @RequestMapping(value = "/insertadopthandleoperation" , method = RequestMethod.POST)
    private Map<String,Object> insertAdoptHandleOperation(@RequestBody AdoptHandleOperation adoptHandleOperation){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", adoptHandleOperationService.insertAdoptHandleOperation(adoptHandleOperation));
        return modelMap;
    }

    /**
     *
     * @return modelMap
     */
    @RequestMapping(value = "/deleteadopthandleoperation", method = RequestMethod.POST)
    private Map<String,Object> deleteAdoptHandleOperation(Integer operationId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", adoptHandleOperationService.deleteAdoptHandleOperation(operationId));
        return modelMap;
    }


    /**
     * @return modelMap
     */
    @RequestMapping(value = "/updateadopthandleoperation", method = RequestMethod.POST)
    private Map<String,Object> updateByPrimaryKey(AdoptHandleOperation adoptHandleOperation){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", adoptHandleOperationService.updateAdoptHandleOperation(adoptHandleOperation));
        return modelMap;
    }




}
