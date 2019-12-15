package com.example.gohome.dao;


import com.example.gohome.entity.AdoptHandleOperation;
import com.example.gohome.entity.AdoptMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdoptHandleOperationDaoTest {

    @Autowired
    private AdoptHandleOperationMapper adoptHandleOperationMapper;


    @Test
    public void insetAdoptHandleOperation() {
        AdoptHandleOperation adoptHandleOperation = new AdoptHandleOperation();
        adoptHandleOperation.setState(1);  //设置为成功领养
        adoptHandleOperation.setInfoId(6);
        adoptHandleOperation.setDescription("成功领养！！！");

        int effectedNum = adoptHandleOperationMapper.insertSelective(adoptHandleOperation);
        assertEquals(1,effectedNum);
    }
}
