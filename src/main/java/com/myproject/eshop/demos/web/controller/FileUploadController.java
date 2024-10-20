package com.myproject.eshop.demos.web.controller;

/**
    * Description:
    * Author:
    * Data:
    */
import com.myproject.eshop.demos.web.service.impl.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author hxt
 * @Date 2024/10/1 下午3:23
 * Description:
 */

@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/file")
public class FileUploadController {

   @Autowired
   private OssConfig ossConfig;

   @PostMapping("/upload")
   public String uploadFile(@RequestParam("file") MultipartFile file,
                            @RequestParam("directory") String directory,
                            @RequestParam("name") String name) {

      try {
         return ossConfig.uploadFile(file, directory, name);
      } catch (IOException e) {
         e.printStackTrace();
         return "上传失败";
      }
   }

}
