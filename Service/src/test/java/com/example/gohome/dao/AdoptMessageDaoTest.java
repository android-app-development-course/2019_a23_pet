package com.example.gohome.dao;


import com.example.gohome.entity.AdoptAppliment;
import com.example.gohome.entity.AdoptMessage;
import com.example.gohome.entity.UserMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdoptMessageDaoTest {

    @Autowired
    private AdoptMessageMapper adoptMessageMapper;


    @Test
    public void updateAdoptMessage() {
        AdoptMessage adoptMessage = new AdoptMessage();
        adoptMessage.setState(1);
        adoptMessage.setAdoptId(1);

        int effectedNum = adoptMessageMapper.updateByPrimaryKeySelective(adoptMessage);
        assertEquals(1,effectedNum);
    }


}
