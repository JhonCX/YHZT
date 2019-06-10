package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.define.SystemParams;
import com.cc.yhzt.entity.AccountData;
import com.cc.yhzt.entity.Inventory;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.service.IAccountDataService;
import com.cc.yhzt.service.IPlayersService;
import com.cc.yhzt.service.impl.InventoryServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/9
 */
@RestController
public class AccountController extends BaseController{
    @Resource
    private IPlayersService playersService;
    @Resource
    private IAccountDataService accountDataService;
    @Resource
    private InventoryServiceImpl inventoryService;
    @Resource
    private SystemParams systemParams;

    @RequestMapping("userInfo")
    public RestBean userInfo() {
        IPage page = playersService.page(getPage(), new QueryWrapper<>(new Players()).orderByAsc("account_name"));
        List<Players> players = page.getRecords();
        List<Players> data = new ArrayList<>();
        for (Players player : players) {
            AccountData account = accountDataService.getById(player.getAccountId());
            if (player.getAccountId().equals(account.getId())) {
                    player.setGmLevel(account.getAccessLevel());
                    player.setActivated(account.getActivated());
                    List<Inventory> moneys = inventoryService.list(new QueryWrapper<>(new Inventory().setItemOwner(player.getId()).setItemId(Integer.valueOf(systemParams.getId()))));
                    long count = 0;
                    for (Inventory money : moneys) {
                        count += money.getItemCount();
                    }
                    player.setWalletCount(count);
                }
            data.add(player);
        }
        return new RestBean(0, "", page.getTotal(),data);
    }
}
