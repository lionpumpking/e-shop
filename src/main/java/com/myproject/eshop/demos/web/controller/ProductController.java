package com.myproject.eshop.demos.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    //显示所有商品
    @GetMapping("/getPage")
    public res getPage(int pageNum, int pageSize) {
        Page<Product> page=new Page(pageNum,pageSize);
        IPage result=productService.page(page);
        return res.success("查询成功",result);
    }

    //根据商品名，最低最高价格，是否免运费，商品类型来进行排序显示
    @PostMapping("/search")
    public res searchProduct(@RequestParam(required = false) String name, @RequestParam(required = false,defaultValue = "0") int lowPrice,
                             @RequestParam(required = false,defaultValue = "99999999") int highPrice,
                             @RequestParam(required = false,defaultValue = "-1") int freight,
                             @RequestParam(required = false) String type, @RequestParam(required = false,defaultValue = "asc") String orderByPrice,
                             int pageNum,int pageSize){
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        int index = 0;
        if(name!=null){
            index++;
            queryWrapper.like("name",name);
        }
        if(lowPrice!=0){
            if(index!=0){
                queryWrapper.gt("price",lowPrice);
            }
        }
        if(highPrice!=0){
            if(index!=0){
                queryWrapper.lt("price",highPrice);
            }
        }
            if(freight==0){
                queryWrapper.eq("freight",0);
            }
        if(type!=null){
            queryWrapper.eq("type",type);
        }
        if(orderByPrice!=null){
            if(orderByPrice.equals("asc")){
                queryWrapper.orderByAsc("price");
            }
            else if(orderByPrice.equals("desc")){
                queryWrapper.orderByDesc("price");
            }
        }
        Page<Product> page=new Page<>(pageNum,pageSize);
        IPage<Product> res1=productService.getBaseMapper().selectPage(page,queryWrapper);
        return res.success("查询成功",res1);
    }


}
