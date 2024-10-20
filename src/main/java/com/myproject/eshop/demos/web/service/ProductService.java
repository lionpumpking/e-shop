package com.myproject.eshop.demos.web.service;

import com.myproject.eshop.demos.web.model.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Service
public interface ProductService extends IService<Product> {

    List<Product> getByOwnshopid(int id);

    List<Product> getLessThan(int id);
}


