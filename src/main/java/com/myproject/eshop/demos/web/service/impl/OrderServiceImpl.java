package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.mapper.OrderMapper;
import com.myproject.eshop.demos.web.model.Order;
import com.myproject.eshop.demos.web.service.OrderService;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getByOwner(int id) {
        List<Order> orders = orderMapper.UserGetOrder(id);
        return orders;
    }

    @Override
    public List<Order> ShopGetOrder(int shopid) {
        List<Order> orders = orderMapper.ShopGetOrder(shopid);
        return orders;
    }

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public int getByAll(int productid, String destination, int userid, int num) {
        List<Order> order= orderMapper.getByAll(productid, destination, userid,num);
        for(Order orderi : order){
            return orderi.getId();
        }
        return 0;
    }

}
