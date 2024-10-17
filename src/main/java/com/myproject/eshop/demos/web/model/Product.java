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
@Data
@Getter
@Setter
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "productid", type = IdType.AUTO)
    private Integer productid;

    /**
     * 商品名
     */
    @TableField("name")
    private String name;

    /**
     * 商品所属店铺
     */
    @TableField("ownershopid")
    private Integer ownershopid;

    /**
     * 商品描述
     */
    @TableField("description")
    private String description;

    /**
     * 商品价格
     */
    @TableField("price")
    private Double price;

    /**
     * 商品数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 运费
     */
    @TableField("freight")
    private Integer freight;

    /**
     * 商品规格
     */
    @TableField("specification")
    private String specification;

    /**
     * 商品类型
     */
    @TableField("typeid")
    private String typeid;
}
