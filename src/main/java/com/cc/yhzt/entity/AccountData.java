package com.cc.yhzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <p>
    * 
    * </p>
*
* @author CC
* @since 2019-05-18
*/
    @Data
    @Accessors(chain = true)
    public class AccountData {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private Integer activated;

    private Integer accessLevel;

    private Integer membership;

    private Integer oldMembership;

    private Integer lastServer;

    private String lastIp;

    private String lastMac;

    private String ipForce;

    private Date expire;

    private Long toll;

    private String email;

    private String question;

    private String answer;

    private Float balance;

    private Long luna;

    private Boolean returnAccount;

    private Date returnEnd;


}
