package com.myproject.eshop.demos.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.eshop.demos.web.model.Order;
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
public interface OrderService extends IService<Order> {

    List<Order> getByOwner(int id);

    List<Order> ShopGetOrder(int shopid);
}