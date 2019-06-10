package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 商城物品表
 * </p>
 *
 * @author CC
 * @since 2019-05-09
 */
@Data
@Accessors(chain = true)
public class ShopList {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 物品id
     */
    private Integer itemId;

    /**
     * 物品名称
     */
    private String itemName;

    /**
     * 商品数量
     */
    private Integer itemCount;

    /**
     * 商品价格
     */
    private Integer itemPrice;

    /**
     * 父类id
     */
    private Integer parentId;

    /**
     * 种族
     */
    private Integer race;

    /**
     * 物品描述
     */
    private String description;

    /**
     * 添加时间
     */
    private Date addTime;


    /**
     * 商品类型
     */
    private Integer type;

    /**
     * 是否允许出售
     */
    private Integer flag;

//    /**
//     * 菜单是否被选中
//     */
//    @TableField(exist =false)
//    private Integer selected;
    @TableField(exist =false)
    private String one;

    @TableField(exist =false)
    private String two;

}
