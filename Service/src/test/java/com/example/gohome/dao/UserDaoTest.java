package com.example.gohome.dao;

import com.example.gohome.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao uDao;

    @Test
    public void queryUser() {
        List<User>  userList = uDao.queryUser();
        assertEquals(1,userList.size());
    }

    @Test
    public void queryUserByUserId() {
        User u = uDao.queryUserByUserId(1);
        assertEquals("哈哈",u.getUserName());
    }

    @Test
    public void insertUser() {
        User u = new User();
        u.setUserType(1);
        u.setTelephone("13889856476");
        u.setAddress("广州天河");
        u.setGender("1");
        u.setUserName("哈哈");
        u.setUserPassword("123456");
        int effectedNum = uDao.insertUser(u);
        assertEquals(1,effectedNum);
    }

    @Test
    public void updateUser() {
        User u = new User();
        u.setUserType(1);
        u.setUserId(1);
        u.setUserName("kaka");
        int effectedNum = uDao.updateUser(u);
        assertEquals(1,effectedNum);
    }

    @Test
    public void deleteUser() {
        int effectedNum = uDao.deleteUser(1);
        assertEquals(1,effectedNum);
    }

}