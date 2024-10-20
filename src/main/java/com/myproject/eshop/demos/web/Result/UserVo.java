package com.myproject.eshop.demos.web.Result;

import com.myproject.eshop.demos.web.model.User;
import lombok.Data;

/**
    * Description:
    * Author:
    * Data:
    */
@Data
   public class UserVo {
   private int id;
   private String uid;
   private String username;
   private String name;
   private int sex;
   private int age;
   private int roleid;
   private String Token;
   private String phone;

   //uid已经用来存储头像
   public UserVo(User user) {
      this.id = user.getId();
      this.uid = user.getUid();
      this.username = user.getUsername();
      this.name = user.getName();
      this.sex = user.getSex();
      this.age = user.getAge();
      this.roleid = user.getRoleid();
      this.Token = user.getToken();
      this.phone = user.getPhone();
   }
   }
