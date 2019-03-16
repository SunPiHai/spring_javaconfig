package com.chen.demo.config;

import com.chen.demo.dao.UserDao;
import com.chen.demo.dao.impl.UserDaoCache;
import com.chen.demo.dao.impl.UserDaoNormal;
import com.chen.demo.service.UserService;
import com.chen.demo.service.impl.UserServiceNormal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyong uthor: chen
 * @date : 2019/3/15
 */
@Configuration
public class AppConfig {
    @Bean
    public UserDao userDaoNormal(){
        System.out.println("创建UserDaoNormal对象");
        return new UserDaoNormal();
    }
     @Bean
    public UserDao userDaoCache() {
         System.out.println("创建UserDaoCache对象");
         return new UserDaoCache();
     }
    @Bean
    public UserService userServiceNormal(@Qualifier("userDaoNormal") UserDao userDao){
        System.out.println("创建一个UserService对象");
        return new UserServiceNormal(userDao);
    }
}
