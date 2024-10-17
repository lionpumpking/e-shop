package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Shoppingcart;
import com.myproject.eshop.demos.web.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/shoppingcart")
public class ShoppingcartController {

    @Autowired
    private ShoppingcartService shoppingcartService;

    //用户加入商品到购物车
    @PostMapping("/addShoppingcart")
    public res addShoppingcart(int id,int userid,int productid,int shopid){
        Shoppingcart shopping = new Shoppingcart();
        shopping.setId(id);
        shopping.setUserid(userid);
        shopping.setProductid(productid);
        shopping.setShopid(shopid);
        if(shoppingcartService.save(shopping))
            return res.success("加入购物车成功",shopping);
        else return res.fail("加入购物车失败");
    }

    //用户从购物车中删除商品
    @PostMapping("/deleteShoppingcart")
    public res removeShopping(int id,int userid){
        if(shoppingcartService.removeById(id))
            return res.success("从购物车中删除成功",true);
        else return res.fail("从购物车中删除失败");
    }

    //商家获取将自家商品加入购物车的用户
    @PostMapping("/merchantGet")
    public res merchantGet(int shopid){
        List<Shoppingcart> shoppings = shoppingcartService.getByShopid(shopid);
        if(shoppings.isEmpty()){
            return res.fail("该商家商品暂无人加入购物车");
        }
        return res.success("商家加入的商品",shoppings);
    }
}
