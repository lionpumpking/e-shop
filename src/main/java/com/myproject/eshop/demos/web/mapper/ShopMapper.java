package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
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
public interface ShopMapper extends BaseMapper<Shop> {

    @Select("select * from shop where shoptypeid = #{shoptypeid} ")
    List<Shop> searchByType(@Param("shoptypeid") int shoptypeid);

    @Delete("delete shop from shop where id = #{id}")
    boolean removeById(Integer id);
}
