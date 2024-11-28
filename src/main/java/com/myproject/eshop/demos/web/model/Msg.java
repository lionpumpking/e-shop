package com.myproject.eshop.demos.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author hxt
 * @since 2024-11-13
 */
@Getter
@Setter
@TableName("msg")
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("fromid")
    private Integer fromid;

    @TableField("toid")
    private Integer toid;

    @TableField("msg")
    private String msg;

    @TableField("time")
    private Long time;

    @TableField("type")
    private int type;
}
