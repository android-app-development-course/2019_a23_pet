package com.example.gohome.service;

import com.example.gohome.entity.UserMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserMessageService {

    boolean insertUserMessage(UserMessage userMessage);

    boolean updateUserMessage(UserMessage userMessage);

    UserMessage userMessageByUserName(String userName);

    UserMessage userMessageByUserId(Integer userId);

    UserMessage userMessageByUserPassword(String userName, String userPassword);

}
