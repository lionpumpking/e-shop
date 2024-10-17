package com.myproject.eshop.demos.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.eshop.demos.web.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select * from `order` where ownerid = #{ownerid}")
    List<Order> UserGetOrder(@Param("ownerid")int oenerid);

    @Select("select * from `order` where shopid = #{shopid}")
    List<Order> ShopGetOrder(@Param("shopid")int shopid);
}
