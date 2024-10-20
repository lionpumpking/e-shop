package com.myproject.eshop.demos.web.service.impl;

/**
    * Description:
    * Author:
    * Data:
    */
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author 优
 * @Date 2024/10/1 下午3:22
 * Description:
 */

@Service
public class OssConfig {

   @Value("${aliyun.oss.endpoint}")
   private String endpoint;

   @Value("${aliyun.oss.accessKeyId}")
   private String accessKeyId;

   @Value("${aliyun.oss.accessKeySecret}")
   private String accessKeySecret;

   @Value("${aliyun.oss.bucketName}")
   private String bucketName;


   //directory 路径 name 拼接的前缀名
   public String uploadFile(MultipartFile file, String directory, String customFileName) throws IOException {
      // 创建OSSClient实例
      OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

      // directory是前端传来文件夹的路径
      // customFileName是前端传来的文件名字
      // 使用自定义文件名，确保包括文件扩展名
      String objectName = directory + "/" + customFileName +  "." + UUID.randomUUID() + "_" + file.getOriginalFilename();
      // 最终的文件名字是, (前端传来的文件名字).uuid_(原文件名字), 需要怎样的文件名可自己修改

      try (InputStream inputStream = file.getInputStream()) {
         // 上传文件
         ossClient.putObject(bucketName, objectName, inputStream);
      } finally {
         // 关闭OSSClient
         ossClient.shutdown();
      }
      return "https://" + bucketName + "." + endpoint + "/" + objectName; // 返回文件的URL
   }

}
