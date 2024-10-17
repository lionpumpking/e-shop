package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.mapper.UserMapper;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
    * Description:
    * Author:
    * Data:
    */
@RestController
@RequestMapping("/user")
   public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
   private UserMapper userMapper;

//   test
//   @GetMapping("/get")
//   public User get(){
//      return userService.getById(1);
//   }

   @GetMapping("/login")
   public User LoginCheck(String username, String password){
      return userMapper.LoginCheck(username, password);
   }

}
