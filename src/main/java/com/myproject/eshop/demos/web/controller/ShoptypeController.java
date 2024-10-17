package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.mapper.ShoptypeMapper;
import com.myproject.eshop.demos.web.model.Shoptype;
import com.myproject.eshop.demos.web.service.ShoptypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@RestController
@RequestMapping("/shoptype")
public class ShoptypeController {

    @Autowired
    private ShoptypeService shoptypeService;

    //新增店铺类型
    @PostMapping("/addShoptype")
    public res addShoptype(String shoptype){
        Shoptype shop = shoptypeService.getByShoptype(shoptype);
        if(shop!=null) return res.fail("该分类已存在");
        else shop.setShoptype(shoptype);
        if(shoptypeService.save(shop))
            return res.success("添加成功",shoptype);
        else return res.fail("添加失败");
    }

    //查询所有店铺类型
    @PostMapping("getShopTypeList")
    public res getShopTypeList(){
        return res.success("查询成功",shoptypeService.list());
    }

    //删除店铺类型
    @PostMapping("removeShopType")
    public res removeShopType(String shoptype){
        Shoptype shop = shoptypeService.getByShoptype(shoptype);
        if(shop==null) return res.fail("该分类不存在");
        if(shoptypeService.removeById(shop.getId()))
            return res.success("删除成功",shoptype);
        else return res.fail("删除失败");
    }

    //修改店铺类型
    public res modShopType(String shoptype){
        Shoptype shop = shoptypeService.getByShoptype(shoptype);
        if(shop==null) return res.fail("该分类不存在");
        shop.setShoptype(shoptype);
        if(shoptypeService.updateById(shop))
            return res.success("修改成功",shop);
        else return res.fail("修改失败");
    }
}
