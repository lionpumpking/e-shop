package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.mapper.ProductMapper;
import com.myproject.eshop.demos.web.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getByOwnshopid(int id) {
        return productMapper.getByOwnershopid(id);
    }

    @Override
    public List<Product> getLessThan(int id) {
        return productMapper.getLessThan(id);
    }
}
