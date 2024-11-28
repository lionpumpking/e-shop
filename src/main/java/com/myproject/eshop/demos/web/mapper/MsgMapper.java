package com.myproject.eshop.demos.web.mapper;

import com.myproject.eshop.demos.web.model.Msg;
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
 * @since 2024-11-13
 */
@Mapper
public interface MsgMapper extends BaseMapper<Msg> {

    @Select("SELECT  fromid, toid, msg, time,type FROM msg WHERE (fromid = #{fromid} AND toid =#{toid}) OR (fromid = #{toid} AND toid = #{fromid}) ORDER BY time")
    List<Msg> getMsgList(@Param("fromid") int fromid, @Param("toid") int from);

    @Select("SELECT  fromid,toid, msg, time,type FROM msg WHERE (fromid = #{id} ) OR (toid = #{id}) ORDER BY time")
    List<Msg> getMsgListAll(@Param("id") int id);
}
