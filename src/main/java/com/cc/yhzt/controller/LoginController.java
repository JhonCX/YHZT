package com.cc.yhzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.entity.AccountData;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.service.IAccountDataService;
import com.cc.yhzt.service.IPlayersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author :cc
 * @date : 2019/6/8
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private IPlayersService playersService;
    @Resource
    private IAccountDataService accountDataService;

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        return "login";
    }


    @RequestMapping("login")
    @ResponseBody
    public RestBean login(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "gamename") String gamename) {
        String gm = "gm";
        if ("gm".equals(username)) {
            try {
                addCookie(username, gamename, response, request);
                addAdmin2Session(request, username, gamename, Constant.ADMIN);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return new RestBean(1, "登录成功~");
        } else {
            Players user = playersService.getOne(new QueryWrapper<>(new Players().setAccountName(username).setName(gamename)));
            if (null != user && null != user.getAccountId()) {
                AccountData accountInfo = accountDataService.getById(user.getAccountId());
                if (null != accountInfo && null != accountInfo.getActivated()) {
                    if (1 == accountInfo.getActivated()) {
                        try {
                            addCookie(username, gamename, response, request);
                            addAdmin2Session(request, username, gamename, Constant.PLAYER);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return new RestBean(1, "登录成功~");
                    } else {
                        return new RestBean(0, "账户被禁止登录!~");
                    }
                } else {
                    return new RestBean(-1, "账户登录名或密码错误~");
                }
            } else {
                return new RestBean(-1, "账户不存在~");
            }
        }
    }

    @RequestMapping("getUserType")
    @ResponseBody
    public RestBean getUserType(HttpServletRequest request,
                                HttpServletResponse response) {
        return new RestBean(1, "gm");
    }
}
