package com.myproject.eshop.demos.web.controller;

import com.myproject.eshop.demos.web.Result.res;
import com.myproject.eshop.demos.web.model.Adress;
import com.myproject.eshop.demos.web.service.IAdressService;
import com.myproject.eshop.demos.web.service.impl.AdressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxt
 * @since 2024-10-22
 */
@CrossOrigin(origins =  "${my.cross.url}",allowCredentials = "true")
@RestController
@RequestMapping("/adress")
public class AdressController {

    @Autowired
    private IAdressService adressService;

    // 用户查询地址
    @GetMapping("/Getadress")
    public res getAdress(int userid){
        List<Adress> asress = adressService.getByUserId(userid);
        if(asress.size()>0){
            return res.success("查询成功",asress);
        }
        return res.fail();
    }

    //用户编辑地址
    @PostMapping("/editAdress")
    public res editAdress(int id ,String address){
        Adress adress = adressService.getById(id);
        if(adress==null) return res.fail("该地址不存在");
        adress.setAdress(address);
        System.out.println(adress);
        if(adressService.updateById(adress)){
            return res.success("修改成功",adress);
        }
        return res.fail("修改失败");
    }

    //用户删除地址
    @PostMapping("/deleteAdress")
    public res deleteAdress(int id){
        if(adressService.removeById(id))
            return res.success("删除成功",true);
        return res.fail("修改失败");
    }

    //用户添加地址
    @PostMapping("/addAdress")
    public res addAdress(int userid,String address){
        Adress adress = new Adress();
        adress.setAdress(address);
        adress.setUserid(userid);
        if(adressService.save(adress)){
            return res.success("添加成功",adress);
        }
        return res.fail("添加失败");
    }

}
