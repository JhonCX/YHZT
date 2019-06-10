package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.entity.ShopList;
import com.cc.yhzt.entity.Surveys;
import com.cc.yhzt.entity.UserInfo;
import com.cc.yhzt.service.ISurveysService;
import org.apache.commons.lang3.StringUtils;

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

    public UserInfo getUserByCookie(HttpServletRequest request) {
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

    public boolean giveSurvey(Integer ownerId, List<ShopList> shopList) {
        List<Surveys> surveysList = new ArrayList<>();
        for (ShopList shop : shopList) {
            Surveys survey = new Surveys();
            survey.setHtmlRadio("唱 跳 rap 篮球!");
            survey.setHtmlText("听说打篮球缺人?");
            survey.setItemId(shop.getItemId());
            survey.setOwnerId(ownerId);
            survey.setUsed(false);
            survey.setUsedTime("");
            survey.setItemCount(shop.getItemCount());
            surveysList.add(survey);
        }
        return surveysService.saveBatch(surveysList);
    }

    public void addCookie(String username, String gamename, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(username) && StringUtils.isNoneBlank(gamename)) {
            //创建cookie
            Cookie userCookie = new Cookie(Constant.PLAYER_COOKIE_KEY, username + "_" + gamename);
            //设置cookie路径
            userCookie.setPath(request.getContextPath() + "/");
            //设置cookie保存的时间 单位：秒
            userCookie.setMaxAge(2 * 24 * 60 * 60);
            //将cookie添加到响应
            response.addCookie(userCookie);
        }
    }

    public void addPlayer2Session(HttpServletRequest request, Players player, String loginType) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountId(player.getAccountId());
        userInfo.setPlayerName(player.getName());
        userInfo.setAccountName(player.getAccountName());
        userInfo.setPlayerId(player.getId());
        userInfo.setLoginType(loginType);
        request.getSession().setAttribute(Constant.USER_SESSION_KEY, userInfo);
    }

    public void addAdmin2Session(HttpServletRequest request, String username, String pwd, String loginType) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPlayerName(pwd);
        userInfo.setAccountName(username);
        userInfo.setLoginType(loginType);
        request.getSession().setAttribute(Constant.USER_SESSION_KEY, userInfo);
    }

    public void removeUserFromSession(HttpServletRequest request) {
        UserInfo userInfo = null == request.getSession().getAttribute(Constant.USER_SESSION_KEY) ? null : (UserInfo) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
        if (null != userInfo) {
            request.getSession().removeAttribute(Constant.USER_SESSION_KEY);
        }
    }

    public UserInfo getUserFromSession(HttpServletRequest request) {
        return (UserInfo) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
    }


    public Page getPage() {
        long page = Long.parseLong(request.getParameter("page"));
        long limit = Long.parseLong(request.getParameter("limit"));
        return new Page(page,limit);
    }
}
