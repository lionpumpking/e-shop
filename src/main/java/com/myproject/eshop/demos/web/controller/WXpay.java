package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Order;
import com.myproject.eshop.demos.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
    * Description:
    * Author:
    * Data:
    */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("WXpay")
   public class WXpay {

   @Autowired
   private OrderService orderService;

   //假微信支付，真的懒得申请，麻烦
   @PostMapping("/pay")
   public res pay(int id ,int price){
      Order order = orderService.getById(id);
      order.setState(0);
      if(orderService.updateById(order))
         return res.success("支付成功",order);
      return res.fail("支付失败");
   }
}
