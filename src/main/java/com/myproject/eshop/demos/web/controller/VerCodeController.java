package com.myproject.eshop.demos.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@RestController
@RequestMapping("/verCode")
public class VerCodeController {

//   前端获取验证码
   @GetMapping("get")
   public void verCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
      ShearCaptcha verCode = CaptchaUtil.createShearCaptcha(140, 33, 4, 2);
      verCode.write(response.getOutputStream());
      String code = verCode.getCode();
      System.out.println("verCode: " + code);
      request.getSession().setAttribute("verCode", code);
   }

//   后端校验验证码
//   @PostMapping("check")
   public Boolean loginVerCode(HttpSession session, String verCode) {
      String captureCode = (String) session.getAttribute("verCode");
      return captureCode.equals(verCode);
   }
}
