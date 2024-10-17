package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Order;
import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.service.OrderService;
import com.myproject.eshop.demos.web.service.ProductService;
import com.myproject.eshop.demos.web.utils.BusinessExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    //下单
    @PostMapping("/GetOrder")
    public res GetOrder(Order order) {
        double price=0;
        Product product = productService.getById(order.getProductid());
        if (product == null) {
            return res.fail("该商品已下架");
        }
        if(product.getQuantity() <= order.getNumber()){
            return res.fail("库存不足");
        }
        price = product.getPrice() * order.getNumber()+product.getFreight();
        return res.success("下单成功",price);
        //判断微信支付是否成功
        //成功
//        product.setQuantity(product.getQuantity() - order.getNumber());
//        productService.updateById(product);
//        if(orderService.save(order)){
//            order.setState(0);
//            return res.success("下单成功",price);
//            }
//        return res.fail("下单失败");
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
     public res userGetOrder(int id){
        List<Order> orders = orderService.getByOwner(id);
        if(orders.isEmpty()){
            return res.fail("该用户没有订单");
        }
        return res.success("查询成功",orders);
     }

     //商家获取店铺订单
     @PostMapping("/shopGetOrder")
     public res shopGetOrder(int shopid){
        List<Order> orders = orderService.ShopGetOrder(shopid);
        if(orders.isEmpty()){
            return res.fail("该商家没有订单");
        }
        return res.success("查询成功",orders);
     }
}
