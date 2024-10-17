package com.myproject.eshop.demos.web.Result;

import lombok.Data;

/**
    * Description:
    * Author:
    * Data:
    */
@Data
   public class res {
      private int code;
      private String msg;
      private Object data;

      public static res success( String msg, Object data){
         return result(2000 ,msg, data);
      }

      public static res fail( String msg){
         return result(4000 ,msg, null);
      }

    public static res fail(){
        return result(4000 ,"非法操作", null);
    }

      private static res result(int code, String msg, Object data){
          res result = new res();
          result.setCode(code);
          result.setMsg(msg);
          result.setData(data);
          return result;
      }
   }
