package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    public class Surveys {

    private static final long serialVersionUID = 1L;

            @TableId(value = "unique_id", type = IdType.AUTO)
    private Integer uniqueId;

    private Integer ownerId;

    private Integer itemId;

    private Integer itemCount;

    private String htmlText;

    private String htmlRadio;

    private Boolean used;

    private String usedTime;


}
