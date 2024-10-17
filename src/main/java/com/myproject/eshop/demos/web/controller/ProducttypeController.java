package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.mapper.ProducttypeMapper;
import com.myproject.eshop.demos.web.model.Producttype;
import com.myproject.eshop.demos.web.service.ProducttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/producttype")
public class ProducttypeController {

    @Autowired
    private ProducttypeMapper productTypeMapper;

    @Autowired
    private ProducttypeService productTypeService;

    //获取所有商品类型
    @PostMapping("getAllProductTypes")
    public res GetListProducts() {
        return res.success("查询成功", productTypeService.list());
    }

    //删除分类
    @PostMapping("/delete")
    public res delete(int id){
        if(productTypeService.removeById(id))
            return res.success("删除成功",true);
        else return res.fail("删除失败");
    }

    //增加分类
    @PostMapping("/addProductType")
    public res addType(String productType) {
        //controller中不应该直接使用Mapper，但是已经写了，懒得改了
        if(productTypeMapper.getByProducttype(productType)!=null){
            return res.fail("已存在该分类");
        }
        Producttype result = new Producttype();
        System.out.println(result.getId());
        result.setProductType(productType);
        productTypeService.save(result);
        return res.success("添加成功",result);
    }

    //修改分类名称
    @PostMapping("/updateProductType")
    public res updateType(String productType ,int id) {
        System.out.println(id);
        if(productTypeService.getById(id)==null){
            return res.fail("该分类不存在");
        }
        Producttype result = new Producttype();
        result.setId(id);
        result.setProductType(productType);
        productTypeService.updateById(result);
        return res.success("修改成功",result);
    }
}
