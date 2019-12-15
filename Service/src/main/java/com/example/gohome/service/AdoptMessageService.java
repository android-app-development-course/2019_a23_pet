package com.example.gohome.service;

import com.example.gohome.entity.AdoptMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AdoptMessageService {

    boolean insertAdoptMessage(AdoptMessage adoptMessage);

    boolean deleteAdoptMessage(int adoptId);

    boolean updateAdoptMessageState(AdoptMessage adoptMessage);

    /**以下为未使用、未测试方法*/
//    Map<String, Object> queryAdoptMessage(Integer pageNum, Integer pageSize);
//
//    Map<String, Object> queryAdoptMessageByState(Integer pageNum, Integer pageSize, Integer state);
//
//    Map<String, Object> queryAdoptMessageByStateHandleId(Integer pageNum, Integer pageSize, Integer state, Integer handleId);
//
//    AdoptMessage adoptAdoptMessageByAdoptId(Integer adoptId);
//
//    boolean updateAdoptMessageByState(Integer adoptId, Integer handleId, Integer state, String description);

}
