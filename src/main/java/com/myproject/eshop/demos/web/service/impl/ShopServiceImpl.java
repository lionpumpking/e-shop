package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Shop;
import com.myproject.eshop.demos.web.mapper.ShopMapper;
import com.myproject.eshop.demos.web.service.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Service("b")
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Shop getByOwnerUsername(String username) {
        return shopMapper.getByUsername(username);
    }
}
