package com.cc.yhzt.entity;

import lombok.Data;

import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/11
 */
@Data
public class WalletEntity {
    private Long allMoney;
    private Players user;
    private List<Inventory> moneys;
}
