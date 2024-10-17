package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.UserVo;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.mapper.UserMapper;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.UserService;
import com.myproject.eshop.demos.web.utils.BusinessExp;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@RestController
@RequestMapping("/user")
   public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
   private UserMapper userMapper;

   @Autowired
   private VerCodeController verCodeController;

//   test
//   @GetMapping("/get")
//   public User get(){
//      return userService.getById(1);
//   }

   //登录用户
   @PostMapping("/login")
   public res LoginCheck(HttpSession session, String username, String password, String verCode){
      if(!verCodeController.loginVerCode(session,verCode))
         return res.fail("验证码错误");
      User user =userService.LoginCheck(username, password);
      if(user == null) return res.fail("账号或密码错误");
      return res.success("登录成功",new UserVo(user));
   }

   //注册用户
   @PostMapping("/signin")
   public res SignIN(@RequestBody User user){
      if(userMapper.getByUsername(user.getUsername()) != null){
         return res.fail("该用户名已存在");
      }
      userService.save(user);
      return res.success("注册成功", new UserVo(user));
   }

   //修改除密码外的信息
   @PostMapping("/modUserInfo")
   public res modUserInfo(@RequestBody User user){
      User newUser = userService.getById(user.getId());
      user.setPassword(newUser.getPassword());
      if(userService.save(user))
         return res.success("修改成功", new UserVo(user));
      throw new BusinessExp("修改失败");
   }

   //修改密码
   @PostMapping("/modPassword")
   public res modPassword(String username, String password,String newPassword){
      User user= userService.LoginCheck(username, password);
      if(user == null) return res.fail("原密码错误！");
      user.setPassword(newPassword);
      try {
         userService.updateById(user);
         return res.success("修改成功",new UserVo(user));
      } catch (Exception e) {
         throw new BusinessExp("修改失败");
      }
   }
}
