package com.myproject.eshop.demos.web.controller;

import cn.hutool.core.util.RandomUtil;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.service.IExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
    * Description:
    * Author:
    * Data:
    */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/express")
   public class expressController {

   @Autowired
   private IExpressService expressService;

   //获取所有快递公司
   @GetMapping("/geyAll")
   public res getAll(){
      return res.success("查询成功",expressService.list());
   }

   //根据快递公司生成假快递号
   @GetMapping("/expressNumber")
   public String expressNumber(String abbreviation){
      String pre = String.valueOf(RandomUtil.randomInt(1000000,9999999));
      String last = String.valueOf(RandomUtil.randomInt(1000000,9999999));
      return abbreviation + pre + last;
   }
}
