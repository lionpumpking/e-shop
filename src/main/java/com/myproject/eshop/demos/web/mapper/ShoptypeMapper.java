package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Shoptype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Mapper
public interface ShoptypeMapper extends BaseMapper<Shoptype> {

    @Select("select * from shoptype where shoptype = #{shoptype}")
    Shoptype getShoptype(@Param("shoptype") String shoptype);
}