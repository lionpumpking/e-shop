package com.myproject.eshop.demos.web.utils;

import com.myproject.eshop.demos.web.Result.res;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author  胡兴涛
 * @Date  2024/10/17
*/

@ControllerAdvice
public class CommonExpHandlerController{
   @ExceptionHandler(RuntimeException.class)
   @ResponseBody
   //系统异常处理
   public res handle(RuntimeException e){
      e.printStackTrace();
      //return JsonResult.fail();
      return res.fail();
   }
   //自定义异常
   //手动抛出异常为throw new BusinessExp("msg")
   public res handle(BusinessExp e){
      e.printStackTrace();
      //return JsonResult.fail();
      return res.fail(e.getMessage());
   }
}
