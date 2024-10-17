package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.model.Shoppingcart;
import com.myproject.eshop.demos.web.mapper.ShoppingcartMapper;
import com.myproject.eshop.demos.web.service.ShoppingcartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Service
public class ShoppingcartServiceImpl extends ServiceImpl<ShoppingcartMapper, Shoppingcart> implements ShoppingcartService {

    @Autowired
    private ShoppingcartMapper shoppingcartMapper;

    @Override
    public List<Shoppingcart> getByShopid(int shopid) {
        List<Shoppingcart> shoppings = shoppingcartMapper.getByShopid(shopid);
        return shoppings;
    }
}
