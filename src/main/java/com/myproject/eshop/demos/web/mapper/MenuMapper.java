package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Menu;
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
 * @since 2024-10-18
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select * from menu where menuright like #{roleid}")
    List<Menu> getByRoleid(@Param("roleid") String roleid);
}
