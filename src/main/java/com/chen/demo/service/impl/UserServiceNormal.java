package com.chen.demo.service.impl;

import com.chen.demo.dao.UserDao;
import com.chen.demo.service.UserService;

/**
 * @author chenyong uthor: chen
 * @date : 2019/3/15
 */
public class UserServiceNormal implements UserService {
    private UserDao userDao;
    public UserServiceNormal(){super();}

    public UserServiceNormal(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void add(){
        userDao.add();
    }
}
