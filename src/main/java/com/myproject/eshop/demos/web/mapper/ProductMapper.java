package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}