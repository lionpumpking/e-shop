package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Shoppingcart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ShoppingcartMapper extends BaseMapper<Shoppingcart> {

    @Select("select * from shoppingcart where shopid = #{shopid}")
    List<Shoppingcart> getByShopid(@Param("shopid")int shopid);


    @Select("SELECT * FROM shoppingcart WHERE userid = #{userid}")
    List<Shoppingcart> getByUserId(@Param("userid") int userid);
}
