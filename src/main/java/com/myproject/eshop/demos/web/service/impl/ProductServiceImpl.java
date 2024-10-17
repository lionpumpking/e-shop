package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Product;
import com.myproject.eshop.demos.web.mapper.ProductMapper;
import com.myproject.eshop.demos.web.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
