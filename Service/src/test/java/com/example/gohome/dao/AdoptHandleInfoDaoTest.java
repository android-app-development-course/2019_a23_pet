package com.example.gohome.dao;


import com.example.gohome.entity.AdoptHandleInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdoptHandleInfoDaoTest {


    @Autowired
    private AdoptHandleInfoMapper adoptHandleInfoMapper;


    @Test
    public void selectHandleInfo() {

        List<AdoptHandleInfo> adoptHandleInfoList = adoptHandleInfoMapper.selectByHandleId(1);
        assertEquals(1,adoptHandleInfoList.size());
    }
}
