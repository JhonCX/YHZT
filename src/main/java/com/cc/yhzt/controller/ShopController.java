package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.yhzt.common.utils.StringUtil;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.constant.Status;
import com.cc.yhzt.constant.TreeResult;
import com.cc.yhzt.entity.ShopList;
import com.cc.yhzt.entity.TreeEntity;
import com.cc.yhzt.entity.WalletEntity;
import com.cc.yhzt.service.IShopListService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :cc
 * @date : 2019/6/10
 */
@Controller
public class ShopController extends BaseController{
    @Resource
    private IShopListService shopListService;

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String toShop(HttpServletRequest request,
                         HttpServletResponse response) {
        return "page/player/shop";
    }

    @ResponseBody
    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping("/myWallet")
    public RestBean myWallet() {
        WalletEntity wallet = getWallet();
        return new RestBean(0, wallet.getAllMoney()+"");
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public TreeResult menu(HttpServletRequest request,
                           HttpServletResponse response) {
        List<ShopList> list = shopListService.list(new QueryWrapper<ShopList>(new ShopList().setFlag(1)).in("type", 0, 1, 2).orderByAsc("item_name"));
        List<TreeEntity> data = new ArrayList<>();
        ShopList top = shopListService.getById(1);
        getTreeTntity(data, top, null);
        for (ShopList shop : list) {
            getTreeTntity(data, shop, null);
        }
        TreeResult treeResult = new TreeResult();
        Status status = new Status();
        treeResult.setStatus(status);
        treeResult.setData(data);
        return treeResult;
    }




    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping("/shopList")
    public RestBean shopList(
            @RequestParam(value = "nodeId",required = false) Integer nodeId,
            @RequestParam(value = "keyword",required = false) String keyword) {
        IPage page = new Page();
        if (null != nodeId) {
            if (StringUtil.isNotBlank(keyword)) {
                page = shopListService.page(getPage(), new QueryWrapper<ShopList>(new ShopList()).like("item_id", keyword).or().like("item_name", keyword).eq("flag", 1).eq("type", 3));
            } else {
                page = shopListService.page(getPage(), new QueryWrapper<ShopList>(
                        new ShopList().setType(3).setFlag(1).setParentId(nodeId)));
            }
        } else {
            if (StringUtil.isNotBlank(keyword)) {
                page = shopListService.page(getPage(), new QueryWrapper<ShopList>(new ShopList()).like("item_id", keyword).or().like("item_name", keyword).eq("flag", 1).eq("type", 3));
            }
        }
        return new RestBean(0, "",page.getTotal(),page.getRecords());
    }
    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/repeatItem",method = RequestMethod.GET)
    public String toRepeatItem() {
        return "page/system/repeatItem";
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/repeatItem",method = RequestMethod.POST)
    public RestBean repeatItem() {
        List<ShopList> shopList = shopListService.list(new QueryWrapper<>(new ShopList().setType(3)));
        Map<Integer, List<ShopList>> collect = shopList.stream().collect(Collectors.groupingBy(ShopList::getItemId));
        List<ShopList> newList = new ArrayList<>();
        for (List<ShopList> value : collect.values()) {
            if (value.size() > 1) {
                newList.addAll(value);
            }
        }
        List<ShopList> a = new ArrayList<>();
        for (ShopList list : newList) {
            ShopList two = shopListService.getById(list.getParentId());
            list.setTwo(two.getItemName());
            ShopList one = shopListService.getById(two.getParentId());
            list.setOne(one.getItemName());
            a.add(list);
        }
        return new RestBean(0, "",a);
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @ResponseBody
    @RequestMapping("/delItem")
    public RestBean delItem(
            @RequestParam(value = "id")Integer id
    ) {
        boolean b = shopListService.removeById(id);
        if (b) {
            return new RestBean(200, "删除成功!");
        } else {
            return new RestBean(0, "删除失败!");
        }
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @ResponseBody
    @RequestMapping("/itemSearch")
    public RestBean itemSearch(
            @RequestParam(value = "keyWord",required = false)String keyWord
    ) {
        IPage page = shopListService.page(getPage(), new QueryWrapper<>(new ShopList().setType(3)).like("item_name", keyWord).or().like("item_id", keyWord));
        List<ShopList> shopList = page.getRecords();
        for (ShopList list : shopList) {
            if (3 != list.getType()) {
                shopList.remove(list);
            }
        }
        List<ShopList> a = new ArrayList<>();
        for (ShopList list : shopList) {
            ShopList two = shopListService.getById(list.getParentId());
            list.setTwo(two.getItemName());
            ShopList one = shopListService.getById(two.getParentId());
            list.setOne(one.getItemName());
            a.add(list);
        }
        return new RestBean(0, "",page.getTotal(),a);
    }



    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/itemActive")
    @ResponseBody
    public RestBean isActive(@RequestParam(value = "state") Integer state,
                             @RequestParam(value = "id") Integer id) {
        ShopList shopList = new ShopList();
        shopList.setId(id);
        shopList.setFlag(state);
        boolean b = shopListService.updateById(shopList);
        if (b) {
            if (1 == state) {
                return new RestBean(200, "物品上架成功！");
            } else {
                return new RestBean(1, "物品下架成功！");
            }
        } else {
            return new RestBean(0, "冻结或开启账户失败");
        }
    }

}
