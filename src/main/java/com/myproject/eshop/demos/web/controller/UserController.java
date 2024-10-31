package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Auth.JWT;
import com.myproject.eshop.demos.web.Result.UserVo;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.mapper.UserMapper;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.UserService;
import com.myproject.eshop.demos.web.utils.BusinessExp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/user")
   public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
   private UserMapper userMapper;

   @Autowired
   private VerCodeController verCodeController;
   @Autowired
   private OrderController orderController;

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
      user.setToken(JWT.createToken(user.getUsername(), user.getRoleid()));
      System.out.println("User"+user);
      if(user.getPassword() == null) return res.fail("账号或密码错误");
      userService.updateById(user);
      return res.success("登录成功",new UserVo(user));
   }

   //注册用户
   @PostMapping("/signin")
   public res SignIN(String uid,String name,String password,String username,int sex,int age,int roleid,String phone){
      if(userService.getByUsername(username) != null) return res.fail("该用户名已存在");
      User user = new User();
      user.setUid(uid);
      user.setUsername(username);
      user.setName(name);
      user.setPassword(password);
      user.setSex(sex);
      user.setAge(age);
      user.setRoleid(roleid);
      user.setPhone(phone);
      if(userService.save(user)) return res.success("注册成功",user);
      return res.fail("注册失败");
   }

   //修改除密码外的信息
   @PostMapping("/modUserInfo")
   public res modUserInfo(int id, String username, int age, int sex, String name,String phone,String uid){
      User user = userService.getById(id);
      if(!Objects.equals(uid, "undefined")){
         user.setUid(uid);
      }
      user.setUsername(username);
      user.setAge(age);
      user.setSex(sex);
      user.setName(name);
      user.setPhone(phone);
      System.out.println(user);
      if(userService.updateById(user))
         return res.success("修改成功", new UserVo(user));
      throw new BusinessExp("修改失败");
   }

   //修改密码
   @PostMapping("/modPassword")
   public res modPassword(String username, String password,String newPassword){
      User user= userService.LoginCheck(username, password);
      System.out.println(user);
      if(user.getUsername() == null) return res.fail("原密码错误！");
      user.setPassword(newPassword);
      try {
         userService.updateById(user);
         return res.success("修改成功",new UserVo(user));
      } catch (Exception e) {
         throw new BusinessExp("修改失败");
      }
   }

   @GetMapping("/checkLogin")
   public Boolean checkLogin(HttpServletRequest request){

      String token = request.getHeader("token");
//      System.out.println(token);
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey("5efc9a").parseClaimsJws(token);
//        System.out.println(claimsJws.getBody());
      String username=claimsJws.getBody().get("username").toString();
//        System.out.println(username);
//        System.out.println(userService.getByUsername(username).getToken());
      if (JWT.checkToken(token)&& Objects.equals(userMapper.getByUsername(username).getToken(), token))
         return true;
      else return JWT.checkToken(token) && Objects.equals(userMapper.getByUsername(username).getToken(), null);
   }
}
