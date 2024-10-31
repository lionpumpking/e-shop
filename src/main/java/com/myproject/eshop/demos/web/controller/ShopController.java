package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.mapper.ShopMapper;
import com.myproject.eshop.demos.web.mapper.ShoptypeMapper;
import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.model.Shoptype;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.ShopService;
import com.myproject.eshop.demos.web.service.ShoptypeService;
import com.myproject.eshop.demos.web.service.UserService;
import com.myproject.eshop.demos.web.utils.BusinessExp;
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
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShoptypeService shoptypeService;

    @Autowired
    private UserService userService;

    //获取所有店铺
    @PostMapping("/shopList")
    public res shopList() {
        List<Shop> shopList = shopService.list();
        if(!shopList.isEmpty()){
            return res.success("查询成功",shopList);
        }
        else return res.fail("没有店铺");
    }

    //根据类型查询店铺
    @GetMapping("/searchByType")
    public res serachShop(int shoptypeid){
        List<Shop> shoplist = shopMapper.searchByType(shoptypeid);
         if(!shoplist.isEmpty()){
             return res.success("查询成功",shopMapper.searchByType(shoptypeid));
         }
         else return res.fail("查询失败");
    }

    //申请新的店铺
    @PostMapping("/addShop")
    public res addShop(int ownerid,int shoptypeid,String description ,String name,double lat,double lon,String shopping){
        Shop shop = shopService.getByOwnerUsername(String.valueOf(ownerid));
        if(shop!=null) return res.fail("您已申请过店铺");
        shop = new Shop();
        shop.setOwnerusername(String.valueOf(ownerid));
        shop.setLon(lon);
        shop.setLat(lat);
        shop.setShoptypeid(shoptypeid);
        shop.setShopimg(shopping);
        shop.setShopdescription(description);
        shop.setShopname(name);
        Shoptype type = shoptypeService.getById(shoptypeid);
        shop.setShoptype(type.getShoptype());
        shop.setIsvalid(0);
        if(shopService.save(shop)){
            return res.success("申请成功，请等待审核",shop);
        }
        else return res.fail("申请失败");
    }

    //删除店铺
    @PostMapping("/removeShop")
    public res removeShop(Shop shop){
        try {
            shopMapper.removeById(shop.getId());
            return res.success("删除成功",shop);
        } catch (Exception e) {
            throw new BusinessExp("删除失败");
        }
    }

    //审核店铺
    @PostMapping("/auditShop")
    public res auditShop(int shopid, int isvalid){
        Shop shop = shopService.getById(shopid);
        if(shop==null) return res.fail("该店铺不存在");
        shop.setIsvalid(isvalid);
        if(shopService.updateById(shop)){
            User user = userService.getById(Integer.valueOf(shop.getOwnerusername()));
            user.setRoleid(0);
            userService.updateById(user);
            return res.success("修改成功",shop);
        }
        return res.fail("修改失败");
    }

    //修改店铺信息
    @PostMapping("/updateShop")
    public res updateShop(Shop shop){
        try {
            shopMapper.updateById(shop);
            return res.success("修改成功",shop);
        } catch (Exception e) {
            throw new BusinessExp("修改失败");
        }
    }

}
