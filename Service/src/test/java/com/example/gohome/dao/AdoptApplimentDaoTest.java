package com.example.gohome.dao;


import com.example.gohome.entity.AdoptAppliment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdoptApplimentDaoTest {
    @Autowired
    private AdoptApplimentMapper adoptApplimentMapper;

    @Test
    public void insertAdoptAppliment() {
        AdoptAppliment adoptAppliment = new AdoptAppliment();
        adoptAppliment.setState(0);
        adoptAppliment.setTelephone("13889856476");
        adoptAppliment.setAddress("广州天河");
        adoptAppliment.setAdoptId(1);
        adoptAppliment.setApplimentId(2);
        adoptAppliment.setApplyName("洛洛");
        adoptAppliment.setDescription("想领养");
        adoptAppliment.setJob("医生");
        adoptAppliment.setUserId(1);
        int effectedNum = adoptApplimentMapper.insert(adoptAppliment);
        assertEquals(1,effectedNum);
    }

}
