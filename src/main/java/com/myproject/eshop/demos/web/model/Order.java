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
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    /**
     * 快递单号
     */
    @TableField("pressnumber")
    private String pressnumber;

    /**
     * 快递公司
     */
    @TableField("presscompany")
    private String presscompany;

    /**
     * 目的地
     */
    @TableField("destination")
    private String destination;

    /**
     * 订单所属店铺
     */
    @TableField("shopid")
    private Integer shopid;

    /**
     * 下单人id
     */
    @TableField("ownerid")
    private Integer ownerid;
}
