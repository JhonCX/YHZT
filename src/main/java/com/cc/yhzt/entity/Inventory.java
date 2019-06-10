package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author CC
* @since 2019-05-16
*/
    @Data
    @Accessors(chain = true)
    public class Inventory {

    private static final long serialVersionUID = 1L;
    @TableId(value = "item_unique_id", type = IdType.AUTO)
    private Integer itemUniqueId;

    private Integer itemId;

    private Long itemCount;

    private Integer itemColor;

    private Integer colorExpires;

    private String itemCreator;

    private Integer expireTime;

    private Integer activationCount;

    private Integer itemOwner;

    private Boolean isEquiped;

    private Boolean isSoulBound;

    private Long slot;

    private Boolean itemLocation;

    private Boolean enchant;

    private Integer itemSkin;

    private Integer fusionedItem;

    private Integer optionalSocket;

    private Integer optionalFusionSocket;

    private Integer charge;

    private Integer rndBonus;

    private Integer rndCount;

    private Integer packCount;

    private Integer authorize;

    private Boolean isPacked;

    private Boolean isAmplified;

    private Integer buffSkill;

    private Integer reductionLevel;

    private Boolean lunaReskin;

        @TableField("isEnhance")
    private Boolean isEnhance;

        @TableField("enhanceSkillId")
    private Integer enhanceSkillId;

        @TableField("enhanceSkillEnchant")
    private Integer enhanceSkillEnchant;


}
