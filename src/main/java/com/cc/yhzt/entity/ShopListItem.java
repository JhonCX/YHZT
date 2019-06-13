package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
* <p>
    * 
    * </p>
*
* @author CC
* @since 2019-06-13
*/
    @Data
    @Accessors(chain = true)
    public class ShopListItem{

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer itemId;

    private String itemName;

    private String strName;

    private Date addTime;

            /**
            * 0 npc 1 技能 2任务 3套装 4物品
            */
    private Integer type;


}
