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
@TableName("shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 店铺名
     */
    @TableField("shopname")
    private String shopname;

    /**
     * 店铺类型
     */
    @TableField("shoptype")
    private String shoptype;

    /**
     * 店铺类型id
     */
    @TableField("shoptypeid")
    private int shoptypeid;

    /**
     * 拥有者
     */
    @TableField("ownerusername")
    private String ownerusername;

    /**
     * 店铺描述
     */
    @TableField("shopdescription")
    private String shopdescription;

    /**
     * 店铺头像
     */
    @TableField("shopimg")
    private String shopimg;

    /**
     * 是否审核 0 待审核 1 审核通过 -1 审核不通过
     */
    @TableField("isvalid")
    private int isvalid;

    /**
     * 店铺经度
     */
    @TableField("lat")
    private Double lat;

    /**
     * 店铺纬度
     */
    @TableField("lon")
    private Double lon;
}
