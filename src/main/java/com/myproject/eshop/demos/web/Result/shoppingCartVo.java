package com.myproject.eshop.demos.web.Result;

import lombok.Data;

/**
    * Description:
    * Author:
    * Data:
    */
@Data
   public class shoppingCartVo {

      //购物车列表id
      private int id;
      // 用户id
   private int userid;
      //商品名称
      private String productName;
      //商品价格
      private double price;
      //商品图像
      private String img;
      // 商品描述
      private String description;

      public shoppingCartVo(int id,int userid, String productName, double price, String img ,String description) {
         this.id=id;
         this.userid = userid;
         this.productName = productName;
         this.price = price;
         this.img = img;  // 假设 img 为空字符串
         this.description = description;  // 假设 description 为空字符串
      }

   }
