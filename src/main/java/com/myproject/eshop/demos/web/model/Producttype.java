package com.myproject.eshop.demos.web.model;

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
@TableName("producttype")
public class Producttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int id;

    /**
     * 商品类型
     */
    @TableField("productType")
    private String productType;
}
