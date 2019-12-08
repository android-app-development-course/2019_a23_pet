package com.example.gohome.service.Impl;

import com.example.gohome.dao.UserMessageDao;
import com.example.gohome.entity.UserMessage;
import com.example.gohome.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserMassageServiceImpl implements UserMessageService {

    @Autowired
    UserMessageDao userMessageDao;

    @Override
    public boolean insertUserMessage(UserMessage userMessage) {
        if(userMessage.getUserName()!=null && userMessage.getUserPassword()!=null && userMessage.getAddress()!=null){
            try{
                int effectNum = userMessageDao.insertUser(userMessage);
                if(effectNum > 0){
                    return true;
                }else{
                    throw new RuntimeException("服务器错误，注册用户失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误，注册用户失败！");
            }
        }else{
            throw new RuntimeException("用户信息不完整！");
        }
    }

    @Override
    public boolean updateUserMessage(UserMessage userMessage) {
        //用户userId不可为空
        if(userMessage.getUserId()!=null){
            try{
                int effectNum = userMessageDao.updateUser(userMessage);
                if(effectNum > 0){
                    return true;
                }else{
                    throw new RuntimeException("服务器错误，修改用户信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误，注册用户失败！");
            }
        }else{
            throw new RuntimeException("用户信息不完整！");
        }
    }

    @Override
    public UserMessage userMessageByUserName(String userName) {
        if(!userName.equals("")){
            try {
                UserMessage userMessage = userMessageDao.queryUserByUserName(userName);
                return userMessage;
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else {
            throw new RuntimeException("用户名错误，获取用户信息失败");
        }
    }

    @Override
    public UserMessage userMessageByUserId(Integer userId) {
        if(userId != null){
            try {
                UserMessage userMessage = userMessageDao.queryUserByUserId(userId);
                return userMessage;
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else {
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public UserMessage userMessageByUserPassword(String userName, String userPassword) {
        if(!userName.equals("") && !userPassword.equals("")){
            try {
                UserMessage userMessage = userMessageDao.queryUserByUserPassword(userName, userPassword);
                return userMessage;
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
