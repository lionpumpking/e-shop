package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.Pay;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.*;
import com.myproject.eshop.demos.web.service.*;
import com.myproject.eshop.demos.web.utils.BusinessExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * @since 2024-10-17
 */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IExpressService expressService;

    @Autowired
    private expressController expressController;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    //下单
    @PostMapping("/GetOrder")
    public res GetOrder(int productid,String destination,int userid,int num) {
//        System.out.println(productid+destination+userid+num);
        double price=0;
        Product product = productService.getById(productid);
        if (product == null) {
            return res.fail("该商品已下架");
        }
        if(product.getQuantity() <= num){
            return res.fail("库存不足");
        }
        Order order = new Order();
        order.setProductid(productid);
        order.setDestination(destination);
        price = product.getPrice() * num+product.getFreight();
        order.setNumber(num);
        order.setOwnerid(userid);
        order.setShopid(product.getOwnershopid());
        order.setState(0);
        orderService.save(order);
        return res.success("总价",price);
    }

    //退款
    @PostMapping("/refund")
     public res refund(int id){
        Order order = orderService.getById(id);
        if(order == null){
            return res.fail("该订单不存在");
        }
        if(order.getState()== 1){
            return res.fail("该订单已完成，无法退款!");
        }
        //判断是否有退货申请
        //申请成功
        order.setState(2);
        if(orderService.updateById(order))
            return res.success("申请退货成功",order);
        throw new BusinessExp("退款申请失败");
     }

     //商家确认订单
     @PostMapping("/acceptOrder")
     public res acceptOrder(int id,String express){
        System.out.println(express);
        Order order = orderService.getById(id);
        if(order == null){
            return res.fail("该订单不存在");
        }
        //确认成功
         if(order.getState()== 0){
             order.setState(4);
        }
         //生成快递单号
         Express newExpress = expressService.getByName(express);
         order.setPresscompany(express);
         order.setPressnumber(expressController.expressNumber(newExpress.getAbbreviation()));
        if(orderService.updateById(order))
            return res.success("确认成功",order);
        throw new BusinessExp("确认失败");
     }

     //商家处理退款订单
     @PostMapping("/acceptOrRefuse")
     public res acceptOrRefuse(boolean state, int id){
        Order order = orderService.getById(id);
        if(state){
            order.setState(-1);
        }else order.setState(3);
        if(orderService.updateById(order)){
            return res.success("处理申请成功",order);
        }
        return res.fail("处理失败");
     }

     //用户获取自己的订单
     @PostMapping("/userGetOrder")
     public res userGetOrder(int id,int state){
        List<Order> orders = orderService.getByOwner(id);
        if(orders.isEmpty()){
            return res.fail("该用户没有订单");
        }
        if(state == -2){
            return res.success("查询成功",orders);
        }
        List<Order> result = new ArrayList<>();
        for(Order order:orders){
            if(order.getState() == state){
                result.add(order);
            }
        }
        return res.success("查询成功",result);
     }

     //商家获取店铺订单
     @PostMapping("/shopGetOrder")
     public res shopGetOrder(int id,int state){
         Shop shop = shopService.getByOwnerUsername(userService.getById(id).getUsername());
        List<Order> orders = orderService.ShopGetOrder(shop.getId());
        if(orders.isEmpty()){
            return res.fail("该商家没有订单");
        }
        if(state == -2)
            return res.success("查询成功",orders);
         List<Order> result = new ArrayList<>();
         for(Order order:orders){
             if(order.getState() == state){
                 result.add(order);
             }
         }
         return res.success("查询成功",result);
     }
}
