package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

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
public class Players{

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer accountId;

    private String accountName;

    private Long exp;

    private Long recoverexp;

    private Float x;

    private Float y;

    private Float z;

    private Integer heading;

    private Integer worldId;

    private Integer worldOwner;

    private String gender;

    private String race;

    private String playerClass;

    private Date creationDate;

    private Date deletionDate;

    private Date lastOnline;

    private Boolean cubeExpands;

    private Boolean advancedStigmaSlotSize;

    private Boolean warehouseSize;

    private Integer mailboxLetters;

    private Integer titleId;

    private Integer bonusTitleId;

    private Integer dp;

    private Boolean soulSickness;

    private Long reposteEnergy;

    private Long goldenstarEnergy;

    private Long growthEnergy;

    private Integer bgPoints;

    private Boolean online;

    private String note;

    private Integer mentorFlagTime;

    private Integer initialGamestats;

    private BigDecimal lastTransferTime;

    private Integer fatigue;

    @TableField("fatigueRecover")
    private Integer fatigueRecover;

    @TableField("fatigueReset")
    private Integer fatigueReset;

    private Integer stamps;

    private Date lastStamp;

    private Integer rewardedPass;

    @TableField("joinRequestLegionId")
    private Integer joinRequestLegionId;

    @TableField("joinRequestState")
    private String joinRequestState;

    /**
     * Upgrade Arcade FrenzyPoints
     */
    private Integer frenzyPoints;

    private Integer frenzyCount;

    private Integer showInventory;

    private Date bonusBuffTime;

    private String bonusType;

    private Integer wardrobeSize;

    private Boolean isHighdaeva;

    private Integer creativityPoint;

    private Integer creativityStep;

    private Integer wardrobeSlot;

    private Integer lunaConsumeCount;

    private Integer muniKeys;

    private Integer lunaConsume;

    private Integer tocFloor;

    private Integer minionSkillPoints;

    private Date minionFunctionTime;

    @TableField(exist = false)
    private Integer gmLevel;

    @TableField(exist = false)
    private Integer activated;

    @TableField(exist = false)
    private Long walletCount;
}
