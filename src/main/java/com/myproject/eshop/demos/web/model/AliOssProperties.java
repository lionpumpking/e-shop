package com.myproject.eshop.demos.web.model;

/**
    * Description:
    * Author:
    * Data:
    */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss的配置属性类
 */
@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {

   private String endpoint;
   private String accessKeyId;
   private String accessKeySecret;
   private String bucketName;

}
