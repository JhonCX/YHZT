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
        IPage page = new Page();
        if (StringUtil.isNotBlank(keyword)) {
            page = shopListService.page(getPage(), new QueryWrapper<>(new ShopList().setParentId(nodeId)).like("item_id", keyword).or().like("item_name", keyword).orderByDesc("flag"));
        } else {
            page = shopListService.page(getPage(), new QueryWrapper<>(new ShopList().setParentId(nodeId)).orderByDesc("flag"));
        }
        return new RestBean(0, "", page.getTotal(), page.getRecords());
    }


    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping(value = "/menuManager", method = RequestMethod.GET)
    @ResponseBody
    public TreeResult menuManager(HttpServletRequest request,
                                  HttpServletResponse response) {
        List<ShopList> list = shopListService.list(new QueryWrapper<ShopList>(new ShopList()).in("type", 1, 2).orderByDesc("flag").orderByAsc("item_name"));
        List<TreeEntity> data = new ArrayList<>();

        TreeEntity top = new TreeEntity();
        top.setId(1 + "");
        top.setTitle("全部");
        List<TreeEntity> topChildren = new ArrayList<>();
        for (ShopList s1 : list) {
            if (s1.getType() == 1) {
                TreeEntity one = new TreeEntity();
                one.setId(s1.getId() + "");
                one.setTitle(s1.getItemName());
                List<TreeEntity> oneChildren = new ArrayList<>();
                for (ShopList s2 : list) {
                    if (s2.getParentId().equals(s1.getId())) {
                        TreeEntity two = new TreeEntity();
                        two.setId(s2.getId() + "");
                        two.setTitle(s2.getItemName());
                        oneChildren.add(two);
                    }
                }
                one.setChildren(oneChildren);
                topChildren.add(one);
            }
        }
        top.setChildren(topChildren);
        data.add(top);

        TreeResult result = new TreeResult();
        Status status = new Status();
        status.setMessage("操作成功");
        result.setStatus(status);
        result.setData(data);
        return result;
    }
}
