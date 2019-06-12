package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.yhzt.common.utils.StringUtil;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.entity.ShopList;
import com.cc.yhzt.service.IShopListService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/11
 */
@Controller
public class BuyController extends BaseController {
    @Resource
    private IShopListService shopListService;

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping("/buy")
    public RestBean buy(@RequestParam(value = "id") Integer id) {
        ShopList shop = shopListService.getOne(new QueryWrapper<>(new ShopList().setId(id).setFlag(1).setType(3)));
        List<ShopList> shopList = new ArrayList<>();
        shopList.add(shop);
        if (null == shop || null == shop.getId()) {
            return new RestBean(-1, "物品不存在！");
        } else {
            return shopResult(shop.getItemPrice().longValue(), shopList, false,null);
        }
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping("/batchBuy")
    public RestBean batchBuy(@RequestParam(value = "ids") String ids) {
        String[] s = ids.split("_");
        List<Integer> idList = new ArrayList<>();
        for (String id : s) {
            if (StringUtil.isNotBlank(id)) {
                idList.add(Integer.parseInt(id));
            }
        }
        List<ShopList> shopList = (List<ShopList>) shopListService.listByIds(idList);
        Long shopPrice = 0L;
        for (ShopList shop : shopList) {
            if (1 == shop.getFlag() && 3 == shop.getType()) {
                shopPrice += shop.getItemPrice();
            } else {
                shopList.remove(shop);
            }
        }
        if (0 >=shopList.size()) {
            return new RestBean(-1, "物品不存在！");
        } else {
            return shopResult(shopPrice, shopList, false,null);
        }
    }
}
