package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.MsgVo;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Msg;
import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.ShopService;
import com.myproject.eshop.demos.web.service.UserService;
import com.myproject.eshop.demos.web.service.impl.MsgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-11-13
 */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgServiceImpl myService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @PostMapping("/send")
    public res sendMsg(String msg,int fromid,int toid,int type){
        System.out.println(System.currentTimeMillis());
        Msg message = new Msg();
        message.setMsg(msg);
        message.setToid(toid);
        message.setFromid(fromid);
        message.setType(type);
        message.setTime(System.currentTimeMillis());
        myService.save(message);
        return res.success("消息已发送",null);
    }

    @PostMapping("/connect")
    public res connect(String msg,int fromid,int shopid,int type){
        Shop shop = shopService.getById(shopid);
        System.out.println(shop);
        int toid = Integer.parseInt(shop.getOwnerusername());
        Msg message = new Msg();
        if(fromid == toid){
            return res.fail("无法与自身聊天");
        }
        message.setMsg(msg);
        message.setToid(toid);
        message.setFromid(fromid);
        message.setType(type);
        message.setTime(System.currentTimeMillis());
        myService.save(message);
        return res.success("消息已发送",null);
    }

    @PostMapping("/getList")
    public res getMsgList(int id){
        List<Msg> list = myService.getMsgListAll(id);
        List<User> result = new ArrayList<>();
        for(Msg msg : list){
            if(msg.getFromid() == id){
            User user = userService.getById(msg.getToid());
            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setUsername(user.getUsername());
            if(!result.contains(newUser)){
                result.add(newUser);
            }
            }else if(msg.getToid() == id){
                User user = userService.getById(msg.getFromid());
                User newUser = new User();
                newUser.setId(user.getId());
                newUser.setUsername(user.getUsername());
                if(!result.contains(newUser)){
                    result.add(newUser);
                }
            }
        }
        return res.success("查询成功",result);
    }

    @PostMapping("/get")
    public res getMsg(int fromid,int toid){
        List<Msg> list = myService.getMsgList(fromid,toid);
        List<MsgVo> result = new ArrayList<>();
        for (Msg msg:list){
            if(msg.getFromid() == fromid){
                MsgVo msgVo = new MsgVo(msg,true);
                result.add(msgVo);
            }
            else if(msg.getToid() == fromid){
                MsgVo msgVo = new MsgVo(msg,false);
                result.add(msgVo);
            }
        }
        return res.success("查询成功",result);
    }
}
