package com.example.gohome.dao;


import com.example.gohome.entity.HelpAppliment;
import com.example.gohome.service.HelpApplimentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelpApplimentDaoTest {

    @Autowired
    private HelpApplimentMapper helpApplimentMapper;

    @Test
    public void insertHelpAppliment() {
        HelpAppliment helpAppliment = new HelpAppliment();
        helpAppliment.setState(0);
        helpAppliment.setTelephone("13889856476");
        helpAppliment.setAddress("广州天河");
        helpAppliment.setApplimentId(2);
        helpAppliment.setApplyName("小话");
        helpAppliment.setDescription("想领养");
        helpAppliment.setUserId(1);
        helpAppliment.setPetType(0);
        helpAppliment.setGender(1);
        helpAppliment.setSteriled(false);
        helpAppliment.setVaccinate(true);
        helpAppliment.setApplyDate(new Date(2019,12,18));
        int effectedNum = helpApplimentMapper.insert(helpAppliment);
        assertEquals(1,effectedNum);
    }
}
