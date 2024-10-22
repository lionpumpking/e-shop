package com.myproject.eshop.demos.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.model.Producttype;
import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.model.User;
import com.myproject.eshop.demos.web.service.ProductService;
import com.myproject.eshop.demos.web.service.ProducttypeService;
import com.myproject.eshop.demos.web.service.ShopService;
import com.myproject.eshop.demos.web.service.UserService;
import com.myproject.eshop.demos.web.utils.BusinessExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProducttypeService producttypeService;

    //商家查询自己店铺的商品
    @GetMapping("/getPageShop")
    public res getPageShop(String id) {
        Shop shop = shopService.getByOwnerUsername(id);
        List<Product> products = productService.getByOwnshopid(shop.getId());
//        IPage result=productService.page(page);
        return res.success("查询成功",products);
    }

    //商家查询需要补货的商品，即商品库存小于10
    @GetMapping("/getProductLess")
    public res getProductLess(int id){
        Shop shop = shopService.getByOwnerUsername(String.valueOf(id));
        List<Product> products = productService.getLessThan(shop.getId());
        if(products.size() == 0){
            return res.fail("暂无商品");
        }
        else return res.success("查询成功",products);
    }

    //显示所有商品
    @GetMapping("/getPage")
    public res getPage(int pageNum, int pageSize) {
        Page<Product> page=new Page(pageNum,pageSize);
        IPage result=productService.page(page);
        return res.success("查询成功",result);
    }

    //新增商品
    @PostMapping("/addProduct")
    public res addProduct(int id,String name,String description,double price,int quantity,
                          int freight,String specification,int typeid,String img){
        Shop shop = shopService.getByOwnerUsername(String.valueOf(id));
        Producttype type = producttypeService.getById(typeid);
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setFreight(freight);
        product.setSpecification(specification);
        product.setTypeid(typeid);
        product.setImg(img);
        product.setProducttype(type.getProductType());
        product.setOwnershopid(shop.getId());
        if(productService.save(product))
            return res.success("添加成功",product);
        throw new BusinessExp("添加失败");

    }

    //修改商品
    @PostMapping("/modProduct")
    public res modProduct(int productid,String name,String description,double price,int quantity,
                          int freight,String specification,int typeid,String img){
        Producttype type = producttypeService.getById(typeid);
        Product product=productService.getById(productid);
        System.out.println(product);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setFreight(freight);
        product.setSpecification(specification);
        product.setTypeid(typeid);
        product.setImg(img);
        product.setProducttype(type.getProductType());
        if(productService.updateById(product)){
            return res.success("修改成功",product);
        }else return res.fail("修改失败");

    }

    //删除商品
    @PostMapping("/removeProduct")
    public res removeProduct(int id){
            if(productService.removeById(id))
                return res.success("删除成功",null);
            throw new BusinessExp("删除失败");
    }


    //根据商品名，商品类型，最低最高价格，是否免运费，商品类型来进行排序显示
    @PostMapping("/search")
    public res searchProduct(@RequestParam(required = false) String name, @RequestParam(required = false,defaultValue = "0") int lowPrice,
                             @RequestParam(required = false,defaultValue = "99999999") int highPrice,
                             @RequestParam(required = false,defaultValue = "-1") int freight,
                             @RequestParam(required = false,defaultValue = "asc") String orderByPrice,
                             int pageNum,int pageSize,@RequestParam(required = false,defaultValue = "-1")int typeid){
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        if(name!=null){
            queryWrapper.like("name",name);
        }
        if(lowPrice!=0){
                queryWrapper.gt("price",lowPrice);
        }
        if(typeid != -1){
            queryWrapper.eq("typeid",typeid);
        }
        if(highPrice!=0){
                queryWrapper.lt("price",highPrice);
        }
            if(freight==0){
                queryWrapper.eq("freight",0);
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