package com.myproject.eshop.demos.web.utils;

/**
 * @Author  胡兴涛
 * @Date  2024/10/17
*/

//自定义异常类
public class BusinessExp extends RuntimeException{
   public BusinessExp(){
   }

   public BusinessExp(String message){
      super(message);
   }
}
