package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Product;
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
public interface ProductMapper extends BaseMapper<Product> {

    @Select("select * from product where ownershopid = #{ownershopid}")
    List<Product> getByOwnershopid(@Param("ownershopid") int ownershopid);

    @Select("select * from product where quantity < 10 and ownershopid = #{id}")
    List<Product> getLessThan(@Param("id") int id);
}
