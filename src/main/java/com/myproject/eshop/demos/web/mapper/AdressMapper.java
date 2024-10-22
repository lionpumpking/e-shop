package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Adress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hxt
 * @since 2024-10-22
 */
@Mapper
public interface AdressMapper extends BaseMapper<Adress> {

    @Select("select * from Adress where userid = #{userid}")
    List<Adress> getByUserId(@Param("userid") int userId);
}
