package com.myproject.eshop.demos.web.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
    * Description:
    * Author:
    * Data:
    */
   public class CodeGeneraTor {
   public static void main(String[] args) {
      FastAutoGenerator.create("jdbc:mysql://localhost:3306/eshop?serverTimezone=GMT%2B8", "root", "20030819hxt=")
              .globalConfig(builder -> {
                 builder.author("hxt") // 设置作者
                         .outputDir("src/main/java"); // 输出目录
              })
              .packageConfig(builder -> {
                 builder.parent("com.myproject.eshop.demos.web") // 设置父包名
                         .entity("model") // 设置实体类包名
                         .mapper("mapper") // 设置 Mapper 接口包名
                         .service("service") // 设置 Service 接口包名
                         .serviceImpl("service.impl") // 设置 Service 实现类包名
                         .xml("mappers"); // 设置 Mapper XML 文件包名
              })
              .strategyConfig(builder -> {
                 builder.addInclude("shoppingcart") // 设置需要生成的表名
                         .entityBuilder()
                         .enableLombok() // 启用 Lombok
                         .enableTableFieldAnnotation() // 启用字段注解
                         .controllerBuilder()
                         .enableRestStyle(); // 启用 REST 风格
              })
              .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
              .execute(); // 执行生成
   }

   // 处理 all 情况
   protected static List<String> getTables(String tables) {
      return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
   }
   }
