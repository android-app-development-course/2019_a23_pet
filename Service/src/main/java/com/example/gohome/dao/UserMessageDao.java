package com.example.gohome.dao;

import com.example.gohome.entity.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMessageDao {
    /* 列出用户列表*/
    List<UserMessage> queryUser();

    /*根据用户的userID搜索某个用户*/
    UserMessage queryUserByUserId(Integer userId);

    /*根据用户的userName搜索用户*/
    UserMessage queryUserByUserName(String userName);

    /*根据用户的userName和userPassword搜索用户*/
    UserMessage queryUserByUserPassword(String userName, String userPassword);

    /* 插入单个用户*/
    int insertUser(UserMessage u);

    /* 更新用户信息*/
    int updateUser(UserMessage u);

    /* 删除用户*/
    int deleteUser(int userId);


}
