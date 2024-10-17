package com.myproject.eshop.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.eshop.demos.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
    * Description:
    * Author:
    * Data:
    */
@Mapper
public interface UserMapper extends BaseMapper<User>{

    @Select("select * from user where username = #{username} and password = #{password} ")
    User LoginCheck(@Param("username") String username, @Param("password") String password);

}
