package com.myproject.eshop.demos.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
    * Description:
    * Author:
    * Data:
    */
@Data
@TableName("user")
   public class User {
      private int id;
      private int uid;
      private String username;
      private String name;
      private String password;
      private int sex;
      private int age;
      private int roleid;
   }
