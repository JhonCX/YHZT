package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.define.SystemParams;
import com.cc.yhzt.entity.AccountData;
import com.cc.yhzt.entity.Inventory;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.service.IAccountDataService;
import com.cc.yhzt.service.IPlayersService;
import com.cc.yhzt.service.impl.InventoryServiceImpl;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/9
 */
@Controller
public class AccountController extends BaseController {
    @Resource
    private IPlayersService playersService;
    @Resource
    private IAccountDataService accountDataService;
    @Resource
    private InventoryServiceImpl inventoryService;
    @Resource
    private SystemParams systemParams;

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "page/system/user";
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/userInfo")
    @ResponseBody
    public RestBean userInfo() {
        List<Players> players = playersService.list(new QueryWrapper<>(new Players()).orderByAsc("account_name"));
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
        return new RestBean(0, "", data);
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/active")
    @ResponseBody
    public RestBean isActive(@RequestParam(value = "state") Integer state,
                             @RequestParam(value = "id") Integer id) {
        AccountData accountData = new AccountData();
        accountData.setId(id);
        accountData.setActivated(state);
        boolean b = accountDataService.updateById(accountData);
        if (b) {
            if (1 == state) {
                return new RestBean(200, "账户启用成功！");
            } else {
                return new RestBean(1, "账户已冻结！");
            }
        } else {
            return new RestBean(0, "冻结或开启账户失败");
        }
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/editUser")
    @ResponseBody
    public RestBean editUser(@RequestParam(value = "id") Integer id,
                             @RequestParam(value = "accountId") Integer accountId,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "gmLevel") Integer gmLevel) {
        Players player = playersService.getById(id);
        if (id.equals(player.getId())) {
            AccountData accountData = accountDataService.getById(accountId);
            if (null == accountData || null == accountData.getId()) {
                return new RestBean(0, "账户不存在！");
            } else {
                boolean b = true;
                if (!name.equals(player.getName())) {
                    player.setName(name);
                    b= playersService.updateById(player);
                }
                if (!gmLevel.equals(accountData.getAccessLevel())) {
                    accountData.setAccessLevel(gmLevel);
                    b = accountDataService.updateById(accountData);
                }
                if (b) {
                    return new RestBean(200, "修改成功");
                } else {
                    return new RestBean(0, "失败，未知错误！~");
                }
            }
        } else {
            return new RestBean(0, "账户不存在！");
        }
    }
}
