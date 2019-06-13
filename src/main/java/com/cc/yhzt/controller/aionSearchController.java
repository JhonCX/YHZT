package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.yhzt.common.utils.StringUtil;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.entity.ShopList;
import com.cc.yhzt.entity.ShopListItem;
import com.cc.yhzt.entity.Surveys;
import com.cc.yhzt.service.IShopListItemService;
import com.cc.yhzt.service.IShopListService;
import com.cc.yhzt.service.ISurveysService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/13
 */
@Controller
public class aionSearchController extends BaseController {
    @Resource
    private ISurveysService surveysService;
    @Resource
    private IShopListService shopListService;
    @Resource
    private IShopListItemService itemService;

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping(value = "/colorSelect", method = RequestMethod.GET)
    public String colorSelect() {
        return "page/player/color";
    }

    @ResponseBody
    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping(value = "/receiveHistory", method = RequestMethod.POST)
    public RestBean buyHistory() {
        Players player = getPlayer();
        IPage page = surveysService.page(getPage(), new QueryWrapper<>(new Surveys().setOwnerId(player.getId())).orderByDesc("unique_id"));
        List<Surveys> newList = new ArrayList<>();
        List<Surveys> surveysList = page.getRecords();
        for (Surveys surveys : surveysList) {
            ShopListItem one = itemService.getOne(new QueryWrapper<>(new ShopListItem().setItemId(surveys.getItemId())));
            surveys.setItemName(one.getItemName());
            newList.add(surveys);
        }
        return new RestBean(200, "", page.getTotal(), newList);
    }


    @RequestMapping(value = "/aionSearch", method = RequestMethod.GET)
    @RequiresRoles(Constant.ALL_ACCOUNT)
    public String aionSearch() {
        return "page/player/aionSearch";
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/taozhuangSearch", method = RequestMethod.POST)
    public RestBean taozhuangSearch(
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "key",required = false) Integer key) {
        final Integer type = 3;
        return getRestBean(keyword, key, type);
    }

    private RestBean getRestBean(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "key", required = false) Integer key, Integer type) {
        IPage List = new Page();
        if (null == key || StringUtil.isBlank(keyword)) {
            return new RestBean(0, "", List.getTotal(), List.getRecords());
        }
        if (1 == key) {
            List = itemService.page(getPage(), new QueryWrapper<>(new ShopListItem().setType(type)).like("item_id", keyword).orderByAsc("id"));
        } else if (2 == key) {
            List = itemService.page(getPage(), new QueryWrapper<>(new ShopListItem().setType(type)).like("item_name", keyword).orderByAsc("id"));
        } else if (3==key){
            List = itemService.page(getPage(), new QueryWrapper<>(new ShopListItem().setType(type)).like("str_name", keyword).orderByAsc("id"));
        }
        return new RestBean(0, "", List.getTotal(), List.getRecords());
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/itemSearch", method = RequestMethod.POST)
    public RestBean itemSearch(
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "key",required = false) Integer key) {
        final Integer type = 4;
        return getRestBean(keyword, key, type);
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/npcSearch", method = RequestMethod.POST)
    public RestBean npcSearch(@RequestParam(value = "keyword",required = false) String keyword,
                              @RequestParam(value = "key",required = false) Integer key) {
        final Integer type = 0;
        return getRestBean(keyword, key, type);
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/skillSearch", method = RequestMethod.POST)
    public RestBean skillSearch(@RequestParam(value = "keyword",required = false) String keyword,
                                @RequestParam(value = "key",required = false) Integer key) {
        final Integer type = 1;
        return getRestBean(keyword, key, type);
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @ResponseBody
    @RequestMapping(value = "/questSearch", method = RequestMethod.POST)
    public RestBean questSearch(@RequestParam(value = "keyword",required = false) String keyword,
                                @RequestParam(value = "key",required = false) Integer key) {
        final Integer type = 2;
        return getRestBean(keyword, key, type);
    }


}
