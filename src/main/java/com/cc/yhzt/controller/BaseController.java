package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.define.SystemParams;
import com.cc.yhzt.entity.*;
import com.cc.yhzt.service.IInventoryService;
import com.cc.yhzt.service.IPlayersService;
import com.cc.yhzt.service.ISurveysService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/8
 */
public class BaseController {
    @Resource
    ISurveysService surveysService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private IInventoryService inventoryService;
    @Resource
    private SystemParams systemParams;
    @Resource
    private IPlayersService playersService;

    public UserInfo getUserByCookie() {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if (Constant.PLAYER_COOKIE_KEY.equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isNotBlank(token)) {
            String[] s = token.split("_");
            if (s.length == BigDecimal.ROUND_CEILING) {
                UserInfo userInfo = new UserInfo();
                userInfo.setAccountName(s[0]);
                userInfo.setPlayerName(s[1]);
                return userInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Players getPlayer(){
        UserInfo userInfo = getUserByCookie();
        return  playersService.getOne(new QueryWrapper<>(new Players().setName(userInfo.getPlayerName())));
    }


    public void addCookie(String username, String gamename, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(username) && StringUtils.isNoneBlank(gamename)) {
            //创建cookie
            Cookie userCookie = new Cookie(Constant.PLAYER_COOKIE_KEY, username + "_" + gamename);
            //设置cookie路径
            userCookie.setPath(request.getContextPath() + "/");
            //设置cookie保存的时间 单位：秒
            userCookie.setMaxAge(180 * 24 * 60 * 60 );
            //将cookie添加到响应
            response.addCookie(userCookie);
        }
    }


    public Page getPage() {
        long page = Long.parseLong(null == request.getParameter("page") ? "1" : request.getParameter("page"));
        long limit = Long.parseLong(null == request.getParameter("limit") ? "10" : request.getParameter("limit"));
        return new Page(page, limit);
    }

    public WalletEntity getWallet() {
        WalletEntity walletEntity = new WalletEntity();
        UserInfo userInfo = getUserByCookie();
        Players players = playersService.getOne(new QueryWrapper<>(new Players().setName(userInfo.getPlayerName())));
        Integer id = players.getId();
        List<Inventory> moneys = inventoryService.list(new QueryWrapper<>(new Inventory().setItemOwner(id).setItemId(systemParams.getId())));
        Long count = 0L;
        for (Inventory money : moneys) {
            count += money.getItemCount();
        }
        walletEntity.setAllMoney(count);
        walletEntity.setUser(players);
        walletEntity.setMoneys(moneys);
        return walletEntity;
    }


    /**
     * 是否出售 1为出售 2为余额不足 0为错误
     */
    public Sale isSale(Long shopPrice, List<ShopList> shopList, Boolean isGift, Players giftPlayer) {
        WalletEntity wallet = getWallet();
        Sale sale = new Sale();
        sale.setCost(0L);
        sale.setFlag(2);
        sale.setWallet(wallet.getAllMoney());
        Subject subject = SecurityUtils.getSubject();
        boolean gm = subject.hasRole(Constant.ADMIN_ACCOUNT);
        if (gm) {
            sale.setFlag(1);
            sale.setCost(shopPrice);
            if (!isGift) {
                boolean b = giveSurvey(wallet.getUser(), shopList);
                if (!b) {
                    sale.setFlag(0);
                }
            } else {
                boolean b = giveSurvey(giftPlayer, shopList);
                if (!b) {
                    sale.setFlag(0);
                }
            }
        }else {
            if (wallet.getAllMoney() >= shopPrice) {
                if (costMoney(wallet.getUser().getId(), wallet.getMoneys(), shopPrice)) {
                    sale.setFlag(1);
                    sale.setCost(shopPrice);
                    sale.setWallet(wallet.getAllMoney() - shopPrice);
                    if (!isGift) {
                        boolean b = giveSurvey(wallet.getUser(), shopList);
                        if (!b) {
                            sale.setFlag(0);
                        }
                    } else {
                        boolean b = giveSurvey(giftPlayer, shopList);
                        if (!b) {
                            sale.setFlag(0);
                        }
                    }

                }
            } else {
                sale.setCost(0L);
                sale.setFlag(2);
            }
        }
        return sale;
    }

    public Boolean costMoney(Integer itemOwner, List<Inventory> moneys, Long cost) {
        for (Inventory money : moneys) {
            if (cost < money.getItemCount()) {
                money.setItemCount(money.getItemCount() - cost);
                return inventoryService.updateById(money);
            } else if (cost.equals(money.getItemCount())) {
                return inventoryService.removeById(money.getItemUniqueId());
            } else if (cost > money.getItemCount()) {
                cost = cost - money.getItemCount();
                inventoryService.removeById(money.getItemUniqueId());
            }
        }
        return false;
    }

    public boolean giveSurvey(Players user, List<ShopList> shopList) {
        List<Surveys> surveysList = new ArrayList<>();
        for (ShopList shop : shopList) {
            Surveys survey = new Surveys();
            survey.setHtmlRadio("我是唱跳时长两年半的 '" + user.getName() + "' ~!");
            survey.setHtmlText("听说打篮球缺人?");
            survey.setItemId(shop.getItemId());
            survey.setOwnerId(user.getId());
            survey.setUsed(false);
            survey.setUsedTime("");
            survey.setItemCount(shop.getItemCount());
            surveysList.add(survey);
        }
        return surveysService.saveBatch(surveysList);
    }


    public RestBean shopResult(Long shopPrice, List<ShopList> shopList, Boolean isGift, Players giftPlayer) {
        Sale sale = isSale(shopPrice, shopList, isGift, giftPlayer);
        if (1 == sale.getFlag()) {
            if (isGift) {
                return new RestBean(0, "赠送成功！", sale.getWallet() + "");
            } else {
                return new RestBean(0, "购买成功！", sale.getWallet() + "");
            }
        } else if (2 == sale.getFlag()) {
            return new RestBean(-1, "失败，余额不足！");
        } else {
            return new RestBean(-1, "失败，未知错误！");
        }
    }

    public void getTreeTntity(List<TreeEntity> data, ShopList top, Integer id) {
        TreeEntity topTree = new TreeEntity();
        topTree.setId(top.getId());
        topTree.setTitle(top.getItemName());
        topTree.setCheckArr(0);
        topTree.setItemId(top.getItemId());
        topTree.setFlag(top.getFlag());
        topTree.setParentId(top.getParentId());
        topTree.setType(top.getType());
        if (null != id) {
            if (top.getId().equals(id)) {
                topTree.setChecked(1);
            }
            if (top.getId() != 1) {
                data.add(topTree);
            }
        } else {
            data.add(topTree);
        }
    }
}
