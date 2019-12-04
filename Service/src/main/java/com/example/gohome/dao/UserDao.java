package com.example.gohome.dao;

import com.example.gohome.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao {
    /* 列出用户列表*/
    List<User> queryUser();

    /*根据用户的userID搜索某个用户*/
    User queryUserByUserId(Integer userID);

    /* 插入单个用户*/
    int insertUser(User u);

    /* 更新用户信息*/
    int updateUser(User u);

    /* 删除用户*/
    int deleteUser(int userId);


}
