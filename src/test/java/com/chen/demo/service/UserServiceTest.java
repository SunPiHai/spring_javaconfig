package com.chen.demo.service;

import com.chen.demo.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chenyong uthor: chen
 * @date : 2019/3/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void addTest(){
        userService.add();
    }
}
