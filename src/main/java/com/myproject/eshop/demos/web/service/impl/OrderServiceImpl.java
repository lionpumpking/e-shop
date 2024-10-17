package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.mapper.OrderMapper;
import com.myproject.eshop.demos.web.model.Order;
import com.myproject.eshop.demos.web.service.OrderService;



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
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

}
