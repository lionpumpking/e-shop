package com.myproject.eshop.demos.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.eshop.demos.web.mapper.UserMapper;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
    * Description:
    * Author:
    * Data:
    */
@Service
   public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

   @Autowired
   private UserMapper userMapper;

   @Override
   public User LoginCheck(String username, String password) {
      User user = userMapper.getByUsername(username);
      if(user!= null) {
         return user;
      }
      return new User();
   }
}
