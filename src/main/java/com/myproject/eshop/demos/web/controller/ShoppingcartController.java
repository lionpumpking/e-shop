package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.Result.shoppingCartVo;
import com.myproject.eshop.demos.web.mapper.ShopMapper;
import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.model.Shoppingcart;
import com.myproject.eshop.demos.web.service.ProductService;
import com.myproject.eshop.demos.web.service.ShopService;
import com.myproject.eshop.demos.web.service.ShoppingcartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/shoppingcart")
public class ShoppingcartController {

    @Autowired
    private ShoppingcartService shoppingcartService;

    @Autowired
    private ProductService productsService;

    @Autowired
    private ShopService shopService;

    //用户加入商品到购物车
    @PostMapping("/addShoppingcart")
    public res addShoppingcart(int userid,int productid){
        Shoppingcart shopping = new Shoppingcart();
        shopping.setUserid(userid);
        shopping.setProductid(productid);
        Product product = productsService.getById(productid);
        shopping.setShopid(product.getOwnershopid());
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

    //用户获取自己的购物车中的商品
    @GetMapping("/cartByUser")
    public res cartByUser(@Param("userid") int userid){
        List<Shoppingcart> shoppings = shoppingcartService.getByUserId(userid);
        if(shoppings.isEmpty()){
            return res.fail("您的购物车为空");
        }
        List<shoppingCartVo>  result = new ArrayList<>();
        for(Shoppingcart s : shoppings){
            Product product = productsService.getById(s.getProductid());
            shoppingCartVo vo = new shoppingCartVo(s.getId(),s.getUserid(),product.getName(),product.getPrice(),product.getImg(),product.getDescription());
            result.add(vo);
        }
        return res.success("您的购物车中的商品",result);
    }

    //商家获取将自家商品加入购物车的用户
    @PostMapping("/merchantGet")
    public res merchantGet(int userid){
        Shop shop = shopService.getByOwnerUsername(String.valueOf(userid));
        System.out.println(shop);
        List<Shoppingcart> shoppings = shoppingcartService.getByShopid(shop.getId());
        if(shoppings.isEmpty()){
            return res.fail("该商家商品暂无人加入购物车");
        }
        List<shoppingCartVo> result =  new ArrayList<>();
        for(Shoppingcart s : shoppings){
            System.out.println(s);
            Product product = productsService.getById(s.getProductid());
            shoppingCartVo vo = new shoppingCartVo(s.getId(),s.getUserid(),product.getName(),product.getPrice(),product.getImg(),product.getDescription());
            result.add(vo);
        }
        return res.success("商家加入的商品",result);
    }
}
