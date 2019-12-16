package com.example.gohome.service.Impl;

import com.example.gohome.dao.MemberMessageMapper;
import com.example.gohome.entity.MemberMessage;
import com.example.gohome.entity.ResponseEntity.ResponseMemberMessage;
import com.example.gohome.service.MemberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MemberMessageServiceImpl implements MemberMessageService {

    @Autowired
    MemberMessageMapper memberMessageMapper;

    @Override
    public boolean insertMemberMessage(MemberMessage memberMessage) {
        if(memberMessage.getUserId()!=null && memberMessage.getAreaId()!=null && memberMessage.getUserId()!=null){
            try{
                memberMessage.setCreated(new Date());
                int effectNum = memberMessageMapper.insert(memberMessage);
                if(effectNum > 0){
                    return true;
                }else{
                    throw new RuntimeException("服务器错误，添加组员信息失败");
                }
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else{
            throw new RuntimeException("组员信息不完整！");
        }
    }

    @Override
    public boolean updateMemberMessage(MemberMessage memberMessage) {
        if(memberMessage.getUserId()!=null && memberMessage.getAreaId()!=null && memberMessage.getUserId()!=null){
            try {
                memberMessage.setCreated(new Date());
                int effectNum = memberMessageMapper.updateByPrimaryKeySelective(memberMessage);
                if(effectNum > 0){
                    return true;
                }else{
                    throw new RuntimeException("添加组员信息失败");
                }
            }catch (Exception e){
                throw new RuntimeException("服务器操作错误: " + e.getMessage());
            }
        }else{
            throw new RuntimeException("组员信息不完整！");
        }
    }

    @Override
    public MemberMessage memberMessageByUserId(Integer userId) {
        MemberMessage memberMessage = null;
        if(userId!=null){
            try {
                memberMessage = memberMessageMapper.selectByUserId(userId);
            }catch (Exception e){
                throw new RuntimeException("服务器错误，获取组员信息失败");
            }
        }
        return memberMessage;
    }

    @Override
    public MemberMessage memberMessageByMessageId(Integer memberId) {
        MemberMessage memberMessage = null;
        if(memberId!=null){
            try {
                memberMessage = memberMessageMapper.selectByPrimaryKey(memberId);
            }catch (Exception e){
                throw new RuntimeException("服务器错误，获取组员信息失败");
            }

        }
        return memberMessage;
    }

    @Override
    public List<ResponseMemberMessage> queryMemberMessageByAreaId(Integer areaId) {
        List<ResponseMemberMessage> ResponseMemberMessage = null;
        if(areaId!=null){
            try {
                ResponseMemberMessage = memberMessageMapper.queryMemberMessageByAreaId(areaId);
            }catch (Exception e){
                throw new RuntimeException("服务器错误：" + e.getMessage());
            }
        }
        return ResponseMemberMessage;
    }
}
