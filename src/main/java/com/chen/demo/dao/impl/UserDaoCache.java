package com.chen.demo.dao.impl;

import com.chen.demo.dao.UserDao;

/**
 * @author chenyong uthor: chen
 * @date : 2019/3/15
 */
public class UserDaoCache implements UserDao {
    @Override
    public void add() {
        System.out.println("添加到缓存中");
    }
}
