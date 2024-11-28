package com.myproject.eshop.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.eshop.demos.web.model.User;
import org.springframework.stereotype.Service;

/**
    * Description:
    * Author:
    * Data:
    */
   public interface UserService extends IService<User> {
      User LoginCheck(String username, String password);
      User getByUsername(String username);
   }
