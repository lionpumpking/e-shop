package com.myproject.eshop.demos.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Getter
@Setter
@Data
@TableName("shoptype")
public class Shoptype implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("shoptype")
    private String shoptype;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
}
