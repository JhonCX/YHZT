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
import com.cc.yhzt.service.IShopListService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/12
 */
@Controller
public class ShopManagerController extends BaseController {
    @Resource
    private IShopListService shopListService;

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/shopManager", method = RequestMethod.GET)
    public String toShopManager() {
        return "page/system/shopManager";
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/shopListManager")
    @ResponseBody
    public RestBean shopListManager(
            @RequestParam(value = "nodeId", defaultValue = "1") Integer nodeId,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<ShopList> shopLists = new ArrayList<>();
        List<ShopList> result = new ArrayList<>();
        IPage page = new Page();
        if (StringUtil.isNotBlank(keyword)) {
            page = shopListService.page(getPage(), new QueryWrapper<>(new ShopList().setParentId(nodeId)).like("item_id", keyword).or().like("item_name", keyword).orderByDesc("flag"));
        } else {
            page = shopListService.page(getPage(), new QueryWrapper<>(new ShopList().setParentId(nodeId)).orderByDesc("flag"));
        }
        shopLists.addAll(page.getRecords());
        for (ShopList shopList : shopLists) {
            if (3 == shopList.getType()) {
                ShopList sec = shopListService.getById(shopList.getParentId());
                shopList.setTwo(sec.getItemName());
                ShopList fir = shopListService.getById(sec.getParentId());
                shopList.setOne(fir.getItemName());
            }
            result.add(shopList);
        }
        return new RestBean(0, "", page.getTotal(), result);
    }


    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/menuManager", method = RequestMethod.GET)
    @ResponseBody
    public TreeResult menuManager(@RequestParam(value = "id", required = false) Integer id) {
        List<ShopList> list = shopListService.list(new QueryWrapper<ShopList>(new ShopList()).in("type", 0, 1, 2).orderByDesc("flag").orderByAsc("item_name").orderByAsc("add_time"));
        List<TreeEntity> data = new ArrayList<>();
        for (ShopList shop : list) {
            getTreeTntity(data, shop, id);
        }
        TreeResult treeResult = new TreeResult();
        Status status = new Status();
        treeResult.setStatus(status);
        treeResult.setData(data);
        return treeResult;
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
    @ResponseBody
    public RestBean insertMenu(
            @RequestParam(value = "parentId") Integer parentId,
            @RequestParam(value = "level") Integer level,
            @RequestParam(value = "context") String context) {
        ShopList shop = new ShopList();
        shop.setItemId(0);
        shop.setItemName(context);
        shop.setItemCount(0);
        shop.setItemPrice(0);
        shop.setParentId(parentId);
        if (2 == level) {
            shop.setDescription("一级菜单");
            shop.setType(1);
        } else if (3 == level) {
            shop.setType(2);
            shop.setDescription("二级菜单");
        }
        shop.setAddTime(new Date());
        shop.setFlag(1);
        boolean save = shopListService.save(shop);
        if (save) {
            return new RestBean(200, "操作成功！");
        } else {
            return new RestBean(-1, "操作失败！");
        }
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/oneMenu", method = RequestMethod.POST)
    @ResponseBody
    public RestBean oneMenu(@RequestParam(value = "id", required = false) Integer id) {
        List<ShopList> oneList = shopListService.list(new QueryWrapper<ShopList>(new ShopList()).eq("type", 1).orderByDesc("flag").orderByAsc("id"));
        String option = "";
        ShopList shop = shopListService.getById(id);
        ShopList two = shopListService.getById(shop.getParentId());
        ShopList one = shopListService.getById(two.getParentId());
        for (ShopList shopList : oneList) {
            if (one.getId().equals(shopList.getId())) {
                option += "<option value='" + shopList.getId() + "'  selected='selected'>" + shopList.getItemName() + "</option>";
            } else {
                option += "<option value='" + shopList.getId() + "'>" + shopList.getItemName() + "</option>";
            }

        }
        return new RestBean(200, "", option);
    }




    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/twoMenu", method = RequestMethod.POST)
    @ResponseBody
    public RestBean twoMenu(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "parentId", required = false) Integer parentId) {
        StringBuilder option = new StringBuilder();
        if (null != id && null != parentId) {
            ShopList two = shopListService.getOne(new QueryWrapper<>(new ShopList().setId(parentId)));
            List<ShopList> twoMenu = shopListService.list(new QueryWrapper<>(new ShopList().setParentId(two.getParentId())).orderByAsc("flag"));
            for (ShopList shop : twoMenu) {
                if (two.getId().equals(shop.getId())) {
                    option.append("<option value='").append(shop.getId()).append("'  selected='selected'>").append(shop.getItemName()).append("</option>");
                } else {
                    option.append("<option value='").append(shop.getId()).append("'>").append(shop.getItemName()).append("</option>");
                }
            }
        }
        if (null != id && null == parentId) {
            ShopList one = shopListService.getOne(new QueryWrapper<>(new ShopList().setId(id)));
            List<ShopList> twoMenu = shopListService.list(new QueryWrapper<>(new ShopList().setParentId(one.getId())).orderByAsc("flag"));
            for (ShopList two : twoMenu) {
                    option.append("<option value='").append(two.getId()).append("'>").append(two.getItemName()).append("</option>");
            }
        }
        return new RestBean(200, "", option.toString());
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/itemEdit", method = RequestMethod.POST)
    @ResponseBody
    public RestBean itemEdit(ShopList shopList,
                             @RequestParam(value = "twoMenu", required = false) Integer twoMenu ) {
        ShopList shop = shopListService.getById(shopList.getId());
        if (null == shop) {
            return new RestBean(-1, "物品不存在");
        } else {
            shopList.setParentId(twoMenu);
            boolean b = shopListService.updateById(shopList);
            if (b) {
                return new RestBean(200, shop.getItemName()+"  修改成功");
            } else {
                return new RestBean(-1, "物品修改失败");
            }
        }
    }

    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemAdd", method = RequestMethod.GET)
    public String itemAdd() {
        return "page/system/itemAdd";
    }

    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestBean itemSave(ShopList shopList,
                             @RequestParam(value = "two")Integer two
                             ) {
        shopList.setParentId(two);
        shopList.setAddTime(new Date());
        shopList.setType(3);
        boolean b = shopListService.save(shopList);
        if (b) {
            return new RestBean(200, "保存成功！");
        } else {
            return new RestBean(0, "保存失败！");
        }
    }

    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemBatchAdd", method = RequestMethod.GET)
    public String itemBatchAdd() {
        return "page/system/itemBatchAdd";
    }

    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemBatchAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestBean itemBatchAdd(ShopList shopList,
                                 @RequestParam(value = "batchAdd") String batchAdd,
                                 @RequestParam(value = "two")Integer two) {
        if (StringUtils.isNotBlank(batchAdd)) {
            batchAdd = batchAdd.trim();
            String[] split;
            if (batchAdd.contains("\r\n")) {
                split = batchAdd.split("\r\n");
            } else if (batchAdd.contains("\r")) {
                split = batchAdd.split("\r");
            } else if (batchAdd.contains("\n")) {
                split = batchAdd.split("\n");
            } else {
                split=batchAdd.split("\n");
            }
            List<ShopList> list = new ArrayList<>();
            for (String s : split) {
                s = s.trim();
                String[] ss = s.split("\\s+");
                String id = ss[0].replace("id:", "").replace("id：", "");
                String name = ss[1];
                ShopList shop = new ShopList();
                shop.setFlag(1);
                shop.setParentId(two);
                shop.setItemPrice(shopList.getItemPrice());
                shop.setItemCount(shopList.getItemCount());
                shop.setAddTime(new Date());
                shop.setItemName(name);
                shop.setItemId(Integer.valueOf(id));
                shop.setType(3);
                list.add(shop);
//                System.out.println(id + "####" + name);
            }
//            System.out.println(list);
            boolean b = shopListService.saveBatch(list);
            if (b) {
                return new RestBean(200, "批量添加成功");
            }
        }
        return new RestBean(0, "批量添加物品失败，请重试！~");
    }

    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemBatchPrice", method = RequestMethod.GET)
    public String itemBatchPrice() {
        return "page/system/itemBatchPrice";
    }


    @RequiresRoles((Constant.ADMIN_ACCOUNT))
    @RequestMapping(value = "/itemBatchPrice", method = RequestMethod.POST)
    @ResponseBody
    public RestBean itemBatchPrice(@RequestParam(value = "two")Integer two,
                                   @RequestParam(value = "itemPrice")Integer itemPrice,
                                   @RequestParam(value = "itemCount")Integer itemCount,
                                   @RequestParam(value = "race",required = false)Integer race,
                                   @RequestParam(value = "isCount")Integer isCount,
                                   @RequestParam(value = "isPrice")Integer isPrice) {
        if (1!=isCount && 1!=isPrice && null==race) {
            return new RestBean(-1, "请选择修改项~");
        }

        List<ShopList> list = shopListService.list(new QueryWrapper<>(new ShopList().setParentId(two)));
        List<ShopList> newList = new ArrayList<>();
        for (ShopList shop : list) {
            if (1 == isCount) {
                shop.setItemCount(itemCount);
            }
            if (1 == isPrice) {
                shop.setItemPrice(itemPrice);
            }
            if (null != race) {
                shop.setRace(race);
            }
            newList.add(shop);
        }
        boolean b = shopListService.updateBatchById(newList);
        if (b) {
            return new RestBean(200, "批量价格修改成功！");
        } else {
            return new RestBean(-1, "批量价格修改失败！");
        }
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    public RestBean one() {
        List<ShopList> list = shopListService.list(new QueryWrapper<>(new ShopList().setType(1).setFlag(1)));
        StringBuilder option = new StringBuilder("<option value =''>请选择一级菜单</option>");
        for (ShopList shop : list) {
            option.append("<option value='").append(shop.getId()).append("'>").append(shop.getItemName()).append("</option>");
        }
        return new RestBean(200, "",option);
    }

    @RequestMapping(value = "/two", method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    public RestBean two(@RequestParam(value = "id")Integer id) {
        List<ShopList> list = shopListService.list(new QueryWrapper<>(new ShopList().setParentId(id).setFlag(1)));
        StringBuilder option = new StringBuilder("<option value =''>请选择一级菜单</option>");
        for (ShopList shop : list) {
            option.append("<option value='").append(shop.getId()).append("'>").append(shop.getItemName()).append("</option>");
        }
        return new RestBean(200, "",option);
    }

}
